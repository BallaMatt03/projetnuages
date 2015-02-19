package client.gui.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import server.webservices.Config;
import server.webservices.nuage.model.Catalog;
import server.webservices.nuage.model.CatalogEntry;
import server.webservices.nuage.services.ZipManager;
import client.gui.main.JPanelCustom;

public class GalleryUI {

	private static JDialog dialog;
	
    private JPanelCustom imagePanel;
    private JToolBar buttonBar;  
    
    private JButton oldButtonSelected;
    private JButton oldChooseButton;
    
    private static List<CatalogEntry> images = null;
    
    private BufferedImage imageSelected;    
    private String imageNameSelected;
    
    private static ZipManager zipManager;

//	public static void main(String[] args) {
//	EventQueue.invokeLater(new Runnable() {
//        public void run() {
//        	GalleryUI galleryUI = new GalleryUI();
//        }
//    });
//	
//}
    
	/**
	 * Create the GalleryUI window
	 * @param JDialog dialog
	 * @param String path
	 */
	public GalleryUI(JDialog dialog, String path) {
		this.dialog = dialog;
		initialize();
		images = getBufferedImages();
        // Start the image loading SwingWorker in a background thread
        loadImages.execute();
	}
	
	/**
	 * Create the GalleryUI window after communication with Google server
	 * @param JDialog dialog
	 * @param String path
	 * @param String keyword
	 */
	public GalleryUI(JDialog dialog, String path, String keyword) {
		this.dialog = dialog;
		zipManager = new ZipManager();
		initialize();
		images = getBufferedImages(path, keyword);
        // Start the image loading SwingWorker in a background thread
        loadImages.execute();
	}
    
	/**
	 * Initialize the contents of the dialog.
	 */
    public void initialize() {
        
        imagePanel = new JPanelCustom();
        imagePanel.setBackground(Color.BLACK);
        dialog.getContentPane().add(imagePanel, BorderLayout.CENTER);
        
        buttonBar = new JToolBar();
        
        dialog.getContentPane().add(buttonBar, BorderLayout.SOUTH);
    }
    
    /**
     * Get the BufferedImages from the server
     * @return List<CatalogEntry>
     */
    public static List<CatalogEntry> getBufferedImages() {
    	List<CatalogEntry> images = new ArrayList<CatalogEntry>();

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(Config.HOST + "/nuage").path("images");
		
		Catalog catalogue = webTarget.request(MediaType.APPLICATION_XML).get(Catalog.class);
		images = catalogue.getImages();

		return images;
    }
    
    /**
     * Get the BufferedImages from Google Image search zip file
     * @param String path
     * @param String keyword
     * @return List<CatalogEntry>
     */
    public static List<CatalogEntry> getBufferedImages(String path, String keyword) {
    	List<CatalogEntry> images = new ArrayList<CatalogEntry>();

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(Config.HOST + "/nuage").path(path).queryParam("keyword", keyword);
		Invocation invoke  = webTarget.request().buildGet();
		Response resp = invoke.invoke();
		
		InputStream zip = (InputStream) resp.getEntity();
		File outputZip = new File("./res/outputZip.zip");
		if (outputZip.exists()) {
			outputZip.delete();
		}
		
		try {
			//Write the zip
			writeZip(zip, outputZip);
	
			//Unzip
			zipManager.unzip(new File("./res/outputZip.zip").getAbsolutePath());
				
			int i = 0;
			for (String image : new File(".").list()) {
				if (image.endsWith(".jpeg")){
					CatalogEntry entry = new CatalogEntry(i, image, new File(new File(".").getAbsolutePath() + File.separator + image).getAbsolutePath());
					images.add(entry);
					++i;
				}
			}
		
		} catch (IOException e) {
			System.err.println("**** ERROR *****\n" + e.getMessage());
		}
		
		return images;
    }
    
    /**
     * Write the zip file
     * @param InputStream zip
     * @param File outputZip
     * @throws IOException
     */
    private static void writeZip(InputStream zip, File outputZip) throws IOException{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(zip);
			bos = new BufferedOutputStream(new FileOutputStream(outputZip));
			int data;
			while ((data = bis.read()) != -1) {
				bos.write(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
    }
    
    
    /**
     * SwingWorker class that loads the images a background thread and calls publish
     * when a new one is ready to be displayed.
     * We use Void as the first SwingWroker param as we do not need to return
     * anything from doInBackground().
     */
    private SwingWorker<Void, ThumbnailAction> loadImages = new SwingWorker<Void, ThumbnailAction>() {
        
        /**
         * Creates full size and thumbnail versions of the target image files.
         */
        @Override
        protected Void doInBackground() throws Exception {
        	
            for (CatalogEntry entry: images) {
            	BufferedImage buff = null;
            
            	if (entry.getUrl().startsWith("C:\\")) {
            		buff = ImageIO.read(new File(entry.getUrl()));
            	} else {
					Client client = ClientBuilder.newClient();
					
					WebTarget webTarget = client.target(entry.getUrl());
					Invocation invoke  = webTarget.request().buildGet();
					Response resp = invoke.invoke();
					
					buff = ImageIO.read((InputStream) resp.getEntity());
            	}
            	
                ImageIcon icon = new ImageIcon(buff, entry.getName());
                
                ThumbnailAction thumbAction = null;
                if (icon != null) {
                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 64, 64));
                    thumbAction = new ThumbnailAction(icon.getImage(), thumbnailIcon, entry.getName());
                }
                publish(thumbAction);
            }
            dialog.setVisible(true);

            return null;
        }
        
        /**
         * Process all loaded images.
         */
        @Override
        protected void process(List<ThumbnailAction> chunks) {
            for (ThumbnailAction thumbAction : chunks) {
                JButton thumbButton = new JButton(thumbAction);
                thumbButton.setBackground(Color.WHITE);
                thumbButton.setFocusPainted(false);
                
                // add the new button BEFORE the last glue
                // this centers the buttons in the toolbar
                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
            }
        }
    };
    
    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param sourceImage - source image to scale
     * @param width - desired width
     * @param height - desired height
     * @return - the new resized image
     */
    private Image getScaledImage(Image sourceImage, int width, int height) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(sourceImage, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }
    
    /**
     * Action class that shows the image specified in it's constructor.
     */
    private class ThumbnailAction extends AbstractAction{
        
        /**
         * The icon if the full image we want to display.
         */
        private Image displayImage;
        
        private String displayName;
        
        /**
         * @param Image - The full size image to show in the button.
         * @param Icon - The thumbnail to show in the button.
         * @param String - The index of the icon.
         */
        public ThumbnailAction(Image image, Icon thumbnail, String index) {
            displayImage = image;
            displayName = index;
            
            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, index);
            
            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumbnail);
        }
        
        /**
         * Shows the full image in the main area and sets the application title.
         */
        public void actionPerformed(ActionEvent event) {
        	JButton buttonSelected = (JButton) event.getSource();
        	
        	JButton chooseButton = new JButton("Choisir");
        	chooseButton.setBackground(SystemColor.activeCaption);
        	chooseButton.setFocusPainted(false);
        	chooseButton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent event) {
					imageSelected = (BufferedImage) displayImage;
					imageNameSelected = displayName;
					dialog.dispose();
				}
			});
        	
        	if (oldButtonSelected != null) {
        		oldButtonSelected.setBackground(Color.WHITE);
        		imagePanel.remove(oldChooseButton);
        	}
        	buttonSelected.setBackground(Color.BLUE);
        	
        	oldButtonSelected = buttonSelected;
        	oldChooseButton = (JButton) imagePanel.add(chooseButton);
        	
        	imagePanel.setImageBackground(displayImage);
        	imagePanel.revalidate();
        }
    }

    /**
     * Return the Image selected into the Gallery
     * @return BufferedImage
     */
	public BufferedImage getImageSelected() {
		return imageSelected;
	}

    /**
     * Return the Image name selected into the Gallery
     * @return BufferedImage
     */
	public String getImageName() {
		return imageNameSelected;
	}
	
}

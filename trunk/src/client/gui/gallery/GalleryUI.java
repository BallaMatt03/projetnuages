package client.gui.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import client.gui.main.JPanelCustom;

public class GalleryUI {

	private static JFrame frame;
	
    private JPanelCustom imagePanel;
    private JToolBar buttonBar;  
    
    private JButton oldButtonSelected;
    private JButton oldChooseButton;
    
    private static List<BufferedImage> images = null;
    /**
     * Main entry point to the demo. Loads the Swing elements on the "Event
     * Dispatch Thread".
     *
     * @param args
     */
    public static void main(String[] args) {
    	images = getBufferedImages();
    	EventQueue.invokeLater(new Runnable() {
            public void run() {
                GalleryUI gallery = new GalleryUI();
            }
        });
    }
    
	/**
	 * Create the application.
	 */
	public GalleryUI() {
		initialize();
		
        // Start the image loading SwingWorker in a background thread
        loadImages.execute();
	}
    
	/**
	 * Initialize the contents of the frame.
	 */
    public void initialize() {
    	frame = new JFrame("Gallery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(740, 480));
	    frame.setUndecorated(true);
	    frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        frame.setSize(new Dimension(740, 480));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        imagePanel = new JPanelCustom();
        imagePanel.setBackground(Color.BLACK);
        frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
        
        buttonBar = new JToolBar();
        
        frame.getContentPane().add(buttonBar, BorderLayout.SOUTH);
    }
    
    public static List<BufferedImage> getBufferedImages() {
		List<BufferedImage> images = new ArrayList<BufferedImage>();

		try {
			File folderZIP = new File("./res/zipfolder");
			if (folderZIP.exists()) {
				for (File image : folderZIP.listFiles()) {
					images.add(ImageIO.read(image));
				}
			}
		} catch (IOException e) {
			System.err.println("*** ERROR ***" + e.getMessage());
		}
		return images;
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
        	
        	int i = 0;
            for (BufferedImage buffImage : images) {
                ImageIcon icon = new ImageIcon(buffImage, "Image[" + (i++) + "]");
                
                ThumbnailAction thumbAction = null;
                if (icon != null) {
                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 64, 64));
                    thumbAction = new ThumbnailAction(icon.getImage(), thumbnailIcon, "" + i);
                }
                publish(thumbAction);
            }

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
        
        /**
         * @param Image - The full size image to show in the button.
         * @param Icon - The thumbnail to show in the button.
         * @param String - The index of the icon.
         */
        public ThumbnailAction(Image image, Icon thumbnail, String index) {
            displayImage = image;
            
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
}

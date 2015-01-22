/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package client.gui.gallery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import client.gui.main.JPanelImage;

public class GalleryUI extends JFrame {
    
    private JPanelImage imagePanel = new JPanelImage();
    private JToolBar buttonBar = new JToolBar();  
    
    /**
     * Main entry point to the demo. Loads the Swing elements on the "Event
     * Dispatch Thread".
     *
     * @param args
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GalleryUI app = new GalleryUI();
                app.setVisible(true);
            }
        });
    }
    
    /**
     * Default constructor for the demo.
     */
    public GalleryUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gallery");
        
        // We add two glue components. Later in process() we will add thumbnail buttons
        // to the toolbar inbetween thease glue compoents. This will center the
        // buttons in the toolbar.
        buttonBar.add(Box.createGlue());
        buttonBar.add(Box.createGlue());
        
        add(buttonBar, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.CENTER);
        
        setSize(new Dimension(740, 480));
        
        // this centers the frame on the screen
        setLocationRelativeTo(null);
        
        // start the image loading SwingWorker in a background thread
        loadImages.execute();
    }
    
    /**
     * SwingWorker class that loads the images a background thread and calls publish
     * when a new one is ready to be displayed.
     *
     * We use Void as the first SwingWroker param as we do not need to return
     * anything from doInBackground().
     */
    private SwingWorker<Void, ThumbnailAction> loadImages = new SwingWorker<Void, ThumbnailAction>() {
        
        /**
         * Creates full size and thumbnail versions of the target image files.
         */
        @Override
        protected Void doInBackground() throws Exception {
        	File folderImage = new File("./res/zipfolder");
        	int i = 0;
            for (String imageFileName : folderImage.list()) {
            	if (!imageFileName.equals("Thumbs.db")) {
	                ImageIcon icon = createImageIcon(folderImage.getCanonicalPath() + File.separator + imageFileName, imageFileName+"["+(i++)+"]");
	                
	                ThumbnailAction thumbAction = null;
	                if(icon != null){
	                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 64, 64));
	                    thumbAction = new ThumbnailAction(icon.getImage(), thumbnailIcon, imageFileName+"["+i+"]");
	                }
	                publish(thumbAction);
            	}
            }
            // unfortunately we must return something, and only null is valid to
            // return when the return type is void.
            return null;
        }
        
        /**
         * Process all loaded images.
         */
        @Override
        protected void process(List<ThumbnailAction> chunks) {
            for (ThumbnailAction thumbAction : chunks) {
                JButton thumbButton = new JButton(thumbAction);
                // add the new button BEFORE the last glue
                // this centers the buttons in the toolbar
                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
            }
        }
    };
    
    /**
     * Creates an ImageIcon if the path is valid.
     * @param String - resource path
     * @param String - description of the file
     */
    protected ImageIcon createImageIcon(String path,
            String description) {
    	File fileImage = new File(path);
    	ImageIcon icon = null;
    	try {
	    	if (fileImage.exists()) {
				icon = new ImageIcon(ImageIO.read(fileImage), description);
	        }
		} catch (IOException e) {
			System.err.println("*** ERROR *** \n"+e.getMessage());
		}
    	
    	return icon;
    }
    
    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param width - desired width
     * @param height - desired height
     * @return - the new resized image
     */
    private Image getScaledImage(Image srcImg, int width, int height){
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }
    
    /**
     * Action class that shows the image specified in it's constructor.
     */
    private class ThumbnailAction extends AbstractAction{
        
        /**
         *The icon if the full image we want to display.
         */
        private Image displayImage;
        
        /**
         * @param Icon - The full size photo to show in the button.
         * @param Icon - The thumbnail to show in the button.
         * @param String - The descriptioon of the icon.
         */
        public ThumbnailAction(Image image, Icon thumb, String desc){
            displayImage = image;
            
            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, desc);
            
            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumb);
        }
        
        /**
         * Shows the full image in the main area and sets the application title.
         */
        public void actionPerformed(ActionEvent e) {
        	imagePanel.setImageBackground(displayImage);
            setTitle("Image: " + getValue(SHORT_DESCRIPTION).toString());
        }
    }
}

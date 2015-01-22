package client.gui.main;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JPanelCustom extends JPanel{

	private static final long serialVersionUID = 6295511276760078241L;

	protected Image image; 
	private int x1, x2, y1, y2;
	private double scale;
	private double diffWidth;
	
	public double getDiffWidth()
	{
		return this.diffWidth;
	}
	public double getScale()
	{
		return this.scale;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		autoFill(this, g);
	}
	
	protected void autoFill(Component canvas, Graphics g){
        if (image != null) {
			int imgWidth = image.getWidth(null);
	        int imgHeight = image.getHeight(null);
	        System.out.println("Taille image départ : "+imgWidth+" x "+imgHeight); 
	        double imgAspect = (double) imgHeight / imgWidth;
	        
	 
	        int canvasWidth = canvas.getWidth();
	        int canvasHeight = canvas.getHeight();
	         
	        double canvasAspect = (double) canvasHeight / canvasWidth;
	 
	        x1 = 0; // top left X position
	        y1 = 0; // top left Y position
	        x2 = 0; // bottom right X position
	        y2 = 0; // bottom right Y position
	         
	        if (canvasAspect > imgAspect) {
	            y1 = canvasHeight;
	            // keep image aspect ratio
	            canvasHeight = (int) (canvasWidth * imgAspect);
	            y1 = (y1 - canvasHeight) / 2;
	        } else {
	            x1 = canvasWidth;
	            // keep image aspect ratio
	            canvasWidth = (int) (canvasHeight / imgAspect);
	            x1 = (x1 - canvasWidth) / 2;
	        }
	        x2 = canvasWidth + x1;
	        y2 = canvasHeight + y1;
	        scale = (double)imgHeight/(y2-y1);
	        // Panel Size : 
	        int widthPanel = this.getWidth();
	        int heightPanel = this.getHeight();
	        this.diffWidth = (double)(widthPanel - canvasWidth)/2.0; 
	        System.out.println("Taille Panel départ : "+widthPanel+" x "+heightPanel);
	        System.out.println("Taille Canvas départ : "+canvasWidth+" x "+canvasHeight);
	        g.drawImage(image, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);
        }
	}
	
	public Rectangle getRectangleImage(){
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}
	
	public BufferedImage getBufferedImage(){
		if (image instanceof BufferedImage) {
	        return (BufferedImage) image;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(image, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}

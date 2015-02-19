package client.gui.results;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class JPanelTransparency extends JPanel {
	private static final long serialVersionUID = 916258527845815191L;
	//alpha = 0 => 100% transparent, alpha = 1 => 0% transparent
	private float alpha = 0.5f;
    private Image image;
    
    private Graphics2D graphic2D;
    
    /** 
     * Constructor of JPanelTransparency.
     */
    public JPanelTransparency() {
    	setOpaque(false);
    }
    

    // Application of the image's dimension to the panel's dimension
    @Override
    public Dimension getPreferredSize() {
        return image == null ? super.getPreferredSize() : 
        	new Dimension(image.getWidth(null), image.getHeight(null));
    }
    
    //	Draw image with the new alpha bit value
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        if (image != null) {
            int width = image.getWidth(this);
            int height = image.getHeight(this);
            
            graphic2D = (Graphics2D) graphic.create();
            graphic2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            graphic2D.drawImage(image, 0, 0, width, height, this);
            
            setSize(new Dimension(width, height));
        }
    }
    
    public void setImage(Image image){
    	this.image = image;
        repaint();
    }
    
    public void setImageAlpha(float alpha){
    	this.alpha = alpha;
    	repaint();
    }
}

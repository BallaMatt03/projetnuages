package client.gui.main;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class JPanelImage extends JPanelCustom implements ChangeListener {
    
	private BufferedImage cropImage;
    private Rectangle cropBounds;
	private boolean drag_status = false;	
	private Point cropPointStart, cropPointEnd;

    public JPanelImage(Image img) {
    	this.image = img;
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
    }

	@Override
	public void stateChanged(ChangeEvent e) {
		if (cropImage != null){
			ClientUI.setImagePreview(cropImage);
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle drawCrop = getCropBounds();
        if (drawCrop != null && drag_status && (getHeight() - 1) > 0 && (getWidth() - 1) > 0) {
            Area area = new Area(new Rectangle(0, 0, getWidth() - 1, getHeight() - 1));
            area.subtract(new Area(drawCrop));
            
            Composite composite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
            g2d.fill(area);
            g2d.setComposite(composite);
            g2d.draw(area);
        }
    }
    
    protected Rectangle getCropBounds() {
        Rectangle actualBounds = null;
        if (cropBounds != null) {
            int x = cropBounds.x;
            int y = cropBounds.y;
            int width = cropBounds.width;
            int height = cropBounds.height;

            if (width < 0) {
                x += width;
                width -= (width * 2);
            }

            if (height < 0) {
                y += height;
                height -= (height * 2);
            }

            actualBounds = new Rectangle(x, y, width, height);
        }
        return actualBounds;
    }

    public void setImageBackground(Image image){
        super.image = image; 
        repaint();
       }
    
    public class MouseHandler extends MouseAdapter {


    	
        @Override
        public void mouseReleased(MouseEvent e) {
        	if (drag_status) {
	        	Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e1) {
					System.err.println("*** ERROR ***\n"+e1.getMessage());
				}
				//OLD SOLUTION
//	        	cropImage = robot.createScreenCapture(new Rectangle(
//	        			(int)cropPointStart.getX() + 1 > (int)cropPointEnd.getX() ? (int)cropPointEnd.getX() + 2 : (int)cropPointStart.getX() + 1, 
//	        			(int)cropPointStart.getY() + 1 > (int)cropPointEnd.getY() ? (int)cropPointEnd.getY() + 2 : (int)cropPointStart.getY() + 1, 
//	        			cropBounds.width - 1 < 0 ? (cropBounds.width + 2)*(-1) : cropBounds.width - 1, 
//	        			cropBounds.height - 1 < 0 ? (cropBounds.height + 2)*(-1) : cropBounds.height - 1));
	        	
				//NEW SOLUTION
	        	BufferedImage img = getBufferedImage();
	        	
	        	//cropPointStart = validPoint(new Point(e.getXOnScreen(), e.getYOnScreen()));
	        	double imgscale = getScale();// <1 ? getScale()+1 : getScale()-1 ;
	        	//System.out.println("Image Echelle : "+imgscale);
	        	if(cropPointStart.getX()<cropPointEnd.getX())
	        	{
	        		if(cropPointStart.getY()<cropPointEnd.getY())
	        		{
	        			cropImage = img.getSubimage((int)((cropPointStart.getX()-getDiffWidth())*imgscale), (int)(cropPointStart.getY()*imgscale), 
			        			(int) ((cropPointEnd.getX()-getDiffWidth())*imgscale - (cropPointStart.getX()-getDiffWidth())*imgscale), 
			        			(int)(cropPointEnd.getY()*imgscale - cropPointStart.getY()*imgscale));
	        		}else
	        		{
	        			cropImage = img.getSubimage((int)((cropPointStart.getX()-getDiffWidth())*imgscale), (int)(cropPointEnd.getY()*imgscale), 
			        			(int) ((cropPointEnd.getX()-getDiffWidth())*imgscale - (cropPointStart.getX()-getDiffWidth())*imgscale), 
			        			(int)(cropPointStart.getY()*imgscale - cropPointEnd.getY()*imgscale));
	        		}
	        		
	        	}else
	        	{
	        		if(cropPointStart.getY()<cropPointEnd.getY())
	        		{
	        			cropImage = img.getSubimage((int)((cropPointEnd.getX()-getDiffWidth())*imgscale), (int)(cropPointStart.getY()*imgscale), 
			        			(int) ((cropPointStart.getX()-getDiffWidth())*imgscale - (cropPointEnd.getX()-getDiffWidth())*imgscale), 
			        			(int)(cropPointEnd.getY()*imgscale - cropPointStart.getY()*imgscale));
	        		}else
	        		{
	        			cropImage = img.getSubimage((int)((cropPointEnd.getX()-getDiffWidth())*imgscale), (int)(cropPointEnd.getY()*imgscale), 
			        			(int) ((cropPointStart.getX()-getDiffWidth())*imgscale - (cropPointEnd.getX()-getDiffWidth())*imgscale), 
			        			(int)(cropPointStart.getY()*imgscale - cropPointEnd.getY()*imgscale));
	        		}

	        	}
	        	
	        	
	        	stateChanged(null);
	            cropBounds = null;
	            repaint();
        	}
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
        	drag_status = false;
            cropBounds = new Rectangle();
            cropPointStart = validPoint(new Point(e.getX(), e.getY()));
            cropBounds.setLocation(validPoint(e.getPoint()));
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (cropBounds != null) {
            	drag_status = true;
                Point p = validPoint(e.getPoint());
                int width = p.x - cropBounds.x;
                int height = p.y - cropBounds.y;
                int x = e.getX()<0 ? 0: e.getX();
                int y = e.getY()<0 ? 0: e.getY();
               // System.out.println("========================");
               // System.out.println("X : "+x+" Y :"+y);
               // System.out.println("========================");
                cropBounds.setSize(width, height);
                cropPointEnd = validPoint(new Point(x, y));
                repaint();
            }
        }
        

        
        private Point validPoint(Point p){
        	Rectangle imgRect = getRectangleImage();
        	Point newPoint = p;
        	if (newPoint.getX() > imgRect.getMaxX()) {
        		newPoint.setLocation(imgRect.getMaxX(), newPoint.getY() - 1);
        		//System.out.println("x max");
        	}
        	if (newPoint.getX() < imgRect.getMinX()) {
        		newPoint.setLocation(imgRect.getMinX(), newPoint.getY() - 1);
        		//System.out.println("x min");
        	}
        	if (newPoint.getY() > imgRect.getMaxY()) {
        		newPoint.setLocation(newPoint.getX(), imgRect.getMaxY() - 1);
        		//System.out.println("y max");
        	}
        	if (newPoint.getY() < imgRect.getMinY()) {
        		newPoint.setLocation(newPoint.getX(), imgRect.getMinY() - 1);
        		//System.out.println("y min");
        	}
        	//System.out.println("Coordonées  x:"+newPoint.getX()+" y:"+newPoint.getY()+" yMax:"+imgRect.getMaxY()+" xMax:"+imgRect.getMaxX());
        	return newPoint;
        }
    }
}

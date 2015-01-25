package server.imageprocessing;

/**
 * ..
 * @author Thomas
 *
 */
public class Crop {
	
	/**
	 * position x .
	 */
	private int startX;
	/**
	 *  position y.
	 */
	private int startY;
	/**
	 * width of crop .
	 */
	private int width;
	/**
	 * height of crop .
	 */
	private int height;
	
	/**
	 * angle of crop .
	 */
	private double angle;
	
	/**
	 * Constructor without parameter.
	 */
	public Crop() {
	}
	
	/**
	 * Constructor.
	 * @param xcoord 
	 * 				x
	 * @param ycoord 
	 * 				y
	 * @param pwidth
	 * 				width
	 * @param pheight
	 * 				height:
	 * @param pangle
	 * 				angle of rotation
	 */
	public Crop(final int xcoord, final int ycoord, final int pwidth, final int pheight) {
		this(xcoord, ycoord, pwidth, pheight, 0);
	}
	/**
	 * ..
	 * @param xcoord
	 * ..
	 * @param ycoord
	 * ..
	 * @param pwidth
	 * ..
	 * @param pheight
	 * ..
	 * @param pangle
	 * ..
	 */
	public Crop(final int xcoord, final int ycoord, final int pwidth, final int pheight, double pangle) {
		this.startX = xcoord;
		this.startY = ycoord;
		this.height = pheight;
		this.width = pwidth;
		this.angle = pangle;
	}
		
	/**
	 * ..
	 * @return the startX
	 */
	public final int getStartX() {
		return startX;
	}

	/**
	 * ..
	 * @param 
	 * 			pstartX the startX to set.
	 */
	public final void setStartX(final int pstartX) {
		this.startX = pstartX;
	}

	/**
	 * ..
	 * @return 
	 * 			the startY
	 */
	public final int getStartY() {
		return startY;
	}

	/**
	 * ..
	 * @param 
	 * 			pstartY the startY to set.
	 */
	public final void setStartY(final int pstartY) {
		this.startY = pstartY;
	}

	/**
	 * ..
	 * @return 
	 * 			the width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * ..
	 * @param 
	 * 			pwidth the width to set
	 */
	public final void setWidth(final int pwidth) {
		this.width = pwidth;
	}

	/**
	 * ..
	 * @return 
	 * 			the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * ..
	 * @param 
	 * 			pheight the height to set
	 */
	public final void setHeight(final int pheight) {
		this.height = pheight;
	}
	
	/**
	 * ..
	 * @return 
	 * 			the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * ..
	 * @param 
	 * 			angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
}

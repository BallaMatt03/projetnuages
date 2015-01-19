package server.imageprocessing;

/**
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
	 * Constructor without parameter.
	 */
	public Crop() {
	}
	
	/**
	 * Constructor.
	 * @param x :x
	 * @param y  :y
	 * @param pwidth : width
	 * @param pheight : height:
	 */
	public Crop(final int x, final int y, final int pwidth, final int pheight) {
		this.startX = x;
		this.startY = y;
		this.height = pheight;
		this.width = pwidth;
	}
		
	/**
	 * @return the startX
	 */
	public final int getStartX() {
		return startX;
	}

	/**
	 * @param pstartX the startX to set.
	 */
	public final void setStartX(final int pstartX) {
		this.startX = pstartX;
	}

	/**
	 * @return the startY
	 */
	public final int getStartY() {
		return startY;
	}

	/**
	 * @param pstartY the startY to set.
	 */
	public final void setStartY(final int pstartY) {
		this.startY = pstartY;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * @param pwidth the width to set
	 */
	public final void setWidth(final int pwidth) {
		this.width = pwidth;
	}

	/**
	 * @return the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * @param pheight the height to set
	 */
	public final void setHeight(final int pheight) {
		this.height = pheight;
	}
}

/**
 * 
 */
package server.imageprocessing;

/**
 * @author Thomas
 *
 */
public class Crop 
{
	
	private int startX;
	private int startY;
	private int width;
	private int height;

	/**
	 * 
	 */
	public Crop() 
	{
	}
	
	public Crop(int x, int y, int pwidth, int pheight)
	{
		this.startX = x;
		this.startY = y;
		this.height = pheight;
		this.width = pwidth;
	}
		
	/**
	 * @return the startX
	 */
	public int getStartX() 
	{
		return startX;
	}

	/**
	 * @param startX the startX to set
	 */
	public void setStartX(int startX) 
	{
		this.startX = startX;
	}

	/**
	 * @return the startY
	 */
	public int getStartY() 
	{
		return startY;
	}

	/**
	 * @param startY the startY to set
	 */
	public void setStartY(int startY) 
	{
		this.startY = startY;
	}

	/**
	 * @return the width
	 */
	public int getWidth() 
	{
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) 
	{
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() 
	{
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) 
	{
		this.height = height;
	}
}

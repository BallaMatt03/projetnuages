/**
 * 
 */
package server.imageprocessing.processing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import server.imageprocessing.Crop;

/**
 * @author Thomas
 *
 */
public class SimpleOperations {

	/**
	 * 
	 */
	public SimpleOperations() {
	}
	
	/**
	 * Get subimage
	 * @param srcImage source Image
	 * @param pCrop position of subImage
	 * @return subimage
	 * @throws IllegalArgumentException if the Crop.height or Crop.Widht is null
	 */
	static Image cut(Image srcImage, Crop pCrop) throws IllegalArgumentException
	{
		BufferedImage temp = toBufferedImage(srcImage);
		
		if ( pCrop.getHeight() == 0 || pCrop.getWidth() == 0)
		{
			throw new IllegalArgumentException("Les dimensions doivent être supèrieurs à zéro!");
		}
		
		return temp.getSubimage(pCrop.getStartX(), pCrop.getStartY(), pCrop.getWidth(), pCrop.getHeight());
	}
	
	
	static Image contourDetection ( Image imageSource)
	{
		
		return imageSource;
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

}

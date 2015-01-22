/**
 * 
 */
package server.imageprocessing.processing;

import java.awt.image.BufferedImage;

/**
 * @author Thomas
 *
 */
public final class ContourDetection {

	/**
	 * 
	 */
	private ContourDetection() {}
	
	/**
	 * @param imageSource 
	 * 			source image
	 * @param precision
	 * 		 	coeff. of erode
	 * @return 
	 * 			image
	 */
	public static BufferedImage eroseDetection(final BufferedImage imageSource, final int precision) {
		
		BufferedImage erodePicture = MorphologicalFilter.erose(imageSource, precision);
		BufferedImage result = new BufferedImage(imageSource.getWidth(null), imageSource.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
		result.createGraphics().drawImage(imageSource, 0, 0, null);
			
		for (int i = 0; i < erodePicture.getWidth(null); i++) {
			for (int j = 0; j < erodePicture.getHeight(null); j++) {
				result.setRGB(i, j , erodePicture.getRGB(i ,  j) * imageSource.getRGB(i,  j));
			}
		}
		return result;
	}
	
}

/**
 * 
 */
package server.imageprocessing.processing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * ..
 * 
 * @author Thomas
 *
 */
public final class MorphologicalFilter {
	
	/**
	 * ..
	 */
	private MorphologicalFilter() {
		
	}


	/**
	 * ..
	 * 
	 * @param image
	 * ..
	 * @param coefficient
	 * ..
	 * @return
	 * ..
	 */
	public static Image opening(final Image image, final int coefficient) {
		return dilatate(erose(image, coefficient), coefficient);
	}

	/**
	 * ..
	 * 
	 * @param image
	 * ..
	 * @param coefficient
	 * ..
	 * @return
	 * ..
	 */
	public static Image closing(final Image image, final int coefficient) {
		return erose(dilatate(image, coefficient), coefficient);
	}

	/**
	 * ..
	 * 
	 * @param image
	 * ..
	 * @param coefficient
	 * ..
	 * @return
	 * ..
	 */
	public static Image erose(final Image image, final int coefficient) {
		BufferedImage b = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        b.createGraphics().drawImage(image, 0, 0, null);
            
        BufferedImage bnew = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        
		for (int i = coefficient; i < b.getWidth() - coefficient; i++) {
			for (int j = coefficient; j < b.getHeight() - coefficient; j++) {
				boolean a = false;
				for (int k = -coefficient; k <= coefficient; k++) {
					for (int l = -coefficient; l <= coefficient; l++) {	
						if (b.getRGB(i + k, j + l) == Color.WHITE.getRGB()) {
							a = true; 
						}
					}
				}
				if (a) {
					bnew.setRGB(i, j, Color.WHITE.getRGB());
				} else {
					bnew.setRGB(i, j, Color.BLACK.getRGB());
				}
			}
		}
		return bnew;
	}

	/**
	 * ..
	 * 
	 * @param image
	 * ..
	 * @param coefficient
	 * ..
	 * @return
	 * ..
	 */
	public static Image dilatate(final Image image, final int coefficient) {
		BufferedImage b = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        b.createGraphics().drawImage(image, 0, 0, null);
            
        BufferedImage bnew = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        
        for (int i = coefficient; i < b.getWidth() - coefficient; i++) {
			for (int j = coefficient; j < b.getHeight() - coefficient; j++) {
				boolean a = false;
				for (int k = -coefficient; k <= coefficient; k++) {
					for (int l = -coefficient; l <= coefficient; l++) {	
						if (b.getRGB(i + k, j + l) == Color.BLACK.getRGB()) {
							a = true; 
						}
					}
				}
				if (a) {
					bnew.setRGB(i, j, Color.BLACK.getRGB());
				} else {
					bnew.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}
		return bnew;
	}
}

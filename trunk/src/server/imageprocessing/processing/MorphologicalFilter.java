/**
 * 
 */
package server.imageprocessing.processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * ..
 * 
 * @author Fabien & Sofiane
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
	public static BufferedImage opening(BufferedImage image, int coefficient) {
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
	public static BufferedImage closing(BufferedImage image, final int coefficient) {
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
	public static BufferedImage erose(BufferedImage image, int coefficient) {
		
		BufferedImage b = new BufferedImage(image.getWidth(null) + 2 * coefficient, image.getHeight(null) + 2 * coefficient, BufferedImage.TYPE_BYTE_BINARY);
        b.createGraphics().drawImage(image, coefficient, coefficient, null);
            
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
					bnew.setRGB(i - coefficient, j - coefficient, Color.WHITE.getRGB());
				} else {
					bnew.setRGB(i - coefficient, j - coefficient, Color.BLACK.getRGB());
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
	public static BufferedImage dilatate(BufferedImage image, int coefficient) {
		
		BufferedImage b = new BufferedImage(image.getWidth(null) + 2 * coefficient, image.getHeight(null) + 2 * coefficient, BufferedImage.TYPE_BYTE_BINARY);
		
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {		
				b.setRGB(i, j, Color.WHITE.getRGB());	
			}
		}
		
        b.createGraphics().drawImage(image, coefficient, coefficient, null);
            
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
					bnew.setRGB(i - coefficient, j - coefficient, Color.BLACK.getRGB());
				} else {
					bnew.setRGB(i - coefficient, j - coefficient, Color.WHITE.getRGB());
				}
			}
		}
		return bnew;
	}
}

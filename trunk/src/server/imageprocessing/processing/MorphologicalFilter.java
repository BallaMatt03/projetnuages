
package server.imageprocessing.processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Mathematical morphology (MM) is a theory and technique for 
 * the analysis and processing of geometrical structures, 
 * based on set theory, lattice theory, topology, and random 
 * functions. MM is most commonly applied to digital images, 
 * but it can be employed as well on graphs, surface meshes, 
 * solids, and many other spatial structures.
 * Topological and geometrical continuous-space concepts such as size, 
 * shape, convexity, connectivity, and geodesic distance, 
 * were introduced by MM on both continuous and discrete spaces. 
 * MM is also the foundation of morphological image processing, 
 * which consists of a set of operators that transform images 
 * according to the above characterizations.
 * The basic morphological operators are erosion, dilation, opening and closing.
 * 
 * @author Fabien & Sofiane
 *
 */
public class MorphologicalFilter {
	
	private MorphologicalFilter() {
		
	}

	/**
	 * The opening of A by B is obtained by the erosion of A by B, followed by dilation of the resulting image by B.
	 * 
	 * @param image
	 * Binary image
	 * @param coefficient
	 * Morphological coefficient
	 * @return
	 * Opened image
	 */
	public static BufferedImage opening(BufferedImage image, int coefficient) {
		return dilate(erose(image, coefficient), coefficient);
	}

	/**
	 * The closing of A by B is obtained by the dilation of A by B, 
	 * followed by erosion of the resulting structure by B.
	 * 
	 * @param image
	 * Binary image
	 * @param coefficient
	 * Morphological coefficient
	 * @return
	 * Closed image
	 */
	public static BufferedImage closing(BufferedImage image, final int coefficient) {
		return erose(dilate(image, coefficient), coefficient);
	}

	/**
	 * The basic effect of the operator on a binary image is 
	 * to erode away the boundaries of regions of foreground pixels.
	 * 
	 * @param image Binary image
	 * @param coefficient Erosed coefficient
	 * @return Erosed image
	 */
	public static BufferedImage erose(BufferedImage image, int coefficient) {
		
		BufferedImage bold = new BufferedImage(image.getWidth(null) + 2 * coefficient, image.getHeight(null) + 2 * coefficient, BufferedImage.TYPE_BYTE_BINARY);
		bold.createGraphics().drawImage(image, coefficient, coefficient, null);
            
        BufferedImage bnew = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        
		for (int i = coefficient; i < bold.getWidth() - coefficient; i++) {
			for (int j = coefficient; j < bold.getHeight() - coefficient; j++) {
				boolean change = false;
				for (int k = -coefficient; k <= coefficient; k++) {
					for (int l = -coefficient; l <= coefficient; l++) {	
						change = (bold.getRGB(i + k, j + l) == Color.WHITE.getRGB() ? true : change);
					}
				}
				bnew.setRGB(i - coefficient, j - coefficient, change ?  Color.WHITE.getRGB() : Color.BLACK.getRGB());
			}
		}
		return bnew;
	}

	/**
	 * The dilation operation usually uses a structuring element 
	 * for probing and expanding the shapes contained in the input image.
	 * 
	 * @param image
	 * Binary image
	 * @param coefficient
	 * Dilated coefficient
	 * @return
	 * Dilated image
	 */
	public static BufferedImage dilate(BufferedImage image, int coefficient) {
		
		BufferedImage bold = new BufferedImage(image.getWidth(null) + 2 * coefficient, image.getHeight(null) + 2 * coefficient, BufferedImage.TYPE_BYTE_BINARY);
		
		for (int i = 0; i < bold.getWidth(); i++) {
			for (int j = 0; j < bold.getHeight(); j++) {		
				bold.setRGB(i, j, Color.WHITE.getRGB());	
			}
		}
		
        bold.createGraphics().drawImage(image, coefficient, coefficient, null);
            
        BufferedImage bnew = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        
		for (int i = coefficient; i < bold.getWidth() - coefficient; i++) {
			for (int j = coefficient; j < bold.getHeight() - coefficient; j++) {
				boolean change = false;
				for (int k = -coefficient; k <= coefficient; k++) {
					for (int l = -coefficient; l <= coefficient; l++) {		
						change = (bold.getRGB(i + k, j + l) == Color.BLACK.getRGB() ? true : change);
					}
				}	
				bnew.setRGB(i - coefficient, j - coefficient, change ?  Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}
		return bnew;
	}
}

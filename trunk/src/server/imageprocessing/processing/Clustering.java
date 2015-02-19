
package server.imageprocessing.processing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Clustering {

	
	private Clustering() {
	}
	
	/**
	 * kMeans algorithm.
	 * @param src image source
	 * @param dst image destination
	 * @return picture
	 */
	static Image applyKmeans(BufferedImage src, String dst, int nbClusters) 
			throws IllegalArgumentException, IOException {
        // create new KMeans object 
        Kmeans kmeans = new Kmeans(); 

        // call the function to actually start the clustering
        BufferedImage dstImage = kmeans.calculate(src, nbClusters); 
        
        // save the resulting image
        try {
			ImageUtils.saveimageasJpeg(dstImage,  new FileOutputStream(dst), 100);
		} catch (IOException e) {
			if ( dst == null   || dst.isEmpty()) {
	        	throw new IllegalArgumentException("Le paramètre dst est incorrect : " + dst);			
			} else {
				e.printStackTrace();
			}
		}
        
        return dstImage;
	}
	
	/**
	 * Get max connex composant.
	 * @param image image source
	 * @return image
	 */
	Image getMaxConnexComposant(final Image image) {
		return null;
	}

}

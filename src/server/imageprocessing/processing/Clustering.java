
package server.imageprocessing.processing;

import java.awt.Image;
import java.awt.image.BufferedImage;

public final class Clustering {

	
	private Clustering() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * kMeans algorithm.
	 * @param src image source
	 * @param dst image destination
	 * @return picture
	 */
	static Image applyKmeans(String src, String dst, int nbClusters) {
        // create new KMeans object 
        Kmeans kmeans = new Kmeans(); 
        // call the function to actually start the clustering 
        BufferedImage dstImage = kmeans.calculate(kmeans.loadImage(src), nbClusters); 
        // save the resulting image 
        kmeans.saveImage(dst, dstImage); 
        
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

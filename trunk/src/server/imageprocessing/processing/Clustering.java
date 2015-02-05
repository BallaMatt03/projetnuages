
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
	static Image applyKmeans(String src, String dst, int nbClusters) 
			throws IllegalArgumentException, IOException {
        // create new KMeans object 
        Kmeans kmeans = new Kmeans(); 

        BufferedImage img  = null;
        try {
        	img = ImageIO.read(new File(src));
        } catch (IOException e ) {
        	System.err.println("Fichier non trouvé à l'emplacement spécifié : " + src);
        	throw new IllegalArgumentException("Le paramètre src est incorrect : " + src);
        }

        // call the function to actually start the clustering
        BufferedImage dstImage = kmeans.calculate(img, nbClusters); 
        
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

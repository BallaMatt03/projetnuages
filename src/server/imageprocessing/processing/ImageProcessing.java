
package server.imageprocessing.processing;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;



/**
 * ..
 * @author Thomas, Va�k
 *
 */
public class ImageProcessing implements IImageProcessing {

	/**
	 * ..
	 */
	public ImageProcessing() {
	}

	@Override
	public File preProcessing(File image, Crop crop) {
		return null;
	}

	@Override
	public File postProcessing(File image, Crop crop, File google, Crop cropGoogle) {
			
		BufferedImage in = null;
		try {
			
			//On r�cup�re les imges qui correspondent aux fichiers 
			//pass�s en param�tre
			in = ImageIO.read(image);
			BufferedImage srcImage = ImageUtils.toBufferedImage(in);
			
			in = ImageIO.read(google);
			BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D graph = newImage.createGraphics();
			graph.drawImage(in, 0, 0, null);
			graph.dispose();
			newImage = newImage.getSubimage(cropGoogle.getStartX(), 
					cropGoogle.getStartY(), cropGoogle.getWidth(), cropGoogle.getHeight());
			
			//ImageUtils.saveimageasJpeg(newImage,  new FileOutputStream("./pictures/results/final/avantRotate.jpg"), 100);
			
			// mise � l'�chelle de l'image google gr�ce au masque de l'image source
			newImage = ImageUtils.resize(newImage, crop.getWidth(), crop.getHeight());
			//ImageUtils.saveimageasJpeg(newImage,  new FileOutputStream("./pictures/results/final/apresRedim.jpg"), 100);
			
			// rotation de l'image google suivant l'angle donn�
			newImage = ImageUtils.rotate(newImage, cropGoogle.getAngle());
			//ImageUtils.saveimageasJpeg(newImage,  new FileOutputStream("./pictures/results/final/apresRotate.jpg"), 100);
			
			// obtention du contour de l'image google
			BufferedImage contourGoogleImage = ContourDetection.eroseDetection(newImage, 4);
			//ImageUtils.saveimageasJpeg(contourGoogleImage,  new FileOutputStream("./pictures/results/final/contourImageGoogleAvantResize.jpg"), 100);
			
			// mise � l'�chelle du contour
			contourGoogleImage = ImageUtils.resizeContour(contourGoogleImage, crop);
			//ImageUtils.saveimageasJpeg(contourGoogleImage,  new FileOutputStream("./pictures/results/final/apresRedim.jpg"), 100);
			
			//on boucle sur l'image source pour inscire le contour de l'image google en rouge
			int googleI = 0;
			int googleJ = 0;

			for (int i = crop.getStartX(); i < crop.getStartX() + crop.getWidth() ; i++) {
				for (int j = crop.getStartY(); j < crop.getStartY() + crop.getHeight() ; j++) {
					// A la base, la d�tection de contour renvoit un fond noir et le contour en blanc.
					// Mais la mise � l'�chelle fait que l'on obtient des nuances de gris.
					// On utilise ainsi les pixels qui ne sont pas noir afin de d�finir pr�cis�ment le contour
					if (contourGoogleImage.getRGB(googleI, googleJ) != Color.BLACK.getRGB()) {
						srcImage.setRGB(i, j, Color.red.getRGB());
					}
					googleJ++;
				}
				googleJ = 0;
				googleI++;
			}
			

			ImageUtils.saveimageasJpeg(srcImage,  new FileOutputStream("./pictures/results/final/result.jpg"), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new File("./pictures/results/final/temp.jpg");
	}

}

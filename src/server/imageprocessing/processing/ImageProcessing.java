/**
 * 
 */
package server.imageprocessing.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;

/**
 * @author Thomas
 *
 */
public class ImageProcessing implements IImageProcessing {

	/**
	 * 
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
			
			//On récupère les imges qui correspondent aux fichiers 
			//passés en paramètre
			in = ImageIO.read(image);
			BufferedImage srcImage = ImageUtils.toBufferedImage(in);
			
			in = ImageIO.read(google);
			BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D g = newImage.createGraphics();
			g.drawImage(in, 0, 0, null);
			g.dispose();
			newImage = newImage.getSubimage(cropGoogle.getStartX(), 
					cropGoogle.getStartY(), cropGoogle.getWidth(), cropGoogle.getHeight());
			
			//TODO mise à l'échelle grâce au masque de kmeans
			
			//TODO voir pourquoi quand on passe newImage on obtient une image noire
			BufferedImage contourGoogleImage = ContourDetection.eroseDetection(in.getSubimage(cropGoogle.getStartX(), 
					cropGoogle.getStartY(), cropGoogle.getWidth(), cropGoogle.getHeight()), 4);

			//on boucle sur l'image source pour inscire l'image google en rouge
			int googleI = 0;
			int googleJ = 0;
			for(int i = crop.getStartX(); i < crop.getStartX() + crop.getWidth() ; i++){
				for(int j = crop.getStartY(); j < crop.getStartY() +crop.getHeight() ; j++){
					if(contourGoogleImage.getRGB(googleI, googleJ) == Color.WHITE.getRGB()) {
						srcImage.setRGB(i, j, Color.red.getRGB());
					}
					googleJ++;
				}
				googleJ=0;
				googleI++;
			}
			

			ImageUtils.saveImageAsJPEG(srcImage,  new FileOutputStream("./pictures/results/final/result.jpg"), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new File("./pictures/results/final/temp.jpg");
	}

}

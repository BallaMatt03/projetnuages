
package server.imageprocessing.test;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;
import server.imageprocessing.processing.ImageProcessing;

import java.io.File;



/**
 * ..
 * @author Thomas
 *
 */
public class ImageProcessingTest {

	/**
	 * ..
	 */
	public ImageProcessingTest() {
	}
	
	/**
	 * ..
	 * @param args
	 */
	public static void main(String[] args) {
		
		IImageProcessing test = new ImageProcessing();
		
		System.out.println("Début des tests sur le pré traitement");
		File imageTest = new File("./pictures/sources/coeur_nuage.jpg");
		Crop preCrop =  new Crop(0,0,398,290,0);
		
		@SuppressWarnings("unused")
		File resultPre = test.preProcessing(imageTest, preCrop);
		System.out.println("Fin des tests sur le pré traitement");
	
		System.out.println("Début des tests sur le post traitement");
		
		Crop google =  new Crop(0,0,323,341,45);
		Crop src =  new Crop(100,100,390,390);
		
		@SuppressWarnings("unused")
		File result = test.postProcessing(new File("./pictures/sources/paysage.jpg"),src, new File("./pictures/sources/pacman2.jpg"),google);
	

		System.out.println("Fin des tests sur le post traitement");
	}

}

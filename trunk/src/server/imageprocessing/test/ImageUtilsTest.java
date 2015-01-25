package server.imageprocessing.test;

import server.imageprocessing.Crop;
import server.imageprocessing.processing.ContourDetection;
import server.imageprocessing.processing.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtilsTest {

	private ImageUtilsTest() {
	}

	/**
	 * ..
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Test Contour detection
		BufferedImage result = null;
		try {
			result = ContourDetection.eroseDetection(ImageIO.read(new File("./pictures/source/pacman2.jpg")), 4);
			result = ImageUtils.rotate(result, 45);
			ImageUtils.saveimageasJpeg(result,  new FileOutputStream("./pictures/results/imageUtils/result.jpg"), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Test cut Image
		try {
			ImageUtils.saveimageasJpeg(
					ImageUtils.cut(ImageIO.read(new File("./pictures/source/pacman2.jpg")), new Crop(20, 20, 100, 100)),
					new FileOutputStream("./pictures/results/imageUtils/resultCut.jpg"), 100);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}

	}

}

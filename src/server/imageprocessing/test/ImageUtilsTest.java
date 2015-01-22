package server.imageprocessing.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.imageprocessing.Crop;
import server.imageprocessing.processing.ImageUtils;

public class ImageUtilsTest {

	private ImageUtilsTest() {
	}

	public static void main(String[] args) {
		
		//Test Contour detection
		BufferedImage result = null;
		try {
			result = (BufferedImage) ImageUtils.contourDetection(ImageIO.read(new File("./GoodPacman.jpg")), 3);
			ImageUtils.saveImageAsJPEG(result,  new FileOutputStream("./result.jpg"), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Test cut Image
		try {
			ImageUtils.saveImageAsJPEG(
					ImageUtils.cut(ImageIO.read(new File("./GoodPacman.jpg")), new Crop(20, 20, 100, 100)),
					new FileOutputStream("./resultCut.jpg"), 100);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}

	}

}

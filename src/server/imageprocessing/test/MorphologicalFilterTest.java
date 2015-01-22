package server.imageprocessing.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.imageprocessing.processing.MorphologicalFilter;

/**
 * ..
 * 
 * @author fabie_000
 *
 */
public final class MorphologicalFilterTest {
	
	/**
	 * ..
	 */
	static final int SIX = 6;
	
	/**
	 * 
	 */
	private MorphologicalFilterTest() {
		
	}

	/**
	 * ..
	 * 
	 * @param args
	 * ..
	 * @throws IOException
	 * ..
	 */
	public static void main(String[] args) throws IOException {
		
		Image i = ImageIO.read(new File("C:/Users/fabie_000/Desktop/nuages/nuages_means.jpeg"));
		
		BufferedImage buffImage =  MorphologicalFilter.closing((BufferedImage) i, SIX);
		
		File o = new File("C:/Users/fabie_000/Desktop/nuages/nuages_morpho.jpg");
		ImageIO.write(buffImage, "jpg", o);	
	}
}

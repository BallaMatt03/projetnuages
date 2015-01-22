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
		
		File directorySource = new File("./pictures/results/morphologicalFilter/sources/");
		
		for(String s : directorySource.list()) {
			
			Image i = ImageIO.read(new File("./pictures/results/morphologicalFilter/sources/" + s));

			File opening = new File("./pictures/results/morphologicalFilter/results/opening/" + s.split("\\.")[0] + "_opening.jpg");
			ImageIO.write(MorphologicalFilter.opening((BufferedImage) i, SIX), "jpg", opening);	
			
			File closing = new File("./pictures/results/morphologicalFilter/results/closing/" + s.split("\\.")[0] + "_closing.jpg");
			ImageIO.write(MorphologicalFilter.closing((BufferedImage) i, SIX), "jpg", closing);	
		}
	}
}

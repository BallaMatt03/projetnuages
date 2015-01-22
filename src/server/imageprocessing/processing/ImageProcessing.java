/**
 * 
 */
package server.imageprocessing.processing;

import java.io.File;
import java.util.List;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	public File preProcessing(File image, Crop crop) {
		// TODO k-means, dilation, erosion
		return null;
	}

	@Override
	public List<File> postProcessing(File image, Crop crop, List<File> google) {
		// TODO Auto-generated method stub
		return null;
	}

}

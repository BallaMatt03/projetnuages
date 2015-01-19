package server.imageprocessing;

import java.io.File;
import java.util.List;

/**
 * @author Thomas
 *
 */
public interface IImageProcessing  {

	/**
	 * 
	 * @param image Full image
	 * @param crop Coordinates to crop
	 * @return The processed crop part
	 */
    File preProcessing(File image, Crop crop);

    /**
     * 
     * @param image Full image
     * @param crop Coordinates of the crop
     * @param google The results from google
     * @return  The processed images
     */
    List<File> postProcessing(File image, Crop crop, List<File> google);
}

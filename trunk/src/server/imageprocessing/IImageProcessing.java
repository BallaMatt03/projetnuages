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
    /**
     * 
     * @param image
     * 				Full image
     * @param crop
     * 				Coordinates of the crop
     * @param google
     * 				The results from google
     * @param cropGoogle
     * 				Coordinates of the crop for google image
     * @return
     * 				The processed images
     */
    File postProcessing(File image, Crop crop, File google, Crop cropGoogle);
}

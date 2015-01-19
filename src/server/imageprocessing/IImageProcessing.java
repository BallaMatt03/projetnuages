/**
 * 
 */
package server.imageprocessing;

import java.io.File;
import java.util.List;

/**
 * @author Thomas
 *
 */
public interface IImageProcessing {

    /**
     * Pre-process the full image for google
     *
     * @param Full image
     * @param Coordinates to crop
     *
     * @return The processed crop part
     */
    public File preProcessing(File image, Crop crop);

    /**
     * Post-process the results from google
     *
     * @param Full image
     * @param Coordinates of the crop
     * @param The results from google
     *
     * @return The processed images
     */
    public List<File> postProcessing(File image, Crop crop, List<File> google);
}

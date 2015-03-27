# Introduction #

This following document describes the interface of the ImageProcessing Package


# Documentation #

```

namespace server.imageprocessing;

/*
 * The interface to interact with the image processing part
 */
interface ImageProcessing
{
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


/*
 * A utility class to manage crops coordinates
 */
class Crop 
{
	public int xStart;
	public int xEnd;

	public int yStart;
	public int yEnd;
}


```

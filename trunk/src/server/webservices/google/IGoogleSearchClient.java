package server.webservices.google;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface IGoogleSearchClient {

	/**
	 * Returns an ArrayList of BufferedImage from a keyword.
	 * This method use Google search image.
	 *
	 * @param  keyword The String to search
	 * @return An arraylist which contain all the matching image
	 */
	public abstract ArrayList<BufferedImage> getImagesFromKeyword(String keyword);

}
package server.webservices.nuage.services;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to help manage files.
 * 
 * @author Cedric Boulet Kessler
 */
public final class FileHandler {
	
	/**
	 * Default constructor.
	 */
	private FileHandler() { }
	
	/**
	 * Load the file at the given path.
	 * 
	 * @param path The path to load the file
	 * 
	 * @return The loaded file
	 */
	public static File loadFile(String path) {
		return new File(path);
	}
	
	/**
	 * Scan the given directory to find all images.
	 * 
	 * @param path The path to the directory to scan
	 * 
	 * @return The absolute path of all images
	 */
	public static List<String> scanDir(String path) {
		List<String> imagesPath = new ArrayList<>();
		
		File repertoire = new File(path);
		File[] files = repertoire.listFiles(jpegFileFilter);
		
		for (File image : files) {
			imagesPath.add(image.getAbsolutePath());
		}
		
		return imagesPath;
	}
	
	/**
	 * A filter to retrieve only jpeg file.
	 */
	private static FilenameFilter jpegFileFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.endsWith(".jpeg");
		}
	};
	
}

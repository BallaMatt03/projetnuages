package server.webservices.nuage.services;

import java.io.File;
import java.util.List;

/**
 * Interface to help manage files.
 * 
 * @author Cedric Boulet Kessler
 */
public interface IFileHandler {
	
	/**
	 * Load the file at the given path.
	 * 
	 * @param path The path to load the file
	 * 
	 * @return The loaded file
	 */
	public abstract File loadFile(String path);
	
	/**
	 * Scan the given directory to find all images.
	 * 
	 * @param path The path to the directory to scan
	 * 
	 * @return The absolute path of all images
	 */
	public abstract List<String> scanDir(String path);
	
}

package server.webservices.nuage.services;

import java.io.File;
import java.io.InputStream;
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
	
	/**
	 * Transform a stream type to a standard file type.
	 * 
	 * @param stream The stream to transform
	 * @param path Where to store the temp file
	 * 
	 * @return The normalized file
	 */
	public abstract File transformStreamToFile(InputStream stream, String path);
	
}

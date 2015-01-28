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
public final class FileHandler implements IFileHandler {
	
	/**
	 * Default constructor.
	 */
	public FileHandler() { }
	
	/* (non-Javadoc)
	 * @see server.webservices.nuage.services.IFileHandler#loadFile()
	 */
	@Override
	public File loadFile(String path) {
		return new File(path);
	}
	
	/* (non-Javadoc)
	 * @see server.webservices.nuage.services.IFileHandler#scanDir()
	 */
	public List<String> scanDir(String path) {
		List<String> imagesPath = new ArrayList<>();
		
		File dir = new File(path);
		File[] files = dir.listFiles(jpegFileFilter);
		
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
			return name.endsWith(".jpeg") || name.endsWith(".jpg");
		}
	};
	
}

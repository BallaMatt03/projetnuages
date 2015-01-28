package server.webservices.nuage.services;

import java.io.File;
import java.util.List;

import server.webservices.nuage.model.Catalog;
import server.webservices.nuage.model.CatalogEntry;

/**
 * Class to help create Catalog.
 * 
 * @author Cedric Boulet Kessler
 */
public final class CatalogFactory implements ICatalogFactory {
	
	/**
	 * The path where to find the images
	 */
	private String dirPath;
	
	/**
	 * The where url to find images
	 */
	private String hostName;
	
	/**
	 * A helper service to handle files and dir
	 */
	private IFileHandler fileHandler;
	
	/**
	 * Default constructor.
	 * 
	 * @param dirPath     The path where to find the images
	 * @param hostName    The where url to find images
	 * @param fileHandler A helper service to handle files and dir
	 */
	public CatalogFactory(String dirPath, String hostName, IFileHandler fileHandler) { 
		this.dirPath = dirPath;
		this.hostName = hostName;
		this.fileHandler = fileHandler;
	}

	/* (non-Javadoc)
	 * @see server.webservices.nuage.services.ICatalogueFactory#createCatalog()
	 */
	@Override
	public Catalog createCatalog() {
		Catalog catalog = new Catalog();
		
		List<String> imagesPath = fileHandler.scanDir(dirPath);
		
		for (int i = 0; i < imagesPath.size(); i++) {
			File file = fileHandler.loadFile(imagesPath.get(i));
			catalog.getImages().add(
					new CatalogEntry(
						i,
						file.getName(),
						buildUrl(i)));
		}
		
		return catalog;
	}
	
	/**
	 * Helper method to generate the image url.
	 * 
	 * @param number The number of the image in the collection
	 * 
	 * @return The formatted url
	 */
	private String buildUrl(int number) {
		return hostName + number;
	}
	
}

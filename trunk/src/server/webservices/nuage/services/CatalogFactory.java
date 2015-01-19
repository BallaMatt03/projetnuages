package server.webservices.nuage.services;

import java.io.File;
import java.util.List;

import server.webservices.nuage.Config;
import server.webservices.nuage.model.Catalog;
import server.webservices.nuage.model.CatalogEntry;

/**
 * Class to help create Catalog.
 * 
 * @author Cedric Boulet Kessler
 */
public final class CatalogFactory {
	
	/**
	 * Default constructor.
	 */
	private CatalogFactory() { }

	/**
	 * Factory method to create and fill a catalog.
	 * 
	 * @param path The path to find images
	 * 
	 * @return The filled catalog
	 */
	public static Catalog createCatalog(String path) {
		Catalog catalog = new Catalog();
		
		List<String> imagesPath = FileHandler.scanDir(path);
		
		for (int i = 0; i < imagesPath.size(); i++) {
			File file = new File(imagesPath.get(i));
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
	private static String buildUrl(int number) {
		return Config.HOST + "nuage/?number=" + number;
	}
	
}

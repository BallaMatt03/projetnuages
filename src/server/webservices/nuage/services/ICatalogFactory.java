package server.webservices.nuage.services;

import server.webservices.nuage.model.Catalog;

/**
 * Interface to help create Catalog.
 * 
 * @author Cedric Boulet Kessler
 */
public interface ICatalogFactory {

	/**
	 * Factory method to create and fill a catalog.
	 * 
	 * @param path The path to find images
	 * 
	 * @return The filled catalog
	 */
	public abstract Catalog createCatalog();

}

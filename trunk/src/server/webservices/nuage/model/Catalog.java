package server.webservices.nuage.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The model representing all available images.
 * 
 * @author Cedric Boulet Kessler
 */
@XmlRootElement
public class Catalog {

	/**
	 * The list of all images.
	 */
	private List<CatalogEntry> images;
	
	/**
	 * Default constructor.
	 */
	public Catalog() {
		images = new ArrayList<>();
	}

	/**
	 * Get all images collection.
	 * 
	 * @return the image collection
	 */
	public final List<CatalogEntry> getImages() {
		return images;
	}

	/**
	 * Set the image collection.
	 * 
	 * @param images a new collection of images
	 */
	public final void setImages(List<CatalogEntry> images) {
		this.images = images;
	}
	
}

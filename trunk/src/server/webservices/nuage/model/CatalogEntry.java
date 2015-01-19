package server.webservices.nuage.model;

/**
 * The model representing an entry for one image.
 * 
 * @author Cedric Boulet Kessler
 */
public class CatalogEntry {

	/**
	 * The position in the collection.
	 */
	private int number;

	/**
	 * The name of the image.
	 */
	private String name;
	
	/**
	 * The url of the image.
	 */
	private String url;
	
	/**
	 * Default constructor.
	 */
	public CatalogEntry() {
		this(0, "", "");
	}

	/**
	 * Initialize all class properties.
	 * 
	 * @param number The position in the collection.
	 * @param name   The name of the image.
	 * @param url    The url of the image.
	 */
	public CatalogEntry(int number, String name, String url) {
		this.number = number;
		this.name = name;
		this.url  = url;
	}

	/**
	 * Get the number.
	 * 
	 * @return The number value
	 */
	public final int getNumber() {
		return number;
	}

	/**
	 * Set the number.
	 * 
	 * @param number The new value
	 */
	public final void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Get the name.
	 * 
	 * @return The name value.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Set the name.
	 * 
	 * @param name The new value
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the url.
	 * 
	 * @return The url value
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * Set the url.
	 * 
	 * @param url The new value
	 */
	public final void setUrl(String url) {
		this.url = url;
	}
}

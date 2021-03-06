package server.webservices.nuage.services;

import java.util.HashMap;
import java.util.Map;

import server.imageprocessing.IImageProcessing;
import server.imageprocessing.processing.ImageProcessing;
import server.webservices.Config;
import server.webservices.google.GoogleSearchClient;
import server.webservices.google.IGoogleSearchClient;
import server.webservices.nuage.NuageService;

/**
 * Class to help instantiate services.
 * 
 * @author Cedric Boulet Kessler
 */
public class ServiceFactory {
	
	/**
	 * Container to hold services in order to avoid multiple instances.
	 */
	private static Map<ServicesCollection, Object> servicesContainer;
	
	/**
	 * List of services available.
	 */
	private enum ServicesCollection {
		FileHandler,
		CatalogFactory,
		ImageProcessing,
		GoogleSearchClient,
		ZipManager
	}
	
	public ServiceFactory() {
		servicesContainer = new HashMap<>();
	}
	
	/**
	 * Helper to instantiate NuageService service.
	 *  
	 * @return The instantiated service
	 */
	public NuageService createNuageService() {
		return new NuageService(createImageProcessing(), 
				createFileHandler(), 
				createCatalogFactory(),
				createGoogleSearchClient(),
				Config.IMAGES_PATH,
				createZipManager());
	}

	/**
	 * Helper to instantiate CatalogFactory service.
	 *  
	 * @return The instantiated service
	 */
	public ICatalogFactory createCatalogFactory() {
		if (!servicesContainer.containsKey(ServicesCollection.CatalogFactory)) {
			servicesContainer.put(
					ServicesCollection.CatalogFactory, 
					new CatalogFactory(Config.IMAGES_PATH, 
										Config.HOST + "nuage/images/?number=", 
										createFileHandler()));
		}
		
		return (ICatalogFactory) servicesContainer.get(ServicesCollection.CatalogFactory);
	}

	/**
	 * Helper to instantiate FileHandler service.
	 *  
	 * @return The instantiated service
	 */
	public IFileHandler createFileHandler() {
		if (!servicesContainer.containsKey(ServicesCollection.FileHandler)) {
			servicesContainer.put(
					ServicesCollection.FileHandler, 
					new FileHandler());
		}
		
		return (IFileHandler) servicesContainer.get(ServicesCollection.FileHandler);
	}
	
	/**
	 * Helper to instantiate GoogleSearchClient service.
	 *  
	 * @return The instantiated service
	 */
	public IGoogleSearchClient createGoogleSearchClient() {
		if (!servicesContainer.containsKey(ServicesCollection.GoogleSearchClient)) {
			servicesContainer.put(
					ServicesCollection.GoogleSearchClient, 
					new GoogleSearchClient());
		}
		
		return (IGoogleSearchClient) servicesContainer.get(ServicesCollection.GoogleSearchClient);
	}
	
	/**
	 * Helper to instantiate ImageProcessing service.
	 *  
	 * @return The instantiated service
	 */
	public IImageProcessing createImageProcessing() {
		if (!servicesContainer.containsKey(ServicesCollection.ImageProcessing)) {
			servicesContainer.put(
					ServicesCollection.ImageProcessing, 
					new ImageProcessing());
		}
		
		return (IImageProcessing) servicesContainer.get(ServicesCollection.ImageProcessing);
	}
	
	/**
	 * Helper to instantiate ZipManager service.
	 * 
	 * @return The instantiated service
	 */
	public IZipManager createZipManager() {
		if (!servicesContainer.containsKey(ServicesCollection.ZipManager)) {
			servicesContainer.put(
					ServicesCollection.ZipManager, 
					new ZipManager());
		}
		
		return (IZipManager) servicesContainer.get(ServicesCollection.ZipManager);
	}
}

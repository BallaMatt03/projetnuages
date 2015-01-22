package server.webservices.nuage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;
import server.webservices.Config;
import server.webservices.nuage.services.CatalogFactory;
import server.webservices.nuage.services.FileHandler;
import server.webservices.nuage.services.ICatalogFactory;
import server.webservices.nuage.services.IFileHandler;

/**
 * Represent the nuage webservice server.
 * 
 * @author Cedric Boulet Kessler
 */
public class Server {
	
	/**
	 * Container to hold services in order to avoid multiple instances
	 */
	private static Map<ServicesCollection, Object> servicesContainer;
	
	/**
	 * List of services available
	 */
	private enum ServicesCollection {
		FileHandler,
		CatalogFactory,
		ImageProcessing,
		GoogleClient
	};
	
	/**
	 * Default constructor.
	 */
	public Server() { 
		servicesContainer = new HashMap<>();
	}
	
	/**
	 * Method to start the server.
	 */
	public void start() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	    
        sf.setResourceClasses(NuageService.class);
        sf.setResourceProvider(
        		NuageService.class, 
        		new SingletonResourceProvider(createNuageService()));
        
        sf.setAddress(Config.HOST);
        sf.create();
	}
	
	/**
	 * Helper to instantiate NuageService service
	 *  
	 * @return The instantiated service
	 */
	private static NuageService createNuageService() {
		return new NuageService(createImageProcessingMock(), createFileHandler(), createCatalogFacory());
	}

	/**
	 * Helper to instantiate CatalogFactory service
	 *  
	 * @return The instantiated service
	 */
	private static ICatalogFactory createCatalogFacory() {
		if (!servicesContainer.containsKey(ServicesCollection.CatalogFactory)) {
			servicesContainer.put(
					ServicesCollection.CatalogFactory, 
					new CatalogFactory(Config.IMAGES_PATH, 
										Config.HOST + "nuages/?number=", 
										createFileHandler()));
		}
		
		return (ICatalogFactory) servicesContainer.get(ServicesCollection.CatalogFactory);
	}

	/**
	 * Helper to instantiate FileHandler service
	 *  
	 * @return The instantiated service
	 */
	private static IFileHandler createFileHandler() {
		if (!servicesContainer.containsKey(ServicesCollection.FileHandler)) {
			servicesContainer.put(
					ServicesCollection.FileHandler, 
					new FileHandler());
		}
		
		return (IFileHandler) servicesContainer.get(ServicesCollection.FileHandler);
	}

	/**
	 * Helper to instantiate ImageProcessing service
	 *  
	 * @return The instantiated service
	 */
	private static IImageProcessing createImageProcessingMock() {
		if (!servicesContainer.containsKey(ServicesCollection.ImageProcessing)) {
			servicesContainer.put(
					ServicesCollection.ImageProcessing, 
					new IImageProcessing() {
						
						@Override
						public File preProcessing(File image, Crop crop) {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public List<File> postProcessing(File image, Crop crop, List<File> google) {
							// TODO Auto-generated method stub
							return null;
						}
					});
		}
		
		return (IImageProcessing) servicesContainer.get(ServicesCollection.ImageProcessing);
	}
	
}

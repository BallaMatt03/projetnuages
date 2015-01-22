package server.webservices.nuage;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import server.webservices.Config;
import server.webservices.nuage.services.ServiceFactory;

/**
 * Represent the nuage webservice server.
 * 
 * @author Cedric Boulet Kessler
 */
public class Server {
	
	private static ServiceFactory factory;
	
	/**
	 * Method to start the server.
	 */
	public void start() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	    
        sf.setResourceClasses(NuageService.class);
        sf.setResourceProvider(
        		NuageService.class, 
        		new SingletonResourceProvider(getFactory().createNuageService()));
        
        sf.setAddress(Config.HOST);
        sf.create();
	}
	
	/**
	 * Helper method to instatiate the factory in singleton.
	 * 
	 * @return The singleton factory
	 */
	private static ServiceFactory getFactory() {
		if (factory == null) {
			factory = new ServiceFactory();
		}
		
		return factory;
	}
	
}

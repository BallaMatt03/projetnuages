package server.webservices.nuage;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;

/**
 * Represent the nuage webservice server.
 * 
 * @author Cedric Boulet Kessler
 */
public final class Server {
	
	/**
	 * Forbid call to constructor.
	 */
	private Server() { }
	
	/**
	 * Method to start the server.
	 */
	public static void start() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	    
        sf.setResourceClasses(NuageService.class);
        sf.setResourceProvider(
        		NuageService.class, 
        		new SingletonResourceProvider(new NuageService(new IImageProcessing() {
					
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
				})));
        
        sf.setAddress(Config.HOST);
        sf.create();

		System.out.println("Type a char then <return> to stop the server");	
		Scanner sc = new Scanner(System.in);
		sc.next();
		sc.close();
		
		System.out.println("Server stopped !");
	}
	
}

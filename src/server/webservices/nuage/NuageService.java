package server.webservices.nuage;

import java.io.File;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;
import server.webservices.Config;
import server.webservices.nuage.model.Catalog;
import server.webservices.nuage.services.ICatalogFactory;
import server.webservices.nuage.services.IFileHandler;

/**
 * Ressources avalaible on the nuage webservice.
 * 
 * @author Cedric Boulet Kessler
 */
@Path("images")
public class NuageService {

	/**
	 * The used image processing service.
	 */
	private IImageProcessing imageProcessing;
	
	/**
	 * A helper service to handle files and dir
	 */
	private IFileHandler fileHandler;
	
	/**
	 * A helper service to create the catalog
	 */
	private ICatalogFactory catalogFactory;
	
	/**
	 * Default constructor.
	 * 
	 * @param imageProcessing The image processing service to be used
	 * @param fileHandler     A helper service to handle files and dir
	 * @param catalogFactory  A helper service to create the catalog
	 */
	public NuageService(IImageProcessing imageProcessing,
			IFileHandler fileHandler,
			ICatalogFactory catalogFactory) {
		
		this.imageProcessing = imageProcessing;
		this.fileHandler = fileHandler;
		this.catalogFactory = catalogFactory;
	}
	
	// GET /images application/xml
	/**
	 * Give the catalog of images available on the server in XML format.
	 * 
	 * @return The catalog in XML
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public final Catalog getCatalogue() {
		return catalogFactory.createCatalog();
	}
	
	// GET /images?number={number:int} image/jpeg
	/**
	 * Give the requested image.
	 * 
	 * @param number The number of the requested image
	 * 
	 * @return The requested image
	 */
	@GET
	@Produces("image/jpeg")
	public final File getImage(@QueryParam("number") String number) {
		List<String> imagesPath = fileHandler.scanDir(Config.IMAGES_PATH);
		
		File image = fileHandler.loadFile(imagesPath.get(Integer.parseInt(number)));
		
		return image;
	}
	
	// GET /findShapes?keyword={keyword:string}
	/**
	 * Give the google results associated to the given keyword.
	 * 
	 * @param keyword The keyword to send to google
	 * 
	 * @return A zipped collection of images (google return)
	 */
	@GET
	@Produces("application/zip")
	public final File findShapes(@QueryParam("keyword") String keyword) {
		return null;
	}
	
	// POST /merge?image={number:int}&xStart={xStart:int}&yStart={yStart:int}&xEnd={xEnd:int}&yEnd={yEnd:int} application/zip
	/**
	 * Give the merge image with google result.
	 * 
	 * @param number   The number of the requested image
	 * @param xStart   The X start coordinate
	 * @param xEnd     The X end coordinate
	 * @param yStart   The y start coordinate
	 * @param yEnd     The y end coordinate
	 * @param selected The selected file from google results
	 * 
	 * @return The merge file with the google result
	 */
	@POST
	@Produces("image/jpeg")
	public final File merge(@QueryParam("number") String number,
			@QueryParam("xStart") String xStart,
			@QueryParam("xEnd") String xEnd,
			@QueryParam("yStart") String yStart,
			@QueryParam("yEnd") String yEnd,
			Attachment selected) {
		File image = getImage(number);
		
		Crop crop = new Crop(
				Integer.parseInt(xStart),
				Integer.parseInt(yStart),
				Integer.parseInt(xEnd) - Integer.parseInt(xStart),
				Integer.parseInt(yEnd) - Integer.parseInt(yStart));
		
		return null;
	}
}

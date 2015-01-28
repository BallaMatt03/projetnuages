package server.webservices.nuage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import server.imageprocessing.Crop;
import server.imageprocessing.IImageProcessing;
import server.webservices.google.IGoogleSearchClient;
import server.webservices.nuage.model.Catalog;
import server.webservices.nuage.services.ICatalogFactory;
import server.webservices.nuage.services.IFileHandler;

/**
 * Ressources avalaible on the nuage webservice.
 * 
 * @author Cedric Boulet Kessler
 */
@Path("nuage")
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
	 * A helper service to handle google custom search
	 */
	private IGoogleSearchClient googleSearchClient;
	
	/**
	 * The path where to find the images
	 */
	private String imagePath;
	
	/**
	 * Default constructor.
	 * 
	 * @param imageProcessing    The image processing service to be used
	 * @param fileHandler        A helper service to handle files and dir
	 * @param catalogFactory     A helper service to create the catalog
	 * @param googleSearchClient A helper service to handle google custom search
	 * @param imagePath          The path where to find the images
	 */
	public NuageService(IImageProcessing imageProcessing,
			IFileHandler fileHandler,
			ICatalogFactory catalogFactory,
			IGoogleSearchClient googleSearchClient,
			String imagePath) {
		
		this.imageProcessing = imageProcessing;
		this.fileHandler = fileHandler;
		this.catalogFactory = catalogFactory;
		this.googleSearchClient = googleSearchClient;
		this.imagePath = imagePath;
	}
	
	// GET /images application/xml
	/**
	 * Give the catalog of images available on the server in XML format.
	 * 
	 * @return The catalog in XML
	 */
	@GET
	@Path("images")
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public final Catalog getCatalog() {
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
	@Path("images")
	@Produces("image/jpeg")
	public final File getImage(@QueryParam("number") String number) {
		List<String> imagesPath = fileHandler.scanDir(imagePath);
		
		int imageNumber = Integer.parseInt(number);
		
		if (imageNumber < 0 || imageNumber >= imagesPath.size()) {
			throw new NotFoundException("The requested image number doesn't exists !");
		}
		
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
	@Path("findShapes")
	@Produces("application/zip")
	public final File findShapes(@QueryParam("keyword") String keyword) {
		
		List<BufferedImage> images = googleSearchClient.getImagesFromKeyword(keyword);
		
		File zip = null;// zip the file
		
		return zip;
	}
	
	// POST /merge?image={number:int}
	// 		&imgXStart={imgXStart:int}&imgYStart={imgYStart:int}&imgXEnd={imgXEnd:int}&imgYEnd={imgYEnd:int} 
	//		&googleXStart={googleXStart:int}&googleYStart={googleYStart:int}&googleXEnd={googleXEnd:int}&googleYEnd={googleYEnd:int}
	//		application/zip
	/**
	 * Give the merge image with google result.
	 * 
	 * @param number    The number of the requested image
	 * @param imgXStart The X start coordinate for source image
	 * @param imgXEnd   The X end coordinate for source image
	 * @param imgYStart The y start coordinate for source image
	 * @param imgYEnd   The y end coordinate for source image
	 * @param googleXStart The X start coordinate for google image
	 * @param googleXEnd   The X end coordinate for google image
	 * @param googleYStart The y start coordinate for google image
	 * @param googleYEnd   The y end coordinate for google image
	 * @param googleSelectedImage The selected file from google results
	 * 
	 * @return The merge file with the google result
	 */
	@POST
	@Path("merge")
	@Produces("image/jpeg")
	public final File merge(@QueryParam("number") String number,
			@QueryParam("imgXStart") String imgXStart,
			@QueryParam("imgXEnd") String imgXEnd,
			@QueryParam("imgYStart") String imgYStart,
			@QueryParam("imgYEnd") String imgYEnd,
			@QueryParam("googleXStart") String googleXStart,
			@QueryParam("googleXEnd") String googleXEnd,
			@QueryParam("googleYStart") String googleYStart,
			@QueryParam("googleYEnd") String googleYEnd,
			Attachment googleSelectedImage) {
		File image = getImage(number);
		File google = null; // retrieve from attachment
		
		Crop imageCrop = new Crop(
				Integer.parseInt(imgXStart),
				Integer.parseInt(imgYStart),
				Integer.parseInt(imgXEnd) - Integer.parseInt(imgXStart),
				Integer.parseInt(imgYEnd) - Integer.parseInt(imgYStart));
		Crop googleCrop = new Crop(
				Integer.parseInt(googleXStart),
				Integer.parseInt(googleYStart),
				Integer.parseInt(googleXEnd) - Integer.parseInt(googleXStart),
				Integer.parseInt(googleYEnd) - Integer.parseInt(googleYStart));
		
		File merged = imageProcessing.postProcessing(image, imageCrop, google, googleCrop);
		
		return merged;
	}
}

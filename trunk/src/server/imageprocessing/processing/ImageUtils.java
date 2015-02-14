package server.imageprocessing.processing;

import com.mortennobel.imagescaling.experimental.ImprovedMultistepRescaleOp;
import server.imageprocessing.Crop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;




/**
 * Basic functions for Image processing (Save, Resize, Rotate...).
 * @author Thomas, Vaïk
 *
 */
public final class ImageUtils {

	/**
	 * Simple constructor.
	 */
	private ImageUtils() { }
	
	/**
	 * Get subimage.
	 * @param srcImage
	 * 			source Image
	 * @param pcrop
	 * 			position of subImage
	 * @return 
	 * 			subimage
	 * @throws IllegalArgumentException 
	 * 			if the Crop.height or Crop.Widht is null.
	 */
	public static BufferedImage cut(final BufferedImage srcImage, final Crop pcrop) 
			throws IllegalArgumentException {
		BufferedImage temp = srcImage;
		
		if (pcrop.getHeight() == 0 || pcrop.getWidth() == 0) {
			throw new IllegalArgumentException("Les dimensions"
					+ " doivent être supèrieurs à zéro!");
		}
		
		return temp.getSubimage(
				pcrop.getStartX(),
				pcrop.getStartY(),
				pcrop.getWidth(),
				pcrop.getHeight());
	}
	
	/**
	 * ..
	 * @param image
	 * ..
	 * @param angle
	 * ..
	 * @return
	 * ..
	 */
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle));
	    double cos = Math.abs(Math.cos(angle));
	    int width = image.getWidth();
	    int height = image.getHeight();
	    int neww = (int)Math.floor(width * cos + height * sin);
	    int newh = (int)Math.floor(height * cos + width * sin);
	    
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        GraphicsConfiguration[] gc = defaultScreen.getConfigurations();
	    
	    BufferedImage result = gc[0].createCompatibleImage(neww, newh);
	    Graphics2D graph = result.createGraphics();
	    graph.translate((neww - width) / 2, (newh - height) / 2);
	    // la rotation se fait avec un angle en radian et non en degre
	    double angleRadian = angle * Math.PI / 180.0;
	    graph.rotate(angleRadian, width / 2, height / 2);
	    graph.drawRenderedImage(image, null);
	    graph.dispose();
	    return result;
	}
	
	
	/**
	 * Convert Image to BufferedImage.
	 * @param image
	 * 		 	image to convert
	 * @return 
	 * 			BufferedImage
	 */
    public static BufferedImage toBufferedImage(Image image) {
    	
        if (image instanceof BufferedImage) {
        	return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;

            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();

            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) { 
        	System.out.println("Erreur lors de la convertion!");
        } //No screen

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;

            if (hasAlpha) {
            	type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics graph = bimage.createGraphics();

        // Paint the image onto the buffered image
        graph.drawImage(image, 0, 0, null);
        graph.dispose();

        return bimage;
    }


	/**
	 * Test if the image support alpha.
	 * @param image
	 *            image to test
	 * @return
	 *            Boolean
	 */
    public static boolean hasAlpha(Image image) {
    	
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
			return ((BufferedImage) image).getColorModel().hasAlpha();
		}

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        	System.out.println("Erreur lors de la récupération du color model");
        }

        // Get the image's color model
        return pg.getColorModel().hasAlpha();
    }
	
    /**
   * Writes an image to an output stream as a JPEG file. The JPEG quality can
   * be specified in percent.
   * 
   * @param image
   *            image to be written
   * @param stream
   *            target stream
   * @param qualityPercent
   *            JPEG quality in percent
   * 
   * @throws IOException
   *             if an I/O error occured
   * @throws IllegalArgumentException
   *             if qualityPercent not between 0 and 100
   */
    public static void saveimageasJpeg(BufferedImage image, OutputStream stream, int qualityPercent) throws IOException {
			  
			if ((qualityPercent < 0) || (qualityPercent > 100)) {
			  throw new IllegalArgumentException("La valeur du paramètre qualityPercent est incorrect.");
			}
			
			ImageWriter writer = null;
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
			if (iter.hasNext()) {
			  writer = (ImageWriter) iter.next();
			}
			ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
			writer.setOutput(ios);
			ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwparam.setCompressionQuality(qualityPercent / 100f);
			writer.write(null, new IIOImage(image, null, null), iwparam);
			ios.flush();
			writer.dispose();
			ios.close();
	  }
    
    /**
     * Resize an image according a new width and height.
     * 
     * @param image
     * 				image to be resized
     * @param width
     * 				new width of the image
     * @param height
     * 				new height of the image
     * @return
     */
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        ImprovedMultistepRescaleOp resampleOp = new ImprovedMultistepRescaleOp(width , height );
        return resampleOp.filter(image, null);
    }
    
    /**
     * Resize du contour d'une image.
     * @param image
     * 				image contenant le contour de l'image google
     * @param crop
     * 				crop de l'image de départ pour la mise à l'échelle
     * @return
     */
    public static BufferedImage resizeContour(BufferedImage image, Crop crop) {
    	int x0 = 0;
    	int x1 = 0;
    	int y0 = 0;
    	int y1 = 0;
    	boolean ok = false;
    	int lineIndex = 0;
    	int columnIndex = 0;
    	// On va récupérer 4 valeurs qui vont correspondre aux réels bords du contour
    	// On élimine ainsi les bords noirs superflus apparu après la rotation
    	while ( lineIndex < image.getWidth() && ! ok ) {
    		while ( columnIndex < image.getHeight() && ! ok ) {
    			if (  image.getRGB( lineIndex ,  columnIndex ) == Color.WHITE.getRGB() ) {
    				y0 = lineIndex;
    				ok = true;
    			}
    			columnIndex++;
    		}
    		lineIndex++;
    		columnIndex = 0;
    	}
    	
    	lineIndex = image.getWidth() - 1;
    	columnIndex = 0;
    	ok = false;
    	
    	while ( lineIndex >= 0 && ! ok ) {
    		while ( columnIndex < image.getHeight() && ! ok ) {
    			if ( image.getRGB(lineIndex , columnIndex) == Color.WHITE.getRGB() ) {
    				y1 = lineIndex;
    				ok = true;
    			}
    			columnIndex++;
    		}
    		lineIndex--;
    		columnIndex = 0;
    	}
    	
    	lineIndex = 0;
    	columnIndex = 0;
    	ok = false;
    	
    	while ( columnIndex < image.getHeight() && ! ok ) {
    		while ( lineIndex < image.getWidth() && ! ok ) {
    			if ( image.getRGB( lineIndex , columnIndex ) == Color.WHITE.getRGB() ) {
    				x0 = columnIndex;
    				ok = true;
    			}
    			lineIndex++;
    		}
    		columnIndex++;
    		lineIndex = 0;
    	}
    	
    	lineIndex = 0;
    	columnIndex = image.getHeight() - 1;
    	ok = false;
    	
    	while ( columnIndex >= 0 && ! ok ) {
    		while ( lineIndex < image.getWidth() && ! ok ) {
    			if ( image.getRGB(lineIndex, columnIndex) == Color.WHITE.getRGB() ) {
    				x1 = columnIndex;
    				ok = true;
    			}
    			lineIndex++;
    		}
    		columnIndex--;
    		lineIndex = 0;
    	}
    	
    	// On découpe l'image suivant les valeurs récupérées
    	BufferedImage newImage = image.getSubimage(x0, y0, x1 - x0, y1 - y0);
    	// On renvoie l'image mise à l'échelle
		return resize(newImage, crop.getWidth(), crop.getHeight()) ;
    }

}

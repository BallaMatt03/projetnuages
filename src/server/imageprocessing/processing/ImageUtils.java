package server.imageprocessing.processing;

import java.awt.Graphics;
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

import server.imageprocessing.Crop;


/**
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
	 * @param pCrop
	 * 			position of subImage
	 * @return 
	 * 			subimage
	 * @throws IllegalArgumentException 
	 * 			if the Crop.height or Crop.Widht is null.
	 */
	public static BufferedImage cut(final BufferedImage srcImage, final Crop pCrop) 
			throws IllegalArgumentException {
		BufferedImage temp = srcImage;
		
		if (pCrop.getHeight() == 0 || pCrop.getWidth() == 0) {
			throw new IllegalArgumentException("Les dimensions"
					+ " doivent être supèrieurs à zéro!");
		}
		
		return temp.getSubimage(
				pCrop.getStartX(),
				pCrop.getStartY(),
				pCrop.getWidth(),
				pCrop.getHeight());
	}
	
	/**
	 * 
	 * @param imageSource 
	 * 			source image
	 * @param precision
	 * 		 	coeff. of erode
	 * @return 
	 * 			image
	 */
	public static Image contourDetection(final BufferedImage imageSource, final int precision) {
		
		BufferedImage test = MorphologicalFilter.erose(imageSource, precision);
		BufferedImage erodePicture = test;
		BufferedImage src = imageSource;
		BufferedImage result = new BufferedImage(imageSource.getWidth(null), imageSource.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);

		/*
		try {
			saveImageAsJPEG(erodePicture, new FileOutputStream("./resulerodet.jpg"), 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		for (int i = 0; i < erodePicture.getWidth(null); i++) {
			for (int j = 0; j < erodePicture.getHeight(null); j++) {
				result.setRGB(i, j , src.getRGB(i ,  j) * src.getRGB(i,  j));
			}
		}
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
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

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
    public static void saveImageAsJPEG(BufferedImage image,
		  OutputStream stream, int qualityPercent) throws IOException {
			  
			if ((qualityPercent < 0) || (qualityPercent > 100)) {
			  throw new IllegalArgumentException("La valeur du paramètre qualityPercent est incorrect.");
			}
			
			float quality = qualityPercent / 100f;
			ImageWriter writer = null;
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
			if (iter.hasNext()) {
			  writer = (ImageWriter) iter.next();
			}
			ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
			writer.setOutput(ios);
			ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwparam.setCompressionQuality(quality);
			writer.write(null, new IIOImage(image, null, null), iwparam);
			ios.flush();
			writer.dispose();
			ios.close();
	  }

}

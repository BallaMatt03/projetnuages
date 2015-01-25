package server.imageprocessing.processing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Kmeans {
    BufferedImage imageTemp;
    boolean notTerminated;
    int loops;
    int changedPixels;
    int[] histogram;
    static int count = 0;
    int[] lowerbounds = new int[3];
    static int g = 0;
    int[] lb = new int[3];
    int[] ub = new int[3];
    int[] mean = new int[3];
    BufferedImage imaged;
    BufferedImage thresholdimage;
    BufferedImage dilateimage;

    /**
     * ..
     * @param args
     * ..
     * @throws IOException
     * ..
     */
	public static void main(String[] args) throws IOException {
		
		Image img = ImageIO.read(new File("./pictures/sources/coeur_nuage.jpg"));
		int[] histograms = new int[256];
		@SuppressWarnings("unused")
		Kmeans kmeans = new Kmeans((BufferedImage)img,3,histograms);
		
		
	}

	/**
	 * ..
	 * @param image
	 * ..
	 * @param bins
	 * ..
	 * @param histogram
	 * ..
	 * @throws IOException
	 * ..
	 */
    public Kmeans(BufferedImage image, int bins, int[] histogram)
            throws IOException {
        this.histogram = histogram;
        initialize(image, bins);
        calcbounds();

        // / calculateMean(histogram);
        processImage(image, bins);
        imaged = returnimage();
        imageWrite();
        for (int j = 0; j < 3; j++) {
            System.out.println("LB" + j + "=" + lb[j] + " UB" + j + "=" + ub[j]
                    + " Means" + j + "=" + mean[j]);
        }
    }

    /**
     * ..
     * @param image
     * ..
     * @param bins
     * ..
     */
    public void initialize(BufferedImage image, int bins) {
        imageTemp = image;
        notTerminated = true;
        mean[0] = 173;
        mean[1] = 100;
        mean[2] = 112;
    }

    /**
     * ..
     * @param image
     * ..
     * @param index
     * ..
     * @param bins
     * ..
     * @return
     * ..
     */
    public int createmean(BufferedImage image, int index, int bins) {
        int pixelindex = 0;
        int sum = 0;
        int value = 0;
        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                try {
                    pixelindex += 1;
                    if (pixelindex % bins == index) {
                        Color rgb = new Color(image.getRGB(w, h));
                        sum += rgb.getRed();
                        value += 1;
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sum / value;

    }

    /**
     * ..
     */
    public void calcbounds() {
        for (int j = 0; j < 3; j++) {
            int lb1 = calculatelb(j);
            int ub1 = calculateub(j);
            lowerbounds[j] = lb1;
            lb[j] = lb1;
            ub[j] = ub1;
        }

    }

    /**
     * ..
     * @param index
     * ..
     * @return
     * ..
     */
    private int calculatelb(int index) {
        int cmean = mean[index];
        int currentBound = 0;
        for (int i = 0; i < 3; i++) {
            if (cmean > mean[i]) {
                currentBound = Math.max((cmean + mean[i]) / 2, currentBound);
            }
        }
        return currentBound;
    }

    /**
     * ..
     * @param index
     * ..
     * @return
     * ..
     */
    private int calculateub(int index) {
        int cmean = mean[index];
        int currentBound = 255;
        for (int i = 0; i < 3; i++) {
            if (cmean < mean[i]) {
                currentBound = Math.min((cmean + mean[i]) / 2, currentBound);
            }
        }
        return currentBound;
    }

    /**
     * ..
     * @param image
     * ..
     * @param bins
     * ..
     */
    private void processImage(BufferedImage image, int bins) {
        int delta = 255 / (bins - 1);

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
            	
                Color rgb = new Color(image.getRGB(w, h));
                int grey = rgb.getRed();

                for (int i = 0; i < 3; i++) {
                    if (grey > lb[i] && grey < ub[i]) {
                        g = i * delta;
                        imageTemp.setRGB(w, h, (new Color(g, g, g)).getRGB());
                    } else {
                        imageTemp.setRGB(w, h, (new Color(g, g, g)).getRGB());
                    }
                }
            }
        }
    }

    /**
     * ..
     * @return
     * ..
     */
    public BufferedImage returnimage() {
        return imageTemp;
    }

    /**
     * ..
     * @throws IOException
     * ..
     */
    public void imageWrite() throws IOException {
        // BufferedImage img1=image_temp;
        ImageIO.write(imaged, "jpg", new File("output.jpg"));
        System.out.println("image write completed");
    }


}
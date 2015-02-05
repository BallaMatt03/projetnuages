package server.imageprocessing.processing;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Kmeans{ 
    BufferedImage original; 
    BufferedImage result; 
    Cluster[] clusters; 
    
    public Kmeans() {} 
     
    /**
     * Launch the calculation.
     * @param image
     * 			Source Image
     * @param coefficient
     * 			number of clusters
     * @return
     * 			result image
     */
    public BufferedImage calculate(BufferedImage image, int coefficient) { 
        long start = System.currentTimeMillis(); 
        int width = image.getWidth(); 
        int height = image.getHeight(); 
        // create clusters 
        clusters = createClusters(image,coefficient); 
        // create cluster lookup table 
        int[] lut = new int[width * height ]; 
        Arrays.fill(lut, -1); 
         
        // at first loop all pixels will move their clusters 
        boolean pixelChangedCluster = true; 
        // loop until all clusters are stable! 
        int loops = 0; 
        while (pixelChangedCluster) { 
            pixelChangedCluster = false; 
            loops++; 
            for (int y = 0 ; y < height ; y++) { 
                for (int x = 0 ; x < width ; x++) { 
                    int pixel = image.getRGB(x, y); 
                    Cluster cluster = findMinimalCluster(pixel); 
                    if (lut[ width * y + x ] != cluster.getId()) { 
                        // cluster changed 
                        if (lut[ width * y + x ] != -1) { 
                            // remove from possible previous  
                            // cluster 
                            clusters[lut[ width * y + x ]].removePixel( 
                                                        pixel); 
                        } 
                        // add pixel to cluster 
                        cluster.addPixel(pixel); 
                        // continue looping  
                        pixelChangedCluster = true; 
                        // update lut 
                        lut[ width * y + x ] = cluster.getId(); 
                    } 
                } 
            } 
        } 
        
        // create result image 
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); 
        
        for (int y = 0 ; y < height ; y++) { 
            for (int x  = 0 ; x < width ; x++) { 
                int clusterId = lut[ width * y + x ]; 
                result.setRGB(x, y, clusters[clusterId].getRGB()); 
            } 
        } 
        long end = System.currentTimeMillis(); 
        System.out.println("Clustered to " + coefficient + " clusters in " + loops  + " loops in " + ( end - start ) + " ms." ); 
        return result; 
    } 
     
    /**
     * Create a cluster.
     * @param image
     * 			source image
     * @param nbClust
     * 			coefficient
     * @return
     * 			array of clusters
     */
    public Cluster[] createClusters(BufferedImage image, int nbClust) { 
        // Here the clusters are taken with specific steps, 
        // so the result looks always same with same image. 
        // You can randomize the cluster centers, if you like. 
        Cluster[] result = new Cluster[nbClust]; 
        int dx = image.getWidth() / nbClust; 
        int dy = image.getHeight() / nbClust; 
        int tempX = 0; 
        int tempY = 0; 
        for (int i = 0 ; i < nbClust; i++) { 
            result[i] = new Cluster(i,image.getRGB(tempX, tempY)); 
            tempX += dx;
            tempY += dy; 
        } 
        return result; 
    } 
     
    /**
     * Find the minimal Cluster.
     * @param rgb
     * 			rgb value
     * @return
     * 			minimal cluster
     */
    private Cluster findMinimalCluster(int rgb) { 
        Cluster cluster = null; 
        int min = Integer.MAX_VALUE; 
        for (int i = 0 ; i < clusters.length ; i++) { 
            int distance = clusters[i].distance(rgb); 
            if (distance < min) { 
                min = distance; 
                cluster = clusters[i]; 
            } 
        } 
        return cluster; 
    }
     
    class Cluster { 
        int id; 
        int pixelCount; 
        int red; 
        int green; 
        int blue; 
        int reds; 
        int greens; 
        int blues; 
         
        public Cluster(int id, int rgb) { 
            int r = rgb >> 16&0x000000FF;  
            int g = rgb >> 8&0x000000FF;  
            int b = rgb >> 0&0x000000FF;  
            red = r; 
            green = g; 
            blue = b; 
            this.id = id; 
            addPixel(rgb); 
        } 
         
        public void clear() { 
            red = 0; 
            green = 0; 
            blue = 0; 
            reds = 0; 
            greens = 0; 
            blues = 0; 
            pixelCount = 0; 
        } 
         
        int getId() { 
            return id; 
        } 
         
        int getRGB() { 
            int r = reds / pixelCount; 
            int g = greens / pixelCount; 
            int b = blues / pixelCount; 
            if ( r < 220 && g < 220) {
            	r = 0;
            	g = 0;
            	b = 0;
            } else {
            	r = 255;
            	g = 255;
            	b = 255;            	
            }
            return 0xff000000|r<<16|g<<8|b; 
        } 
        
        void addPixel(int color) { 
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF; 
            reds += r; 
            greens += g; 
            blues += b;
            pixelCount++; 
            red   = reds / pixelCount; 
            green = greens / pixelCount; 
            blue  = blues / pixelCount; 
        } 
         
        void removePixel(int color) { 
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF;  
            reds -= r; 
            greens -= g; 
            blues -= b; 
            pixelCount--; 
            red   = reds / pixelCount; 
            green = greens / pixelCount; 
            blue  = blues / pixelCount; 
        } 
         
        int distance(int color) { 
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF;
            
            int rx = Math.abs( red - r ); 
            int gx = Math.abs( green - g ); 
            int bx = Math.abs( blue - b ); 
            return ( rx + gx + bx ) / 3; 
        } 
    } 
     
} 
package server.imageprocessing.processing;
import java.awt.Color;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Kmeans {

	public static void main(String[] args) throws IOException {
		Image monImage = ImageIO.read(new File("./images/coeur.jpg"));
		BufferedImage bIm = (BufferedImage) monImage;
		BufferedImage means = new BufferedImage(bIm.getWidth(), bIm.getHeight(), bIm.getType());
		Kmeans kmeans = new Kmeans(bIm,means);
		
		// Enregistrement d'image
		BufferedImage bMeans = kmeans.getMeans();
        ImageIO.write(bMeans, "jpg",new File("./images/kmeans.jpg"));
        System.out.println("fin");
	}
	
	int nn,mm, kk;
	int[][] data;
	int[][] means;
	int assign[];
	int count[];
	
	public Kmeans() {
		super();
		// TODO Auto-generated constructor stub
		
	}
	
	public Kmeans(BufferedImage ddata, BufferedImage mmeans) {
		
		Init(ddata, mmeans);
		
		//int step = 100;
		int n = estep();
		mstep();
		while (n!=0){
			n = estep();
			mstep();
			System.out.println(n);
		}
	}
	
	public void Init(BufferedImage ddata, BufferedImage mmeans) {
		nn = ddata.getHeight();
		mm = ddata.getWidth();
		
		System.out.println(nn + " " + mm);
		kk = mmeans.getHeight();
		
		// Convertit l'image en NG
		ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		BufferedImage imGray = op.filter(ddata,null);
		data = convertBufferedImageToTab(imGray);
		
		means = convertBufferedImageToTab(mmeans);
		assign = new int[nn];
		count = new int[kk];
	}
	
	public int[][] convertBufferedImageToTab(BufferedImage im) {
        int nbRows = im.getHeight();
        int nbCols = im.getWidth();
        int[][] tab = new int[nbRows][nbCols];
        for(int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbCols; j++) {
                Color pixelColor = new Color(im.getRGB(j, i));
                tab[i][j] = pixelColor.getGreen();
            }
        }
        return tab;
	}
	
	int estep() {
		int k, m, n, kmin = 0;
		double dmin, d;
		int nchg = 0;
		
		
		for(k=0;k<kk;k++) { count[k] = 0; }

		for(n=0;n<nn;n++) { 
			dmin = 9.99e99;

			for(k=0;k<kk;k++) { 
				for (d=0.,m=0; m<mm; m++) { 
			        int gData = data[n][m];
			        int gMeans = means[k][m];
			        
					//d += (gData-gMeans)*(gData-gMeans);
			        d += gData*gData - gMeans*gMeans;
				}
				
				System.out.println("d<dmin");
				if (d < dmin) { 
					dmin = d;
					kmin = k;
					System.out.println("d<dmin ok");
				}
			}
			if (kmin != assign[n]) { nchg++; }
			assign[n] = kmin;
			count[kmin]++;
		}
		return nchg;
	}
	
	void mstep() {
		int n,k,m;
		
		for (k=0;k<kk;k++) {
			for (m=0;m<mm;m++) {
		        means[k][m] = 0;
			}
		}
		
		for (n=0;n<nn;n++) {
			System.out.println("assign["+ n + "] = " + assign[n]);
			for (m=0;m<mm;m++) {
		        means[assign[n]][m] += data[n][m];
			}
		}
		
		for (k=0;k<kk;k++) {
			if (count[k] > 0) {
				for (m=0;m<mm;m++) { 
					means[k][m] /= count[k];
				}
			}
		}
	}

	public BufferedImage getMeans() {
		int nbRowsCenters = means.length;
        int nbColsCenters = means[0].length;
		BufferedImage bwMeans = new BufferedImage(nbColsCenters, nbRowsCenters, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < nbRowsCenters; i++) {
            for(int j = 0; j < nbColsCenters; j++) {
            	int v = (int)means[i][j];
            	int rgb = new Color(v,v,v).getRGB();
            	bwMeans.setRGB(j,i, rgb);
            }
        }
		return bwMeans;
	}
	
}
	

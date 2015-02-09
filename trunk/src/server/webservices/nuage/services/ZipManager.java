package server.webservices.nuage.services;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;


/**
 * @author Julien Beaussier
 * @version 1.0
 * User: Projet Nuages
 * Date: 1/22/15
 * Time: 10:25 PM (GMT + 1)
 */
public class ZipManager implements IZipManager {
	private final int bufferSize;
	private byte data[];
	private int count;
	
	public ZipManager(){
		bufferSize = 2048;
		data = new byte[bufferSize];
	}
	 
	/* (non-Javadoc)
	 * @see server.webservices.nuage.services.IZipManager#unzip(java.lang.String)
	 */
	@Override
	public void unzip (String archivePath) {
		try { 
			BufferedOutputStream dest = null;
			FileInputStream fileInputS = new FileInputStream(archivePath);
			BufferedInputStream bufferedInputS = new BufferedInputStream(fileInputS);
			ZipInputStream zipInputS = new ZipInputStream(bufferedInputS);
			ZipEntry entry;
			
			while ((entry = zipInputS.getNextEntry()) != null) {
				FileOutputStream fileOutputS = new FileOutputStream(archivePath.split("\\.")[0] + entry.getName());
				dest = new BufferedOutputStream(fileOutputS, bufferSize);
				
				while ((count = zipInputS.read(data, 0, bufferSize)) != -1) {
			        dest.write(data, 0, count);
			    }
				dest.flush();
			    dest.close();
			}
			
			zipInputS.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see server.webservices.nuage.services.IZipManager#zip(java.util.ArrayList)
	 */
	@Override
	public File zip (List<BufferedImage> images) {
		String archivePath = new String("archiveTemp.zip");
		try {
			FileOutputStream dest= new FileOutputStream(archivePath);
			BufferedOutputStream outBuffer = new BufferedOutputStream(dest);
			ZipOutputStream zipOutputS = new ZipOutputStream(outBuffer);
		
			for (int i = 0; i < images.size(); i++) {
				ByteArrayOutputStream byteOutputS = new ByteArrayOutputStream();
				ImageIO.write(images.get(i), "jpeg", byteOutputS);
			    InputStream fileInputS = new ByteArrayInputStream(byteOutputS.toByteArray());
			    BufferedInputStream bufferedInputS = new BufferedInputStream(fileInputS, bufferSize);
			    ZipEntry entry= new ZipEntry("Image" + i + ".jpeg");
			    zipOutputS.putNextEntry(entry);
				
			    while ((count = bufferedInputS.read(data, 0, bufferSize)) != -1) {
			    	zipOutputS.write(data, 0, count);
			    }
			    
			    zipOutputS.closeEntry();
			    bufferedInputS.close();
			}
			
			zipOutputS.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new File(archivePath);
	}
}

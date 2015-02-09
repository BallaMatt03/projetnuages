package server.webservices.nuage.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface IZipManager {

	public abstract void unzip(String archivePath);

	public abstract File zip(List<BufferedImage> images);

}
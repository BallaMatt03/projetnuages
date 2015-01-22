package client.gui.main;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class JPanelPreview extends JPanelCustom {

	private static final long serialVersionUID = 5776021318553121335L;
	private ImageIcon icon;
	
	@Override
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Image im = ImageIO.read(new File("./res/default.jpg"));
			g.drawImage(im, 0, 0, null);
			if (icon != null) {
				autoFill(this, g);
			}		
		} catch (IOException e) {
			System.err.println("*** ERROR ***\n" + e.getMessage());
		}
	}  
	
	@Override
	public final void repaint() {
		try {
			Graphics g = this.getGraphics();
			if (g != null) {
				Image im = ImageIO.read(new File("./res/default.jpg"));
				g.drawImage(im, 0, 0, null);
				if (icon != null) {
					autoFill(this, g);
				}
			}
		} catch (IOException e) {
			System.err.println("*** ERROR ***\n" + e.getMessage());
		}
	}
	
	public final void setImage(ImageIcon icon){
		try {
			this.icon = icon;
			Graphics g = this.getGraphics();
			if (g != null) {
				Image im = ImageIO.read(new File("./res/default.jpg"));
				g.drawImage(im, 0, 0, null);
				if (icon != null) {
					int width = this.getSize().width;
					Image img = icon.getImage();
					image = img.getScaledInstance(width, (width * icon.getIconHeight()) / icon.getIconWidth(), Image.SCALE_SMOOTH);
				}
			}
		} catch (IOException e) {
			System.err.println("*** ERROR ***\n" + e.getMessage());
		}
	}
}

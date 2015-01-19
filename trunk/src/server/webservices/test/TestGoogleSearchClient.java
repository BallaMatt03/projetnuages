package server.webservices.test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import server.webservices.google.GoogleSearchClient;

/**
 * Created by Julien Beaussier.
 * User: Projet Nuages
 * Date: 1/19/15
 * Time: 04:42 PM (GMT + 1)
 */
public class TestGoogleSearchClient {

	public static void main(final String[] args) {
		//Instantiate the google client
		GoogleSearchClient gsc = new GoogleSearchClient();
		
		//Instantiate the arraylist wich contain the images
		ArrayList<BufferedImage> results = new ArrayList<BufferedImage>();
		//Run the search
		results = gsc.getImagesFromKeyword("");
		//Instantiate a Swing container to show the result
		JFrame frame = new JFrame("Result of query");
		JTabbedPane jtb = new JTabbedPane();
		//Add the TabbedPane to the frame
		frame.add(jtb);
		//When X is pressed, the frame close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//For each BufferedImage in result
		for (int i = 0; i < results.size(); i++) {
			//We add the image into the TabbedPane
			jtb.add("Image " + (i + 1), new JLabel(new ImageIcon(results.get(i))));
		}
		
		frame.pack();
		frame.setVisible(true);
	}

}

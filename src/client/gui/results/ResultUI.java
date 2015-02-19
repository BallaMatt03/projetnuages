package client.gui.results;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.gui.main.JPanelCustom;

/**
 * Nom de classe : ResultUI
 * Description   : Testing the transparency of an image
 * Version       : 1.0
 * Date          : 22/01/2015
 * Copyright     : Dat
*/
public class ResultUI {
	
	private static JFrame frame;
	private JPanelCustom background;
	private JPanelTransparency foreground;
	private JSlider slider;
	private Image imagebackground;
	private JLayeredPane layeredPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
            	ResultUI resultUI = new ResultUI();
            }
        });
		
	}
	
	/** Constructor of TransparencyTest.
	 */
	public ResultUI() {
    	initialize();
    }

	/**
	 * Initialize the contents of the frame.
	 */
    public void initialize() {
    	frame = new JFrame("Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(740, 480));	    
		frame.setUndecorated(true);
	    frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		frame.setSize(new Dimension(740, 480));	    
	    
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 210, 20, 0};
		gridBagLayout.rowHeights = new int[]{30, 50, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
        frame.setLocationRelativeTo(null);
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer(0), new JLabel("0%"));
		labelTable.put( new Integer(25), new JLabel("25%"));
		labelTable.put( new Integer(50), new JLabel("50%") );
		labelTable.put( new Integer(75), new JLabel("75%"));
		labelTable.put( new Integer(100), new JLabel("100%") );
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		slider.setLabelTable(labelTable);
		slider.setValue(50);
		slider.setPaintLabels(true);
        
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 0;
		frame.getContentPane().add(slider, gbc_slider);
		background = new JPanelCustom();
		background.setLayout(null);
		GridBagConstraints gbc_background = new GridBagConstraints();
		gbc_background.fill = GridBagConstraints.BOTH;
		gbc_background.insets = new Insets(0, 0, 5, 5);
		gbc_background.gridx = 1;
		gbc_background.gridy = 1;
		frame.getContentPane().add(background, gbc_background);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 675, 414);
		background.add(layeredPane);
		foreground = new JPanelTransparency();
		foreground.setBounds(0, 0, 675, 414);
		layeredPane.add(foreground);
		
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
            	JSlider source = (JSlider)event.getSource();
            	if (!source.getValueIsAdjusting()) {
            		int val = (int) source.getValue();
                    float alpha = (float) (val * 0.01);
                    foreground.setImageAlpha(alpha);
                    System.out.println(alpha);
                }
            }
        });
        
    }
	
    
    public void setImageBackground(Image background) {
    	imagebackground = background;
    	this.background.setImageBackground(imagebackground);
    }
    
    public void setImageForeground(Image foreground) {
    	this.foreground.setImage(foreground);
    }
    
}

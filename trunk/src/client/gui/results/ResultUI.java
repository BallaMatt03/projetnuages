package client.gui.results;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
	
	private static JDialog dialog;
	private JPanelCustom background;
	private JPanelTransparency foreground;
	private JSlider slider;
	private Image imagebackground;
	private JLayeredPane layeredPane;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//            public void run() {
//            	
//            	try {
//					ResultUI resultUI = new ResultUI(ImageIO.read(new File("Image0.jpeg")), ImageIO.read(new File("Image1.jpeg")));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//        });
//		
//	}
//	
//	public ResultUI(BufferedImage front, BufferedImage back) {
//		dialog = new JDialog(new JFrame("aaa"), Dialog.ModalityType.APPLICATION_MODAL);
//		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		dialog.setMinimumSize(new Dimension(740, 480));
//		dialog.setUndecorated(true);
//		dialog.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
//		dialog.setSize(new Dimension(740, 480));
//    	dialog.setLocationRelativeTo(null);
//    	
//    	initialize();
//    	
//    	background.setImageBackground(back);
//    	foreground.setImage(front);
//    	
//        dialog.setVisible(true);
//    }
	
	/** Constructor of TransparencyTest.
	 */
	public ResultUI(JDialog dialog, BufferedImage front, BufferedImage back) {
		this.dialog = dialog;
    	initialize();
    	
    	background.setImageBackground(back);
    	foreground.setImage(front);
    	
        dialog.setVisible(true);
    }

	/**
	 * Initialize the contents of the dialog.
	 */
    public void initialize() {   
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 210, 20, 0};
		gridBagLayout.rowHeights = new int[]{30, 50, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		dialog.getContentPane().setLayout(gridBagLayout);
		dialog.setLocationRelativeTo(null);
		
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
		dialog.getContentPane().add(slider, gbc_slider);
		background = new JPanelCustom();
		background.setLayout(null);
		GridBagConstraints gbc_background = new GridBagConstraints();
		gbc_background.fill = GridBagConstraints.BOTH;
		gbc_background.insets = new Insets(0, 0, 5, 5);
		gbc_background.gridx = 1;
		gbc_background.gridy = 1;
		dialog.getContentPane().add(background, gbc_background);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 675, 414);
		background.add(layeredPane);
		foreground = new JPanelTransparency();
		foreground.setBounds(0, 0, 675, 414);
		layeredPane.add(foreground);
		
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        
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
	
    /**
     * Set the Image for the background panel
     * @param Image background
     */
    public void setImageBackground(Image background) {
    	imagebackground = background;
    	this.background.setImageBackground(imagebackground);
    }
    
    /**
     * Set the Image for the foreground panel
     * @param Image foreground
     */
    public void setImageForeground(Image foreground) {
    	this.foreground.setImage(foreground);
    }
}

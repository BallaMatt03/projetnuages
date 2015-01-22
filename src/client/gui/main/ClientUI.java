package client.gui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;



public class ClientUI {

	private static JFrame frame;
	private JTextField textField;
	private JFileChooser fileChooser;

	private static JTabbedPane tabbedPane;

	private JPanelImage imagePanel;
	private static ImageIcon myImage;

	private static JPanelPreview previewPanel;
	private static ImageIcon myPreview;

	private static JButton btnSendButton;
	private static JTextField textKeywordField;
	private JLabel labelKeyword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
				} catch (Exception e) {
					System.err.println("*** ERROR ***\n" + e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("THE Client");
		frame.setBounds(100, 100, 740, 480);
		frame.setMinimumSize(new Dimension(740, 480));
	    frame.setUndecorated(true);
	    frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 557, 200, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 150, 24, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setEnabled(false);
		
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.insets = new Insets(0, 0, 5, 5);
		gbcTextField.fill = GridBagConstraints.HORIZONTAL;
		gbcTextField.gridx = 1;
		gbcTextField.gridy = 1;
		frame.getContentPane().add(textField, gbcTextField);
		textField.setColumns(10);
		
		FileFilter filtreImage = new FileNameExtensionFilter("Images JPEG", new String[]{"jpeg", "jpg"});

		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filtreImage);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		JButton btnBrowseButton = new JButton("Choisir...");
		btnBrowseButton.setBackground(SystemColor.activeCaption);
		btnBrowseButton.setFocusPainted(false);
		btnBrowseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(frame.getContentPane()) 
						== JFileChooser.APPROVE_OPTION ){
					setImage(fileChooser.getSelectedFile());				
				}
				//setImage(new File("C:/Users/Julien De Almeida/Desktop/toUpload/success-baby.jpg"));
			}
		});
		GridBagConstraints gbcBtnBrowseButton = new GridBagConstraints();
		gbcBtnBrowseButton.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnBrowseButton.insets = new Insets(0, 0, 5, 5);
		gbcBtnBrowseButton.gridx = 2;
		gbcBtnBrowseButton.gridy = 1;
		frame.getContentPane().add(btnBrowseButton, gbcBtnBrowseButton);
		
		JButton btnCropButton = new JButton("Rogner");
		btnCropButton.setBackground(SystemColor.activeCaption);
		btnCropButton.setFocusPainted(false);
		GridBagConstraints gbcBtnCropButton = new GridBagConstraints();
		gbcBtnCropButton.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnCropButton.insets = new Insets(0, 0, 5, 5);
		gbcBtnCropButton.gridx = 2;
		gbcBtnCropButton.gridy = 2;
		frame.getContentPane().add(btnCropButton, gbcBtnCropButton);
		
		previewPanel = new JPanelPreview();
		previewPanel.setBackground(SystemColor.menu);
		GridBagConstraints gbcPreviewPanel = new GridBagConstraints();
		gbcPreviewPanel.insets = new Insets(0, 0, 5, 5);
		gbcPreviewPanel.fill = GridBagConstraints.BOTH;
		gbcPreviewPanel.gridx = 2;
		gbcPreviewPanel.gridy = 3;
		frame.getContentPane().add(previewPanel, gbcPreviewPanel);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBackground(Color.BLACK);
		GridBagConstraints gribTabbedPane = new GridBagConstraints();
		gribTabbedPane.gridheight = 7;
		gribTabbedPane.insets = new Insets(0, 0, 5, 5);
		gribTabbedPane.fill = GridBagConstraints.BOTH;
		gribTabbedPane.gridx = 1;
		gribTabbedPane.gridy = 2;
		frame.getContentPane().add(tabbedPane, gribTabbedPane);
		
		JButton btnSaveCrop = new JButton("Enregistrer Rognage");
		btnSaveCrop.setBackground(SystemColor.activeCaption);
		btnSaveCrop.setFocusPainted(false);
		GridBagConstraints gribBtnSaveCrop = new GridBagConstraints();
		gribBtnSaveCrop.fill = GridBagConstraints.HORIZONTAL;
		gribBtnSaveCrop.insets = new Insets(0, 0, 5, 5);
		gribBtnSaveCrop.gridx = 2;
		gribBtnSaveCrop.gridy = 4;
		frame.getContentPane().add(btnSaveCrop, gribBtnSaveCrop);
		
		labelKeyword = new JLabel("Mot-cl\u00E9 :");
		GridBagConstraints gbc_labelKeyword = new GridBagConstraints();
		gbc_labelKeyword.insets = new Insets(0, 0, 5, 5);
		gbc_labelKeyword.gridx = 2;
		gbc_labelKeyword.gridy = 6;
		frame.getContentPane().add(labelKeyword, gbc_labelKeyword);
		
		textKeywordField = new JTextField();
		textKeywordField.setEnabled(false);
		GridBagConstraints gbcTextKeywordField = new GridBagConstraints();
		gbcTextKeywordField.insets = new Insets(0, 0, 5, 5);
		gbcTextKeywordField.fill = GridBagConstraints.HORIZONTAL;
		gbcTextKeywordField.gridx = 2;
		gbcTextKeywordField.gridy = 7;
		frame.getContentPane().add(textKeywordField, gbcTextKeywordField);
		textKeywordField.setColumns(10);
		
		btnSendButton = new JButton("Envoyer");
		btnSendButton.setBackground(SystemColor.activeCaption);
		btnSendButton.setFocusPainted(false);
		btnSendButton.setEnabled(false);
		GridBagConstraints gribBtnSendButton = new GridBagConstraints();
		gribBtnSendButton.fill = GridBagConstraints.HORIZONTAL;
		gribBtnSendButton.insets = new Insets(0, 0, 5, 5);
		gribBtnSendButton.gridx = 2;
		gribBtnSendButton.gridy = 8;
		frame.getContentPane().add(btnSendButton, gribBtnSendButton);
	}
	
	private void setImage(File fileImage) {
		try {
			if (fileImage != null) {
				myImage = new ImageIcon(ImageIO.read(fileImage));
				imagePanel = new JPanelImage(myImage.getImage());
				tabbedPane.add(imagePanel, fileImage.getName());
				textField.setText(fileImage.getAbsolutePath());
			}
		} catch (IOException e) {
			System.err.println("*** ERROR ***\n" + e.getMessage());
		}
	}
	
	public static void setImagePreview(BufferedImage buffCropImage) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffCropImage, "jpeg", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			if (buffCropImage != null) {
				myPreview = new ImageIcon(ImageIO.read(is));
				previewPanel.setImage(myPreview);
				previewPanel.repaint();
				btnSendButton.setEnabled(true);
				textKeywordField.setEnabled(true);
			}
		} catch (Exception e) {
			System.err.println("*** ERROR ***\n" + e.getMessage());
		}
	}
}

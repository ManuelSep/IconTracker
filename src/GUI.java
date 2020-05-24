import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI implements Serializable{

	private static final long serialVersionUID = 1L;
	private JFrame mainFrame;
	private DefaultListModel<FinalImage> images = new DefaultListModel<>();
	private JLabel screenLabel;
	private JList<String> searchTypeList;
	private String searchType;
	private JList<FinalImage> finalImagesList;
	private DefaultListModel<String> imagesListOfPaths = new DefaultListModel<>();
	private ArrayList<String> imagesListOfPathsTEST = new ArrayList<>();
	private JButton searchButton = new JButton("Procurar");
	private JButton folderButton = new JButton("Pasta");
	private JTextField folderField = new JTextField();
	private JButton imageButton = new JButton("Imagem");
	private JTextField imageField = new JTextField();
	private BufferedImage logo;
	private String logoName;
	private byte[] logoByte;
	private ArrayList<ImageData> imgsDataList = new ArrayList<>();
	private ImageIcon img;
	private byte[] imgByte;
	private String imgName;
	private Client client;
	private Image image = new Image();;
	private Search search;
	private JFileChooser fileChooser;
	private File file;
	private BufferedImage bi;
	
	public GUI(Client cliente) {
		this.client = cliente;
		mainFrame = new JFrame("Find images");
		mainFrame.setBounds(40, 40, 800, 800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		initializeMainFrame();
	}
	
	private void initializeMainFrame() {
		mainFrame = new JFrame("Find images");
		mainFrame.setBounds(40, 40, 800, 800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		initializeScreenPanel();
		open();
	}

	private void open() {
		mainFrame.setVisible(true);
	}

	private void initializeScreenPanel() {
		screenLabel = new JLabel();
		ImageIcon icon = new ImageIcon("images/iscteImage.jpg");
		screenLabel.setIcon(icon);
		mainFrame.add(screenLabel, BorderLayout.CENTER);

		addMainFrameContent();
	}

	private DefaultListModel<String> searchTypes() {
		DefaultListModel<String> st = new DefaultListModel<String>();
		st.addElement("Procura simples");
		st.addElement("Procura 90º");
		st.addElement("Procura 180º");
		return st;
	}

	private void addMainFrameContent() {

		// Painel da direita
		
		finalImagesList = new JList<FinalImage>(images);
		finalImagesList.setPreferredSize(new Dimension(100, 20));
		finalImagesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane pane = new JScrollPane(screenLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainFrame.getContentPane().add(pane);

		finalImagesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				BufferedImage img = finalImagesList.getSelectedValue().getImage();
				screenLabel.setIcon(new ImageIcon(img));
				screenLabel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
			}
		});
		mainFrame.add(finalImagesList, BorderLayout.EAST);

		
		// Painel da esquerda
		searchTypeList = new JList<String>(searchTypes());
		searchTypeList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				searchType = searchTypeList.getSelectedValue();
			}
		});

		searchTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		mainFrame.add(searchTypeList, BorderLayout.WEST);

		// Painel de baixo
		JPanel southMainPanel = new JPanel();
		southMainPanel.setLayout(new BorderLayout());

		JPanel southFieldPanel = new JPanel();
		southFieldPanel.setLayout(new GridLayout(2, 0));
		JPanel southButtonPanel = new JPanel();
		southButtonPanel.setLayout(new GridLayout(2, 0));

		imageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fileChooser = new JFileChooser(".");
					if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						imageField.setText(file.getPath());
						logo = ImageIO.read(new File(file.getAbsolutePath()));
						logoName = imageField.getText();
					}
					logoByte = image.convertBufferedImageToByteArray(logo);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		southFieldPanel.add(imageField);
		southButtonPanel.add(imageButton);
		
		folderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser(".");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				       File file = fileChooser.getSelectedFile();
				       folderField.setText(file.getName());
				    
				       for (File f : file.listFiles()) {
				           String filename = f.getAbsolutePath();
				           if (filename.endsWith(".png")) {
				               imagesListOfPaths.addElement(filename);
				           }
				       }
				}else{
					folderField.setText("Choose another one");
				}
			}
		});
		southFieldPanel.add(folderField);
		southButtonPanel.add(folderButton);
		
		southMainPanel.add(southFieldPanel, BorderLayout.CENTER);
		southMainPanel.add(southButtonPanel, BorderLayout.EAST);
		
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < imagesListOfPaths.getSize(); i++) {

					try {
						bi = ImageIO.read(new File(imagesListOfPaths.get(i)));
						imgByte = image.convertBufferedImageToByteArray(bi);
						//Mudar para so receber o getPath()
						imgName = imagesListOfPaths.get(i);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ImageData imgData = new ImageData(imgByte, imgName);
					imgsDataList.add(imgData);
				}
				ImageData logoData = new ImageData(logoByte, logoName);

				search = new Search(logoData, imgsDataList, searchType);
				client.sendSearch(search);
				System.out.println("logo description " + logo);
				System.out.println("array list imgs size: " + imgsDataList.size());
				System.out.println("type of search: " + searchType);

			}
		});
		southMainPanel.add(searchButton, BorderLayout.SOUTH);
		mainFrame.add(southMainPanel, BorderLayout.SOUTH);
	}

	public Search getSearch() {
		return search;
	}
	
	public void showResults(ArrayList<Result> results) {
		System.out.println("chegou ao show results");
		System.out.println(results);
		images.clear();
		for (Result x : results)
			images.addElement(new FinalImage(x));
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Image {

	public Image() {
	}
    
    public Boolean compareImages(BufferedImage logo, BufferedImage subImage) throws IllegalArgumentException{
    	
    	if (logo.getWidth() == subImage.getWidth() && logo.getHeight() == subImage.getHeight()) {
    		for (int x = 0; x < logo.getWidth() - 1; x++) {
	    		for (int y = 0; y < logo.getHeight() - 1; y++) {
	    			Color subImageColor = new Color(subImage.getRGB(x, y));
	    			Color logoColor = new Color(logo.getRGB(x, y));
	    			if (!subImageColor.equals(logoColor)) {
	    				return false;
					}
	    		}
			}
			return true;
		}else {
			throw new IllegalArgumentException("List size dont match!");
		}
	}
    
    public void searchSubImage(String imageFileName, String logoFileName) {
    	
		try {
			BufferedImage image = ImageIO.read(new File(imageFileName));
			BufferedImage logo = ImageIO.read(new File(logoFileName));
			
			//Percorro toda a imagem original
	    	for (int x = 0; x < image.getWidth() - 1; x++) {
	    		for (int y = 0; y < image.getHeight() - 1; y++) {
	    			if ((x + logo.getWidth()) < image.getWidth() && (y + logo.getHeight()) < image.getHeight()) {
		    			BufferedImage subImage = image.getSubimage(x, y, logo.getWidth(), logo.getHeight());
		    			if (compareImages(logo, subImage)) {
		    				System.out.println("desenhou!");
							drawRectangle(image, x, y, logo.getWidth(), logo.getHeight());
						}
	    			}
	    		}
	    	}
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage convertImageIconToBufferedImage(ImageIcon imgicon) {
		BufferedImage bi = new BufferedImage(imgicon.getIconWidth(), imgicon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		// paint the Icon to the BufferedImage.
		imgicon.paintIcon(null, g, 0,0);
		g.dispose();
		return bi;
	}
	
	public byte[] convertBufferedImageToByteArray(BufferedImage bi) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, "png", baos);
		baos.flush();
		byte[] imgByte = baos.toByteArray();
		baos.close();
		return imgByte;
	}

	public BufferedImage convertByteArrayToBufferedImage(byte[] imgByte) throws IOException {
		
		InputStream in = new ByteArrayInputStream(imgByte);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, "png", new File("img/out.png"));
		return bImageFromConvert;
	}
	
	public ImageIcon convertBufferedImageToImageIcon(BufferedImage bi) {
		ImageIcon imgicon = new ImageIcon(bi);
		return imgicon;
	}
	
    public static void drawRectangle(BufferedImage image, int x, int y, int width, int height) {
    	
    	Graphics2D g2d = image.createGraphics();
    	g2d.setColor(Color.RED); 
    	g2d.drawRect(x, y, width, height);    	
    	g2d.dispose();
    }
}
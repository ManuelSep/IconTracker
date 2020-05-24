import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class FinalImage  {

	private BufferedImage paintedImage;
	private Result res;
	private String imageName;

	public FinalImage(Result res) {
		this.res = res;
		try {
			
			File file = new File(res.getName());
			imageName = file.getName();
			
			paintedImage = ImageIO.read(file);

			ArrayList<Pixel> pixels = res.getPixel();
			for (Pixel pixel : pixels) {
				Image.drawRectangle(paintedImage, pixel.getX(), pixel.getY(), pixel.getW(), pixel.getH());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public BufferedImage getImage() {
		return paintedImage;
	}

	public String toString() {
		return imageName;
	}

}

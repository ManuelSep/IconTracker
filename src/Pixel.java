import java.awt.Color;
import java.io.Serializable;

public class Pixel implements Serializable {
	private int x;
	private int y;
	private int w;
	private int h;
	
	private Color rgb;
	
	Pixel(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
	}
	
	void setColor(Color c) {
		rgb = c;
	}
	
	public Color getRGB() {
		return rgb;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}

	Boolean ComparePixels(Pixel pixel) {
		// Se o pixel do logo se encontra na mesma posição (x,y) e tem a mesma cor return true
//		return pixel.x == x && pixel.y == y && pixel.rgb.equals(rgb);
		return pixel.rgb.equals(rgb);
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " COLOR: " + rgb;
	}
}
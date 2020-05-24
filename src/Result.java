import java.io.Serializable;
import java.util.ArrayList;

public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Pixel> pixels;
	private String name;
	
	public Result(ArrayList<Pixel> pixels, String name) {
		this.pixels = pixels;
		this.name = name;
	}

	public ArrayList<Pixel> getPixel() {
		return pixels;
	}


	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name + " (" + pixels.size() + ")";
	}
}

import java.io.Serializable;

public class SimpleTask implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private byte[] logo;
	private String orientation;
	private ImageData img;

	public SimpleTask(byte[] logo, ImageData img, String orientation) {
		this.logo = logo;
		this.img = img;
		this.orientation = orientation;
	}
	
	public String getOrientation() {
		return orientation;
	}
	
	public byte[] getLogo() {
		return logo;
	}

	public ImageData getImg() {
		return img;
	}
	
	@Override
	public String toString() {
		return "byteA logo: " + logo + ", ImageData: " + img + ", orientação: " + orientation;
	}
}

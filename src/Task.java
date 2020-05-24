import java.io.Serializable;

public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private DealWithClient dealWithClient;
	private byte[] logo;
	private String orientation;
	private ImageData img;

	public Task(DealWithClient dealWithClient, byte[] logo, ImageData img, String orientation) {
		this.dealWithClient = dealWithClient;
		this.logo = logo;
		this.img = img;
		this.orientation = orientation;
	}
	
	public DealWithClient getDealWithClient() {
		return dealWithClient;
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
	
	public SimpleTask getSimpleTask() {
		return new SimpleTask(logo, img, orientation);
	}
	
	@Override
	public String toString() {
		return "byteA logo: " + logo + ", ImageData: " + img + ", orientação: " + orientation;
	}
}

import java.io.Serializable;

public class ImageData  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private byte[] img;
	private String name;
	
	
	public ImageData(byte[] img, String name) {
		this.img = img;
		this.name = name;
	}

	public byte[] getImg() {
		return img;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "byteA image: " + img + ", nome: " + name;
	}
}

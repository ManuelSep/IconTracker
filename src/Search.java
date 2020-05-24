import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Search implements Serializable{
	
	private ImageData logoData;
	private ArrayList<ImageData> imgsData;
	private String typeOfSearch;
	
	public Search(ImageData logoData, ArrayList<ImageData> imgsData, String typeOfSearch) {
		super();
		this.logoData = logoData;
		this.imgsData = imgsData;
		this.typeOfSearch = typeOfSearch;
	}

	public ImageData getLogoData() {
		return logoData;
	}

	public ArrayList<ImageData> getImgsData() {
		return imgsData;
	}

	public String getTypeOfSearch() {
		return typeOfSearch;
	}

	@Override
	public String toString() {
		return "\n ByteAlogo: " + logoData.getName() + ",\n ArrayList bytes imgs: " + imgsData + ",\n orientation: " + typeOfSearch;
	}
}

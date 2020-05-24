import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Worker implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Image img;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Worker() {
		img = new Image();
		
		runWorker();
		handleServerAnswers();
	}
	
	public void runWorker() {
		try {
			socket = new Socket("localhost", 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject("Worker");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void handleServerAnswers(){
		try {
			while(true) {
				SimpleTask taskReceived = (SimpleTask) in.readObject();
				out.writeObject(searchSubImage(taskReceived));
			}
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		
	}

	public Result searchSubImage(SimpleTask t) throws IOException {

		BufferedImage image = img.convertByteArrayToBufferedImage(t.getImg().getImg());
		BufferedImage logo = img.convertByteArrayToBufferedImage(t.getLogo());

		ArrayList<Pixel> points = new ArrayList<>();
		// Percorro toda a imagem original
		for (int x = 0; x < image.getWidth() - 1; x++) {
			for (int y = 0; y < image.getHeight() - 1; y++) {
				if ((x + logo.getWidth()) < image.getWidth() && (y + logo.getHeight()) < image.getHeight()) {
					BufferedImage subImage = image.getSubimage(x, y, logo.getWidth(), logo.getHeight());
					if (img.compareImages(logo, subImage)) {
						Pixel pixel = new Pixel(x, y, logo.getWidth(), logo.getHeight());
						points.add(pixel);
					}
				}
			}
		}

		Result result = new Result(points, t.getImg().getName());
		System.out.println("result: " + result);
		return result;
	}
	
	public static void main(String[] args) {
		new Worker();
	}
}

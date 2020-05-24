import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Search search;
	private GUI gui;
	
	public Client() throws ClassNotFoundException, IOException {
		gui = new GUI(this);
		runClient();
	}

	public void runClient() throws IOException, ClassNotFoundException {
		try {
			socket = new Socket("localhost", 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject("Client");
			while (true) {
				handleServerAnswers();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleServerAnswers() throws ClassNotFoundException, IOException {
		System.out.println("reached handleServerAswers");
		try {
			//aqui vem o out do DWC com a resultlist!!! 
		
			@SuppressWarnings("unchecked")
			ArrayList<Result> results = (ArrayList<Result>) in.readObject();
			gui.showResults(results);

		} catch (Exception exception) {
			exception.getStackTrace();
		}
	}


	public void sendSearch(Search s) {
		try {
			out.writeObject(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			new Client();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

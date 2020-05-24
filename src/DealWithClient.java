import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DealWithClient extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private Image image;
	private Server server;

	private ArrayList<Result> results = new ArrayList<>();
	
	public DealWithClient(Socket socket, ObjectOutputStream out, ObjectInputStream in, Server server) {
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			
			while (true) {
				handleClient();
			}
		} catch (Exception e) {
			e.printStackTrace();
//			server.removeClient(this);
//			closeSocket();
		}
	}

	public void sendMessage(Object ob) {
		try {
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleClient() throws IOException, ClassNotFoundException {
		System.out.println("Handling with client");
		Search searchReceived = (Search) in.readObject();
		handleSearch(searchReceived);
	}

	private synchronized void handleSearch(Search search) {
		results.clear();
		ArrayList<Task> tasks = new ArrayList<>();
		for (int i = 0; i < search.getImgsData().size(); i++) {
			Task task = new Task(this, search.getLogoData().getImg(), search.getImgsData().get(i), search.getTypeOfSearch());
			tasks.add(task);
		}

		System.out.println("tasks: "+tasks);

		for (Task task : tasks) {
			server.getBlockingQueue().addTask(task);
		}
		
		
		while(results.size() < tasks.size()) {
			try {
				wait();
				System.out.println(results.size() + "/" + tasks.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		sendMessage(results);
		
	}

	private void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized void addResult(Result result) {
		results.add(result);
		notify();
	}
}

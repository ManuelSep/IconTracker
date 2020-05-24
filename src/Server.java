import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class Server {
	
	public static final int PORTO = 8080;
	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<DealWithClient> activeClients;
	private ArrayList<DealWithWorker> activeWorkers;
	private BlockingQueue<Task> blockingQueue;
	
	public Server() throws ClassNotFoundException, IOException {
		activeClients = new ArrayList<>();
		activeWorkers = new ArrayList<>();
		blockingQueue = new BlockingQueue<>();
		startServer();
	}
	
	private void startServer() throws IOException, ClassNotFoundException {
		serverSocket = new ServerSocket(PORTO);
		
		while (true) {
			socket = serverSocket.accept();
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			Object object = in.readObject();
			String st = (String)object;
			
			if (st.equals("Client")) {
				DealWithClient client = new DealWithClient(socket, out, in, this);
				client.start();
				addClient(client);
				
			}else {
				DealWithWorker worker = new DealWithWorker(socket, out, in, this);
				worker.start();
				addWorker(worker);
			}
		}
	}
	
	public BlockingQueue<Task> getBlockingQueue() {
		return blockingQueue;
	}
	
	private synchronized void addClient(DealWithClient client) {
		activeClients.add(client);
		System.out.println("Deal with Client added");
	}
	private synchronized void addWorker(DealWithWorker worker) {
		activeWorkers.add(worker);
		System.out.println("Deal with Worker added");
	}
	public synchronized void removeClient(DealWithClient client) {
		activeClients.remove(client);
		System.out.println("Deal with Client removed");
	}
	public synchronized void removeWorker(DealWithWorker worker) {
		activeWorkers.remove(worker);
		System.out.println("Deal with Worker removed");
	}
	
	public void closeServerSocket() throws IOException {
		System.out.println("Socket will close.");
		serverSocket.close();
	}

	
	public static void main(String[] args) {
			try {
				new Server();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

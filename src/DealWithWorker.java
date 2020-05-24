import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DealWithWorker extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Server server;

	public DealWithWorker(Socket socket, ObjectOutputStream out, ObjectInputStream in,
			Server server) throws ClassNotFoundException, IOException {
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.server = server;
	}

	public void sendMessage(Object ob) {
		try {
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Task task = server.getBlockingQueue().getNextTask();
				SimpleTask simpleTask = task.getSimpleTask();
				sendMessage(simpleTask);

				System.out.println("Handling with worker");
				Result result = (Result) in.readObject();
				task.getDealWithClient().addResult(result);
				
				

			} catch (Exception e) {
//				server.removeWorker(this);
//				closeSocket();
				e.printStackTrace();
			}
		}
	}


	private void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

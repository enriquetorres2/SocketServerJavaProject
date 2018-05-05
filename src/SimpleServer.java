import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleServer {

	public static void main(String args[]) {

		int port = 6789;
		SimpleServer server = new SimpleServer(port);
		server.startServer();
	}
	ServerSocket server = null;
	Socket clientSocket = null;
	int numConnections = 0;
	int port;
	ArrayList<ServerConnection> connectionsList;

	public SimpleServer(int port) {
		this.port = port;
		this.connectionsList = new ArrayList<ServerConnection>();
	}
	public void stopServer() {
		System.out.println("Server closing");
		System.out.println(0);
	}
	public ArrayList<ServerConnection> clientList() {
		return this.connectionsList;
	}
	public void startServer() {
		try {
			server = new ServerSocket(port);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server started");
		while (true) {
			try {
				clientSocket = server.accept();
				numConnections++;
				ServerConnection oneconnection = new ServerConnection(clientSocket,this);
				connectionsList.add(oneconnection);
				new Thread(oneconnection).start();
//				BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//				String line;
//				while (true) {
//					line = is.readLine();
//					System.out.println(line);
//					if(line.equals("exit")) {
//						break;
//					}
//				}
//				System.out.println("Connection closed");
//				is.close();
//				clientSocket.close();
//				stopServer();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
class ServerConnection implements Runnable {
	BufferedReader is;
	Socket clientSocket;
	SimpleServer server;
	PrintStream os;

	public ServerConnection(Socket clientSocket, SimpleServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
		try {
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String line;
		try {
			while (true) {
				line = is.readLine();
				System.out.println(line);
				for(ServerConnection sc : server.clientList()) {
					if(sc!=this) {
						//TODO Write
						sc.os.println(line);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
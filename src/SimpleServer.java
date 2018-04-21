import java.io.*;
import java.net.*;

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

	public SimpleServer(int port) {
		this.port = port;
	}
	public void stopServer() {
		System.out.println("Server closing");
		System.out.println(0);
	}
	public void startServer() {
		try {
			server = new ServerSocket(port);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server started");
		try {
			clientSocket = server.accept();
			numConnections = 1;
			BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String line;
			while (true) {
				line = is.readLine();
				System.out.println(line);
				if(line.equals("exit")) {
					break;
				}
			}
			System.out.println("Connection closed");
			is.close();
			clientSocket.close();
			stopServer();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

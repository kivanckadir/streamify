import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.table.TableColumn;


@SuppressWarnings("unused")
public class Server {

	private MainFrame mainFrame;
	private ServerSocket serverSocket;
	private Broadcast broadcast;
	public static int mPort = 7553;
	private Socket client;

	public Server(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void startServer() throws IOException {
		serverSocket = new ServerSocket(mPort);
		
		try {
			do {
				client = serverSocket.accept();
				System.out.println("\nNew client accepted.\n");
				ClientHandler handler = new ClientHandler(client);
				handler.start();

			} while (true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServer() throws IOException {
		serverSocket.close();
	}

	class ClientHandler extends Thread {

		private Socket client;
		private Scanner input;
		private PrintWriter output;

		public ClientHandler(Socket socket) {

			client = socket;

			try {
				input = new Scanner(new BufferedInputStream(client.getInputStream()));
				output = new PrintWriter(new BufferedOutputStream(client.getOutputStream()), true);
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
			}
		}

		public void run() {
			try {
				broadcast = new Broadcast();
				broadcast.start(client.getInetAddress().getHostAddress().toString(), Server.mPort+1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}
}
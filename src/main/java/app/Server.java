package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import dataStructures.Client;
import managers.Manager;

/**
 * Handles incoming connections of clients
 * @author Ben Shabowski
 *
 */
public class Server {
	
	private ArrayList<Client> clients;
	private Manager manager;
	
	public Server(Manager m) {
		manager = m;
		clients = new ArrayList<Client>();
		new incomingConnectionHandler().start();
	}
	
	
	/**
	 * Handles incoming connections
	 * @author Ben Shabowski
	 *
	 */
	class incomingConnectionHandler extends Thread{
		//server socket
		ServerSocket serverSocket;
		
		public incomingConnectionHandler() {
			try {
				serverSocket = new ServerSocket(manager.getServerConfigManager().getPort());
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		
		
		public void run() {
			while(!serverSocket.isClosed()) {
				try {
					Client current = new Client(serverSocket.accept());
					clients.add(current);
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
		}
		
		
	}
}

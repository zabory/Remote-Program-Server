package dataStructures;

import java.net.Socket;

/**
 * Class to hold client connection to server. Clients connect with their own GUI on remote systems.
 * @author Ben Shabowski
 *
 */
public class Client {
	
	Socket socket;

	public Client(Socket socket) {
		this.socket = socket;
	}
	


}

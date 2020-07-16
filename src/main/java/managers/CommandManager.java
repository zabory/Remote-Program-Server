package managers;

import java.util.Scanner;

/**
 * A handler to easily translate server messages from the console, connected
 * client, or server GUI
 * 
 * @author Ben Shabowski
 *
 */
public class CommandManager extends Object {

	private Manager manager;

	public CommandManager(Manager m) {
		manager = m;
		new Console().start();
	}

	/**
	 * This is to handle incoming messages. Anything from the UI or console or
	 * remote clients will get sent through here.
	 * 
	 * @param message Message to be handled
	 */
	public void recieveMessage(String message) {
		switch (message) {

		case "exit":
			System.out.println("Exiting program");
			System.exit(0);
			break;

		default:
			System.out.println("Command not recognized");
		}
	}

	/**
	 * Short class to handle console input, really only meant to be used with no UI.
	 * 
	 * @author Ben Shabowski
	 *
	 */
	class Console extends Thread {

		Scanner kb;

		public Console() {
			kb = new Scanner(System.in);
		}

		public void run() {
			String input = "";
			while (!input.equals("exit")) {
				input = kb.nextLine();
				recieveMessage(input);
			}
		}

	}

}

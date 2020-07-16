package app;

import javafx.application.Application;
import javafx.stage.Stage;
import managers.Manager;

/**
 * GUI for server interface. It should react more than anything to the program, rather than change by itself
 * @author Ben Shabowski
 *
 */
public class RemoteProgramServerUI extends Application{
	
	private Manager manager;
	
	public void start(Stage arg0) {		
		arg0.show();
		arg0.setTitle("Remote Program Server");
	}
	
	/**
	 * Set the manager for the class
	 * @param h Manager for the class to be set too
	 */
	public void setHead(Manager h) {
		manager = h;
	}

}

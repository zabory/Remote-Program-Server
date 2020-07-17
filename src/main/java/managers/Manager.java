package managers;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import app.RemoteProgramServerUI;
import javafx.application.Application;
import managers.config.ServerConfigManager;

public class Manager {

	private static RemoteProgramServerUI remoteProgramServerUI;
	private static ServerConfigManager serverConfigManager;
	private static ProgramManager fileManager;
	private static Scheduler scheduler;
	private static CommandManager commandManager;
	private static ReportingManager reportingManager;

	/**
	 * Main manager to hold, and start all the other managers.
	 * A manager is meant to run independently without intervention from other managers
	 * @param args
	 */
	public Manager(String[] args) {
		//Converting array of args into a list for ez pickings
		List<String> arguments = Arrays.asList(args);
		
		// Config manager
		System.out.println("Starting config manager...");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("managers.config");
		context.refresh();
		context.close();
		
		// Reporting manager
		reportingManager = new ReportingManager(this);
		
		// File manager
		System.out.println("Starting file manager...");
		fileManager = new ProgramManager(this);

		// Program scheduler
		System.out.println("Starting scheduler...");
		scheduler = new Scheduler(this);
		
		// Command manager
		System.out.println("Starting command manager...");
		commandManager = new CommandManager(this);
		
		if (!arguments.contains("-nogui")) {
			// Server UI
			System.out.println("Starting UI...");
			remoteProgramServerUI = new RemoteProgramServerUI();
			remoteProgramServerUI.setHead(this);

			// launch the UI
			System.out.println("Opening UI...");
			Application.launch(remoteProgramServerUI.getClass());
		}
	}

	public RemoteProgramServerUI getRemoteProgramServerUI() {
		return remoteProgramServerUI;
	}

	public ServerConfigManager getServerConfigManager() {
		return serverConfigManager;
	}

	public ProgramManager getFileManager() {
		return fileManager;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
}

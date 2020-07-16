package managers;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import app.RemoteProgramServerUI;
import javafx.application.Application;
import managers.config.ServerConfigManager;

@SuppressWarnings("restriction")
public class Manager {

	static RemoteProgramServerUI remoteProgramServerUI;
	static ServerConfigManager serverConfigManager;
	static FileManager fileManager;
	static Scheduler scheduler;
	static CommandManager commandManager;

	public Manager(String[] args) {

		List<String> arguments = Arrays.asList(args);
		
		// Config manager
		System.out.println("Starting config manager...");
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("managers.config");
		context.refresh();
		serverConfigManager = context.getBean(ServerConfigManager.class);
		context.close();
		
		System.out.println(serverConfigManager.getReportingPhoneNumber());
		
		// File manager
		System.out.println("Starting file manager...");
		fileManager = new FileManager(this);

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

	public FileManager getFileManager() {
		return fileManager;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
}

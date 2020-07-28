package managers;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import managers.config.ServerConfigManager;

public class Manager {

	private static ServerConfigManager serverConfigManager;
	private static ProgramManager fileManager;
	private static ScheduleManager schedulManager;
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
		
		try {
			// Config manager
			System.out.println("Starting config manager...");
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.scan("managers.config");
			context.refresh();
			serverConfigManager = context.getBean(ServerConfigManager.class);
			context.close();
		} catch (org.springframework.beans.factory.BeanDefinitionStoreException e) {
			System.out.println("Unable to load file");
			System.exit(1);
		}
		
		// Reporting manager
		System.out.println("Starting reporting manager...");
		reportingManager = new ReportingManager(this);
		
		// File manager
		System.out.println("Starting file manager...");
		fileManager = new ProgramManager(this);

		// Program scheduler
		System.out.println("Starting scheduler...");
		schedulManager = new ScheduleManager(this);
		
		// Command manager
		System.out.println("Starting command manager...");
		commandManager = new CommandManager(this);
		
	}

	public ServerConfigManager getServerConfigManager() {
		return serverConfigManager;
	}

	public ProgramManager getFileManager() {
		return fileManager;
	}

	public ScheduleManager getSchedulManager() {
		return schedulManager;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public ReportingManager getReportingManager() {
		return reportingManager;
	}
}

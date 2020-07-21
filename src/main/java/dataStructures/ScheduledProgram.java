package dataStructures;

import java.util.Date;

public class ScheduledProgram {
	
	//Program to be run
	private Program program;
	//Time to be run at
	private Date scheduledTime;
	//if it should re-occur or not
	private boolean reoccuring;

	/**
	 * Create a scheduled program with a program
	 * @param program Process to be run
	 */
	public ScheduledProgram(Program program) {
		this.program = program;
	}
	
	/**
	 * Create a scheduled program with a time to be run, and a program
	 * @param program Process to be run
	 * @param scheduledTime Time for the process to be run
	 * @param reoccuring If the program should run every day at the same time
	 */
	public ScheduledProgram(Program program, Date scheduledTime, boolean reoccuring) {
		this(program);
		this.scheduledTime = scheduledTime;
		this.reoccuring = reoccuring;
	}
	
	/**
	 * Start the process
	 */
	public void startProcess() {
		program.start();
	}
	
	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
	public Date getScheduledTime() {
		return scheduledTime;
	}

	public boolean isReoccuring() {
		return reoccuring;
	}

	public void setReoccuring(boolean reoccuring) {
		this.reoccuring = reoccuring;
	}
	
}

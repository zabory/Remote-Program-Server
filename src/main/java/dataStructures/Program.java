package dataStructures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to hold and run program information. Can access the actual process as its running
 * @author Ben Shabowski
 *
 */
public class Program {
	
	private File sourceFile;
	
	private String startCommand;
	private String runningSwitch;
	private String stopCommand;
	
	//holds current running process
	private Process currentProcess;
	
	//holds current log of process
	private String currentLog;
	
	private Date startDate;
	private Date endDate;
	
	public State status;
	
	public Program(File sourceFile) {
		this.sourceFile = sourceFile;
		status = State.Stopped;
	}
	
	public void start() {
		//Process builder
		ProcessBuilder p = new ProcessBuilder();
		
		//if we have a custom start command or not
		if(startCommand == null) {
			//no custom start command, and its a jar file
			if(sourceFile.getName().contains(".jar")) {
				p.command("cmd.exe", "/c", "java -jar " + sourceFile.getName());
			}
		}else {
			if(sourceFile.getName().contains(".jar")) {
				p.command("cmd.exe", "/c", "java -jar " + sourceFile.getName() + " " + startCommand);
			}
		}
		
		//sets the working directory
		p.directory(sourceFile.getParentFile());
		
		//clear log
		currentLog = "";
		
		//clear end date
		endDate = null;
		
		//set start date
		startDate = new Date();
		
		new ProcessRunner(p).start();
	}
	
	/**
	 * Stop the process
	 */
	public void stop() {
		if (currentProcess.isAlive()) {
			if (stopCommand == null) {
				currentProcess.destroy();
				System.out.println("Tried to kill process");
				status = State.Stopped;
			} else {
				sendProcessMessage(stopCommand);
			}
		} else {
			System.out.println("Process is already dead");
		}
	}
	
	/**
	 * Send message to the current running process
	 * 
	 * @param message Message to be send to the process
	 */
	public void sendProcessMessage(String message) {
		if (currentProcess.isAlive()) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(currentProcess.getOutputStream()));

			try {
				out.write(message + "\n");
				out.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			System.out.println("Unable to send command to a process that isnt running");
		}
	}
	
	public void setStopCommand(String stopCommand) {
		this.stopCommand = stopCommand;
	}
	
	public void setRunningSwitch(String runningSwitch) {
		this.runningSwitch = runningSwitch;
	}
	
	private void addToLog(String line) {
		System.out.println(line);
		currentLog += line + "\n";
	}
	
	public String getCurrentLog() {
		return currentLog;
	}
	
	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}
	
	/**
	 * 
	 * @return How long the process has been running, from start to end, or start to current date
	 */
	public String getRuntime() {
		long differenceInMilliseconds;
		
		if(endDate != null) {
			differenceInMilliseconds = Math.abs(endDate.getTime() - startDate.getTime());
		}else {
			differenceInMilliseconds = Math.abs(new Date().getTime() - startDate.getTime());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		
		return sdf.format(new Date(differenceInMilliseconds));
	}
	
	/**
	 * Run the processes
	 * @author Ben Shabowski
	 *
	 */
	private class ProcessRunner extends Thread{
		
		private ProcessBuilder p;
		
		public ProcessRunner(ProcessBuilder p) {
			this.p = p;
		}
		
		public void run() {
			
			//Start the process
			try {
				currentProcess = p.start();
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
			//get a buffered reader goin
			BufferedReader BR = new BufferedReader(new InputStreamReader(currentProcess.getInputStream()));
			
			//Dont you dare set it to running it were waiting for a message
			if(runningSwitch == null) {
				status = dataStructures.State.Running;
			}else {
				status = dataStructures.State.Starting;
			}
			
			
			while(currentProcess.isAlive()) {
				try {
					String input = BR.readLine();
					//change state once we find the running switch string
					if(runningSwitch != null && input != null && input.contains(runningSwitch)) {
						status = dataStructures.State.Running;
					}
					
					//dont send a null to the log oke?
					if(input != null) {
						addToLog(input);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//get any errors
			BufferedReader errorStream = new BufferedReader(new InputStreamReader(currentProcess.getInputStream()));
			boolean reading = true;
			//Loop until there are no more errors
			while(reading) {
				try {
					String line = errorStream.readLine();
					if(line == null) {
						reading = false;
					}else {
						//if there are errors set the status to failed
						addToLog(errorStream.readLine());
						status = dataStructures.State.Failed;
					}
				} catch (IOException e) {
					reading = false;
				}
				
			}
			
			//if we havent failed, change it to stopped 
			if(status != dataStructures.State.Failed) {
				status = dataStructures.State.Stopped;
			}
			
			endDate = new Date();
			
		}
	}

}

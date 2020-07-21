package managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dataStructures.Program;
import dataStructures.ScheduledProgram;

/**
 * Class to handle scheduling
 * @author Ben Shabowski
 *
 */
public class ScheduleManager extends Thread{

	private Manager manager;
	private ArrayList<ScheduledProgram> schedule;
	private boolean scheduleRunning = true;
	private Calendar reportingTime;
	
	public ScheduleManager(Manager m) {
		manager = m;
		schedule = new ArrayList<ScheduledProgram>();
		reportingTime = Calendar.getInstance();
		reportingTime.set(Calendar.HOUR, (Integer.parseInt(manager.getServerConfigManager().getDailyReportingTime().substring(0, 2))));
		reportingTime.set(Calendar.MINUTE, (Integer.parseInt(manager.getServerConfigManager().getDailyReportingTime().substring(2, 4))));
	}
	
	public void run() {
		while(scheduleRunning) {
			Calendar currentTime = Calendar.getInstance();
			//Reporting manager kick off
			if(currentTime.get(Calendar.MINUTE) == reportingTime.get(Calendar.MINUTE) &&
					currentTime.get(Calendar.HOUR) == reportingTime.get(Calendar.HOUR)) {
				manager.getReportingManager().sendReport();
			}
			
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Stops schedule checking
	 */
	public void stopSchedule() {
		scheduleRunning = false;
	}
	
	/**
	 * Add a scheduled program to the schedule
	 * @param sp scheduled program to be added
	 */
	public void addToSchedule(ScheduledProgram sp) {
		schedule.add(sp);
	}
	
	/**
	 * Add a program to the schedule with a date
	 * @param p Program to be added
	 * @param d Date for the program to run at 
	 * @param reocurring If the program should run daily or not
	 */
	public void addToSchedule(Program p, Date d, boolean reocurring) {
		schedule.add(new ScheduledProgram(p, d, reocurring));
	}
}

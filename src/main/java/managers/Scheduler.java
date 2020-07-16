package managers;

/**
 * Class to handle scheduling, loading the schedule from file
 * @author Ben Shabowski
 *
 */
public class Scheduler {

	ReportingManager reportingManager;
	private Manager manager;
	
	public Scheduler(Manager m) {
		manager = m;
		reportingManager = new ReportingManager(m);
	}
}

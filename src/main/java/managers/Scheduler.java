package managers;

/**
 * Class to handle scheduling
 * @author Ben Shabowski
 *
 */
public class Scheduler {

	private ReportingManager reportingManager;
	private Manager manager;
	
	public Scheduler(Manager m) {
		manager = m;
		reportingManager = new ReportingManager(m);
	}
}

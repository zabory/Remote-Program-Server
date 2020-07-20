package dataStructures;

/**
 * Short Enum to differentiate the states of a report
 * @author Ben Shabowski
 *
 */
public enum State{
	/**
	 * Starting up, will be in this state until the processes' custom initialized message is hit 
	 */
	Starting,
	/**
	 * Process is currently running.
	 * Unless specified otherwise, a process is set to running as soon as it starts
	 */
	Running,
	/**
	 * Process has been finished and exited with no errors
	 */
	Stopped, 
	/**
	 * Process crashes or fails to close without error
	 */
	Failed
}
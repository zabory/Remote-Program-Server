package dataStructures;

/**
 * Saves a report and converts it to HTML to fit on a table for emailing the specific report
 *  
 * @author Ben Shabowski
 *
 */
public class Report {
	
	private String title;
	private State state;
	private String runTime;
	private String logName;
	
	/**
	 * Constructor to build a report
	 * @param title Process Name
	 * @param state Current state of the process at the time of report generation
	 * @param runTime Current time the process has been running for
	 * @param logName File name of log
	 */
	public Report(String title, State state, String runTime, String logName) {
		this.title = title;
		this.state = state;
		this.runTime = runTime;
		this.logName = logName;
	}
	
	/**
	 * Converts a report into HTML to be put into a table
	 * @return HTML COde of the report to be put into a table
	 */
	public String toHTML() {
		String htmlString = "";
		String statusColor;
		
		switch (state) {
		case Running:
			statusColor = "#008000"; //green
			break;
		case Failed:
			statusColor = "#008000"; //red
			break;
		default:
			statusColor = "#000000"; //black
			break;
		}
		
		
		htmlString = 
				  "<tr>"
                + "<td style=\"width:232px;\">" + title + "</td>"
                + "<td style=\"width:133px;\"><span style=\"color: " + statusColor + ";\">" + state + "</span></td>"
                + "<td style=\"width:184px;\">" + runTime + "</td>"
                + "<td style=\"width:310px;\"><a href=\"zgamelogic.com/logs/" + logName + "\">link to log</a></td>"
                + "</tr>";
		
		return htmlString;
	}
	

}

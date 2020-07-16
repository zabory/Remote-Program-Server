package dataStructures;

/**
 * Saves a report and converts it to HTML to fit on a table for emailing the specific report
 * 
 * States
 * ====================
 * Starting: turning on
 * Running: running
 * Stopped: program closed gracefully
 * Failed: program crashed from last run
 * 
 * @author Ben Shabowski
 *
 */
public class Report {
	
	String title;
	String status;
	String statusColor;
	String runTime;
	String logName;
	
	public Report(String title, String status, String runTime, String logName) {
		this.title = title;
		this.status = status;
		this.runTime = runTime;
		this.logName = logName;
		if(status.equals("Running")) {
			statusColor = "#008000";
		}else if(status.equals("Failed")) {
			statusColor = "#ff0000";
		}else {
			statusColor = "#000000";
		}
	}
	
	public String toHTML() {
		String htmlString = "";
		
		htmlString = 
				  "<tr>"
                + "<td style=\"width:232px;\">" + title + "</td>"
                + "<td style=\"width:133px;\"><span style=\"color: " + statusColor + ";\">" + status + "</span></td>"
                + "<td style=\"width:184px;\">" + runTime + "</td>"
                + "<td style=\"width:310px;\"><a href=\"zgamelogic.com/logs/" + logName + "\">link to log</a></td>"
                + "</tr>";
		
		return htmlString;
	}
	

}

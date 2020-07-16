package managers;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dataStructures.Report;
/**
 * Class to handle emailing results 
 * @author Ben Shabowski
 *
 */
public class ReportingManager {
	
	ArrayList<Report> reports;
	Manager manager;
	
	public ReportingManager(Manager m) {
		manager = m;
		reports = new ArrayList<Report>();
	}
	
	public ReportingManager() {
		reports = new ArrayList<Report>();
	}

	public void addReport(Report r) {
		reports.add(r);
	}
	
	public void addReport(ArrayList<Report> r) {
		reports.addAll(r);
	}
	
	public void sendReport() {
		//TODO change to config 
		String to;
		if(manager != null) {
			to = manager.getServerConfigManager().getName();
		}else {
			to = "benshabowski@gmail.com,orangeleafbush@gmail.com";
		}
		final String from = "ZgameProgramReporter@gmail.com";
		
		String host = "smtp.gmail.com";
		
		Properties properties = System.getProperties();
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
		
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(from, "lsotsntlxlkfdvqr");
        	}
        });
        
        try {
        	MimeMessage message = new MimeMessage(session);
        	
        	message.setFrom(new InternetAddress(from));
        	
        	for(String current : to.split(",")) {
        		message.addRecipient(Message.RecipientType.TO, new InternetAddress(current));
        	}
        	
        	// Set Subject: header field
            message.setSubject("Report for <plz put date here at some point>");
            
            String content = "<p><strong>This report is for &lt;date&gt; at a time</strong></p>"
                    + "<table style=\"width: 507px; height:79px,\">"
                    + "<tbody>"
                    + "<tr>"
                    + "<td style=\"width:232px;\">Program</td>"
                    + "<td style=\"width:133px;\">Status</td>"
                    + "<td style=\"width:184px;\">Runtime</td>"
                    + "<td style=\"width:310px;\">Log</td>"
                    + "</tr>";
            for(Report r : reports) {
            	content = content + r.toHTML();
            }
            
            message.setContent(content, "text/html");
            
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully!");
        } catch (MessagingException e) {
        	e.printStackTrace();
        }
	}
	
}

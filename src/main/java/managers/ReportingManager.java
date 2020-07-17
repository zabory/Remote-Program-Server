package managers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.twilio.Twilio;

import dataStructures.Report;

/**
 * Class to handle emailing statuses, as well as send crash texts
 * 
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

	/**
	 * Add report to be sent out
	 * 
	 * @param report Report to be sent out
	 */
	public void addReport(Report report) {
		reports.add(report);
	}

	/**
	 * Add reports to be sent out
	 * 
	 * @param reports Reports to be sent out
	 */
	public void addReport(ArrayList<Report> reports) {
		reports.addAll(reports);
	}

	/**
	 * Sends a report out to the email addresses in the config file.
	 */
	public void sendReport() {
		if (manager.getServerConfigManager().getReportingEmailAddresses() == null) {
			System.out.println("No reporting email addresses");
		} else if (manager.getServerConfigManager().getGoogleEmailAddress() == null) {
			System.out.println("No google email address");
		} else if (manager.getServerConfigManager().getGoogleAPIKey() == null) {
			System.out.println("No google email API key");
		} else {
			// gets the to addresses from config
			String[] to = manager.getServerConfigManager().getReportingEmailAddresses();

			final String from = manager.getServerConfigManager().getGoogleEmailAddress();

			String host = "smtp.gmail.com";

			Properties properties = System.getProperties();

			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.auth", "true");

			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, manager.getServerConfigManager().getGoogleAPIKey());
				}
			});

			try {
				MimeMessage message = new MimeMessage(session);

				message.setFrom(new InternetAddress(from));

				for (String current : to) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(current));
				}

				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

				message.setSubject("Report for " + formatter.format(date));

				String content = "<p><strong>This report is for " + formatter.format(date) + "</strong></p>"
						+ "<table style=\"width: 507px; height:79px,\">" + "<tbody>" + "<tr>"
						+ "<td style=\"width:232px;\">Program</td>" + "<td style=\"width:133px;\">Status</td>"
						+ "<td style=\"width:184px;\">Runtime</td>" + "<td style=\"width:310px;\">Log</td>" + "</tr>";
				// Add reports
				for (Report r : reports) {
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

	/**
	 * Sends text message to numbers based off the config
	 * 
	 * @param errorMessage Message to be sent to the number(s)
	 */
	public void sendErrorText(String errorMessage) {
		if (manager.getServerConfigManager().getTwilioAccountSID() == null) {
			System.out.println("No twilio account SID");
		} else if (manager.getServerConfigManager().getTwilioAuthToken() == null) {
			System.out.println("No twilio account auth token");
		} else if (manager.getServerConfigManager().getTwilioPhoneNumber() == null) {
			System.out.println("No twilio account phone number");
		} else if (manager.getServerConfigManager().getReportingPhoneNumber() == null) {
			System.out.println("No reporting phone numbers");
		} else {
			Twilio.init(manager.getServerConfigManager().getTwilioAccountSID(),
					manager.getServerConfigManager().getTwilioAuthToken());

			for (String x : manager.getServerConfigManager().getReportingPhoneNumber()) {
				com.twilio.rest.api.v2010.account.Message
						.creator(new com.twilio.type.PhoneNumber(x),
								new com.twilio.type.PhoneNumber(
										manager.getServerConfigManager().getTwilioPhoneNumber()),
								errorMessage)
						.create();
			}
		}

	}

}

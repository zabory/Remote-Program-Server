package managers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
@PropertySource("classpath:server-local.properties")
public class ServerConfigManager {

	@Value("${server.name:Program Server")
	private String name;
	
	@Value("${reporting.emails}")
	private String[] reportingEmailAddresses;
	
	@Value("${daily.reporting.time:0700}")
	private String dailyReportingTime;
	
	@Value("${reporting.numbers}")
	private String[] reportingPhoneNumber;
	
	@Value("${port:60533}")
	private int port;

	@Value("${twilio.account.sid}")
	private String twilioAccountSID;
	
	@Value("${twilio.auth.token}")
	private String twilioAuthToken;
	
	@Value("${twilio.phone.number}")
	private String twilioPhoneNumber;
	
	@Value("${google.email.address}")
	private String googleEmailAddress;
	
	@Value("${google.api.key}")
	private String googleAPIKey;

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}
	
	public String[] getReportingPhoneNumber() {
		return reportingPhoneNumber;
	}
	
	public String[] getReportingEmailAddresses() {
		return reportingEmailAddresses;
	}

	public String getTwilioAccountSID() {
		return twilioAccountSID;
	}

	public String getTwilioAuthToken() {
		return twilioAuthToken;
	}
	
	public String getTwilioPhoneNumber() {
		return twilioPhoneNumber;
	}

	public String getGoogleEmailAddress() {
		return googleEmailAddress;
	}

	public String getGoogleAPIKey() {
		return googleAPIKey;
	}

	public String getDailyReportingTime() {
		return dailyReportingTime;
	}
	
	

}

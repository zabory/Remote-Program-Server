package managers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@PropertySource("file:${N:\\server.properties}")
@Configuration
@PropertySource("classpath:server.properties")
public class ServerConfigManager {

	@Value("${server.name:Program Server")
	private String name;
	
	@Value("${reporting.emails}")
	private String[] reportingEmailAddresses;
	
	@Value("${reporting.numbers}")
	private String[] reportingPhoneNumber;
	
	@Value("${port:60533}")
	private int port;

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}
	
	public String[] getReportingPhoneNumber() {
		return reportingPhoneNumber;
	}
	
	public String[] getReportingEmails() {
		return reportingEmailAddresses;
	}

}

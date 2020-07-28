package app;

import managers.Manager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class to launch the program
 * @author Ben Shabowski
 *
 */
@ComponentScan({"managers"})
@SpringBootApplication
public class Head {

	public static void main(String[] args) {		
		SpringApplication.run(Head.class, args);
		new Manager(args);
	}

}


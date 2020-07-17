import java.io.File;

import dataStructures.Program;
import dataStructures.State;

public class ProgramTester {
	
	public static void main(String[] args) throws InterruptedException {

		Program test = new Program(new File("D:\\Desktop\\MinecraftServer\\server.jar"));
		test.setStartCommand("-nogui");
		test.setRunningSwitch("[Server thread/INFO]: Done ");
		test.setStopCommand("stop");
		test.start();
		
		
		Thread.sleep(13000);
		
		test.stop();
		
		while(test.status != State.Stopped) {
			Thread.sleep(500);
		}
		
		
		
		System.out.println(test.getRuntime());
	}
	
}

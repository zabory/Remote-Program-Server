package managers;

import java.util.ArrayList;

import dataStructures.Program;

/**
 * Class to manage programs
 * @author Ben Shabowski
 *
 */
public class ProgramManager {

	private Manager manager;
	private ArrayList<Program> programs;
	
	public ProgramManager(Manager m) {
		manager = m;
		programs = new ArrayList<Program>();
	}
}

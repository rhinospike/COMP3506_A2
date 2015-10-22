package adsa2p2;

import java.io.*;

public class ProblemTwo {

	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.err.println("A single argument must be provided.");
			System.exit(1);
		}
		
		FileReader inputFile = null;
		
		try {
			inputFile = new FileReader(args[0] + ".in");
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read file: " + e.getMessage());
			System.exit(2);
		}
		
		FlappyMap map = new FlappyMap(inputFile);
	}

}

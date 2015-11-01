package adsa2p2;

import java.io.*;
import java.util.*;

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

		solveMap(map, args[0]);
		
	}
	
	private static void solveMap(FlappyMap map, String filename) {
			
		Location[] currentMins = new Location[map.getHeight()];
		long startTime = System.nanoTime();		

		if (map.getFloor(0) < map.getInitialAltitude() && map.getInitialAltitude() < map.getCeiling(0)) { 
			// Can get to starting position with 0 taps
			currentMins[map.getInitialAltitude()] = new ReachableLocation(0);
		}

		// Keep a list of the lowest number of taps required to reach each position in each column.
		List<Location[]> steps = new ArrayList<Location[]>();
		
		steps.add(currentMins);
		
		for (int i = 0; i < map.getWidth(); i++) {
			currentMins = nextColumn(i, currentMins, map);
			steps.add(currentMins);
		}
		
		long endTime = System.nanoTime();
		
		System.out.printf("%d ms\n", (endTime - startTime)/1000000);
		
		// Uncomment the following line for a pretty picture
		//printColumns(steps);
		
		int minTaps = lowestInColumn(currentMins);
		
		if (minTaps >= 0) {
			writeOutput(filename, 1, minTaps);
		} else {
			int lastReached = getLastReachedColumn(steps);
			writeOutput(filename, 0, map.pipesPassed(lastReached));
		}
	}
	
	private static int getLastReachedColumn(List<Location[]> steps) {
		for (int i = steps.size() - 1; i >= 0; i--) {
			for (Location l : steps.get(i)) {
				if (l instanceof ReachableLocation) {
					return i;
				}
			}
		}
		return 0;
	}

	private static int lowestInColumn(Location[] column) {
		int minTaps = -1;

		for (Location l : column) {
			if (l instanceof ReachableLocation) {
								
				int lTaps = ((ReachableLocation)l).taps;
				
				if (lTaps < minTaps || minTaps == -1){
					minTaps = lTaps;
				}
			}
		}
		
		return minTaps;
	}
	
	/**
	 * 
	 * 
	 * @param currentColumn The 
	 * @param current
	 * @param map
	 * @return
	 */
	private static Location[] nextColumn(int currentColumn, Location[] current, FlappyMap map) {

		// Initialise the next column
		Location[] result = new Location[map.getHeight()];
		
		// Fill in any pipe locations
		// Bottom section of pipe
		for (int i = 0; i <= map.getFloor(currentColumn + 1); i++) {
			result[i] = new PipeLocation();
		}
		// Top section of pipe
		for (int i = map.getCeiling(currentColumn + 1); i < map.getHeight(); i++) {
			result[i] = new PipeLocation();
		}
			
		// Loop through locations in current column
		for (int height = 1; height < map.getHeight(); height++) {
			
			// If the location is reachable, see where we can go from here
			if (current[height] instanceof ReachableLocation) {
				int taps = 0;
				int currentTaps = ((ReachableLocation)current[height]).taps;
				int newHeight;
				
				// Start with no taps, increase until reaching the ceiling
				while ((newHeight = height + map.getAltitudeChange(currentColumn, taps)) < map.getCeiling(currentColumn + 1)) {
					
					// If the new height is more than 0 and requires less taps than previously found, update it
					if (newHeight > 0 && 
					   (result[newHeight] == null || 
					       ((result[newHeight] instanceof ReachableLocation) &&
					       ((ReachableLocation)result[newHeight]).taps > taps + currentTaps))) {
						
						result[newHeight] = new ReachableLocation(taps + currentTaps);
					}
					// Tap one more time
					taps++;
				}				
			}
		}

		return result;
	}
	
	/**
	 * Writes the two integers specified to a file
	 * 
	 * @param filename The filename to write to
	 * @param line1 the integer to put on the first line
	 * @param line2 the integer to put on the second line
	 */
	private static void writeOutput(String filename, int line1, int line2) {
		
		try {
			FileWriter output = new FileWriter(filename + ".out");
			String ls = System.getProperty("line.separator");

			System.out.printf("%d%s%d%s", line1, ls, line2, ls);
			output.write(String.format("%d%s%d%s", line1, ls, line2, ls));
			
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Print out a list of columns in a nicely formatted manner
	 * 
	 * @param steps a list of columns from a solution
	 */
	@SuppressWarnings("unused")
	private static void printColumns(List<Location[]> steps) {
		for (int i = steps.get(0).length - 1; i > 0; i--) {
			
			for (int j = 0; j < steps.size(); j++) {
				Location val = steps.get(j)[i];

				if (val instanceof Location) {
					System.out.printf("%s\t", val);
				} else {
					System.out.printf(" -\t");
				}
			}
			
			if (steps.get(steps.size() - 1)[i] instanceof ReachableLocation) {
				System.out.printf("(%s )\n", steps.get(steps.size() - 1)[i]);
			} else {
				System.out.printf("( - )\n");
			}
		}
	}
	
}

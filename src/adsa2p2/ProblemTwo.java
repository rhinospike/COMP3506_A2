package adsa2p2;

import java.io.*;
import java.util.*;

public class ProblemTwo {
	
	private static final int PIPE = -1;
	private static final int UNREACHED = Integer.MAX_VALUE;
	
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
		
		long startTime = System.nanoTime();
		
		int[] currentMins = new int[map.getHeight()];
		Arrays.fill(currentMins, UNREACHED);
		
		// Make sure the start is valid
		if (map.getFloor(0) < map.getInitialAltitude() && map.getInitialAltitude() < map.getCeiling(0)) { 
			// Can get to starting position with 0 taps
			currentMins[map.getInitialAltitude()] = 0;
		}
		
		int lastReached = 0;
		
		for (int i = 0; i < map.getWidth(); i++) {
			
			int[] result = new int[map.getHeight()];
			
			// Increment lastReached if the next column is reachable, break if it's not
			if (nextColumn(i, currentMins, result, map)) {
				lastReached++;
				currentMins = result;
			} else { // Still need to set currentMins so that lowestInColumn fails
				currentMins = result;
				break;
			}
		}
		
		int minTaps = lowestInColumn(currentMins);
		
		long endTime = System.nanoTime();
		
		System.out.printf("%d ms\n", (endTime - startTime)/1000000);
				
		if (minTaps >= 0) {
			writeOutput(filename, 1, minTaps);
		} else {
			// Apparently a pipe is considered passed if the bird is alive in the gap, so +1
			writeOutput(filename, 0, map.pipesPassed(lastReached + 1));
		}
	}
	
	private static int lowestInColumn(int[] column) {
		int minTaps = -1;

		for (int l : column) {
			if (l != PIPE && l != UNREACHED) {				
				if (l < minTaps || minTaps == -1){
					minTaps = l;
				}
			}
		}
		
		return minTaps;
	}
	
	private static boolean nextColumn(int currentColumn, int[] current, int[] result, FlappyMap map) {

		boolean nextReached = false;
		
		// Initialise the next column
		Arrays.fill(result, UNREACHED);
		
		// Fill in any pipe locations
		// Bottom section of pipe
		for (int i = 0; i <= map.getFloor(currentColumn + 1); i++) {
			result[i] = PIPE;
		}
		// Top section of pipe
		for (int i = map.getCeiling(currentColumn + 1); i < map.getHeight(); i++) {
			result[i] = PIPE;
		}
			
		// Loop through locations in current column
		for (int height = 1; height < map.getHeight(); height++) {
						
			// If the location is reachable, see where we can go from here
			if (current[height] != PIPE && current[height] != UNREACHED) {
				int taps = 0;
				int currentTaps = current[height];
				int newHeight;
				
				// Start with no taps, increase until reaching the ceiling
				while ((newHeight = height + map.getAltitudeChange(currentColumn, taps)) < map.getCeiling(currentColumn + 1)) {
					
					// If the new height is more than 0 and requires less taps than previously found, update it
					if (newHeight > 0 && taps + currentTaps < result[newHeight]) {
						result[newHeight] = taps + currentTaps;
						nextReached = true;
					}
					// Tap one more time
					taps++;
				}				
			}
		}
		return nextReached;
	}
	
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

}

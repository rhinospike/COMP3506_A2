package adsa2p2;

import java.io.*;
import java.util.*;

public class ProblemTwo {

	static int level = 0;
		

	
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

		minTaps(map);
		
	}
	
	public static int minTaps(FlappyMap map) {
		
		Location[] currentMins = new Location[map.getHeight()];
		long startTime = System.nanoTime();		

		// Can get to starting position with 0 taps
		currentMins[map.getInitialAltitude()] = new ReachableLocation(0);

		List<Location[]> steps = new ArrayList<Location[]>();
		
		steps.add(currentMins);

		
		for (int i = 0; i < map.getWidth(); i++) {
			currentMins = nextColumn(i, currentMins, map);
			steps.add(currentMins);
		}
		
		long endTime = System.nanoTime();
		
		System.out.printf("%d ms\n", (endTime - startTime)/1000000);
		
		//printColumns(steps);

		return 0;
		
	}
	
	public static Location[] nextColumn(int currentColumn, Location[] current, FlappyMap map) {

		Location[] result = new Location[map.getHeight()];
				
		for (int i = 0; i < map.getLowestSafe(currentColumn + 1); i++) {
			result[i] = new PipeLocation();
		}
		
		for (int i = map.getHighestSafe(currentColumn + 1) + 1; i < map.getHeight(); i++) {
			result[i] = new PipeLocation();
		}

		
		for (int height = 1; height < map.getHeight(); height++) {
			
			if (current[height] instanceof ReachableLocation) {
				int taps = 0;
				int currentTaps = ((ReachableLocation)current[height]).taps;
				int newHeight;
								
				while ((newHeight = height + map.getAltitudeChange(currentColumn, taps)) < map.getHeight()) {
										
					if (newHeight > 0 && (result[newHeight] == null 
						|| ((result[newHeight] instanceof ReachableLocation) 
							&& ((ReachableLocation)result[newHeight]).taps > taps + currentTaps))) {
						
						result[newHeight] = new ReachableLocation(taps + currentTaps);
					}
					taps++;
				}				
			}
		}

		return result;
	}
	
	public static void printColumns(List<Location[]> steps) {
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

package adsa2p1;

import java.io.*;
import java.util.*;

public class ProblemOne {

	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}
		
		String filename = args[0];
		String algType = args[1];
		
		if (!(algType.equals("greedy") || algType.equals("optimal"))) {
			System.err.println("Second argument must be \"greedy\" or \"optimal\".");
			System.exit(1);
		}
		
		FileReader inputFile = null;
		
		try {
			inputFile = new FileReader(filename + ".in");
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read file: " + e.getMessage());
			System.exit(2);
		}
		
		loadMap(inputFile);

	}
	
	private static Map<String, List<TimeSlot>> loadMap(FileReader inputFile) {
		
		BufferedReader input = new BufferedReader(inputFile);
		
		Map<String, List<TimeSlot>> result = new HashMap<String, List<TimeSlot>>();
		
		String line;
		Scanner s;
		
		try {
			line = input.readLine();
			s = new Scanner(line);
			int courseCount = s.nextInt();
		
			if (s.hasNextInt()) {
				s.close();
				throw new Exception("More than one int in first line.");
			}
			
			for (int i = 0; i < courseCount; i++) {
				
				List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
				
				line = input.readLine();
				s = new Scanner(line);
				
				String name = s.next();
				
				while (s.hasNextInt()) {
					
				}
				
				result.put(name, timeSlots);
				
			}
			
			s.close();
			
		} catch (Exception e) {
			
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

}

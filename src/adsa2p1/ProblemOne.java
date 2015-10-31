package adsa2p1;

import java.io.*;
import java.util.*;

public class ProblemOne {
	
	private interface PermuationGenerator<E> {
		public List<List<E>> generate(List<E> list);
	}
	
	private static class RotateGenerator<E> implements PermuationGenerator<E>  {

		@Override
		public List<List<E>> generate(List<E> list) {
			List<List<E>> result = new ArrayList<List<E>>();
			
			LinkedList<E> w = new LinkedList<E>(list);
			
			for (int i = 0; i < w.size(); i++) {
				result.add(new LinkedList<E>(w));
				w.add(w.removeFirst());
			}
			
			return result;
		}
	}
	
	private static class ExhaustiveGenerator<E> implements PermuationGenerator<E> {
		
		@Override
		public List<List<E>> generate(List<E> l) {
			List<List<E>> result = new ArrayList<List<E>>();

			if (l.size() == 1) {
				result.add(l);
			} else {
				for (E e : l) {
					List<E> copy = new ArrayList<E>(l);
					
					copy.remove(e);
					
					for (List<E> sub : generate(copy)) {
						sub.add(0, e);
						result.add(sub);
					}
				}
			}
			return result;
		}
	}
	
	public static void main(String[] args) {	
		
		long startTime = System.nanoTime();
		
		if (args.length != 2) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}
		
		String filename = args[0];
		String algType = args[1];
		
		List<String> allowed = Arrays.asList("greedy", "optimal", "balanced");
		
		if (!allowed.contains(algType)) {
			System.err.println("Second argument must be \"greedy\" or \"optimal\" (or \"balanced\").");
			System.exit(1);
		}
		
		FileReader inputFile = null;
		
		try {
			inputFile = new FileReader(filename + ".in");
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read file: " + e.getMessage());
			System.exit(2);
		}
		
		List<Course> courses = loadTimeTable(inputFile);
		
		int roomsRequired = 0;
		
		if (algType.equals("greedy")) {
			roomsRequired = greedyAlloc(courses);
		} else if (algType.equals("balanced")){
			roomsRequired = bestGreedyAlloc(courses, new RotateGenerator<Course>());
		} else {
			roomsRequired = bestGreedyAlloc(courses, new ExhaustiveGenerator<Course>());
		}
		
		try {
			writeTimeTable(filename, courses);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		long endTime = System.nanoTime();		
		System.out.printf(
				"%d rooms required\nTime taken: %dms (%s algorithm)\nOutput written to \"%s.out\"\n",
				roomsRequired,
				(endTime - startTime)/1000000,
				algType,
				filename);
	}

	private static int bestGreedyAlloc(List<Course> courses, PermuationGenerator<Course> pg) {
			
		List<Course> reduced = new LinkedList<Course>(); 
		
		for (Course c : courses) {
			if (c.getClashes().isEmpty()) {
				c.allocate(1);
			} else {
				reduced.add(c);
			}
		}
		
		// If there are no clashes, we only need 1 room
		if (reduced.size() == 0) {
			return 1;
		}
			
		
		List<List<Course>> permutations = pg.generate(reduced);
		
		int worst = 0;
		
		int best = courses.size();
		List<Course> bestList = null;
		
		for (List<Course> l : permutations) {
			
			int res = greedyAlloc(l);
			
			if (res > worst) {
				worst = res;
			}
			
			if (res < best) {
				best = res;
				bestList = l;
			}
			
			for (Course c : l) {
				c.allocate(0);
			}
		}
		
		if (bestList == null) {
			return 0;
		} else {
			return greedyAlloc(bestList);
		}
	}

	private static int greedyAlloc(List<Course> courses) {
		
		int roomsUsed = 0;
		
		for (Course c : courses) {
			
			int room = c.lowestAvailable();
			c.allocate(room);
			
			if (room > roomsUsed) {
				roomsUsed = room;
			}
		}
		
		return roomsUsed;
	}
	
	private static void writeTimeTable(String filename, List<Course> courses) throws IOException {
		
		if (filename == null || filename.length() == 0) {
			throw new IOException("0 length filename");
		}
		
		FileWriter output = new FileWriter(filename + ".out");
		
		String ls = System.getProperty("line.separator");
		
		output.write(String.format("%d%s", courses.size(), ls));
		
		for (Course c : courses) {
			output.write(String.format("%s%s", c.toString(), ls));
		}
		
		output.close();
			
	}

	private static List<Course> loadTimeTable(FileReader inputFile) {
		
		BufferedReader input = new BufferedReader(inputFile);
		
		List<Course> courses = new LinkedList<Course>();
		
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
					try {
						timeSlots.add(new TimeSlot(s.nextInt(), s.nextInt()));
					} catch (Exception e) {
						s.close();
						throw new Exception("Integer pair not found.");
					}
				}
				
				Course c = new Course(name, timeSlots, courses);
				if (courses.contains(c)) {
					s.close();
					throw new Exception(name + " appears twice.");
				} else {
					courses.add(c);
				}
				
			}
			
			s.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return courses;
	}

}

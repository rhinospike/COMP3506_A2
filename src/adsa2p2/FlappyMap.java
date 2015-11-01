package adsa2p2;

import java.io.*;
import java.util.*;

public class FlappyMap {
	
	private int initialAltitude, n, m, k;
	private int[] tapIncrease, drop, topDead, bottomDead;
	private List<Integer> pipeLocations;
	
	public FlappyMap(FileReader inputFile) {
			
		BufferedReader input = new BufferedReader(inputFile);		
		String line;
		Scanner s;
		
		try {
			line = input.readLine();
			s = new Scanner(line);
			
			n = s.nextInt();
			m = s.nextInt();
			k = s.nextInt();
									
			s.close();
			line = input.readLine();
			s = new Scanner(line);
			
			initialAltitude = s.nextInt();
			
			s.close();
			line = input.readLine();
			s = new Scanner(line);
						
			tapIncrease = new int[n];
			
			for (int i = 0; i < n; i++) {
				tapIncrease[i] = s.nextInt();
			}
			
			s.close();
			line = input.readLine();
			s = new Scanner(line);
						
			drop = new int[n];
			
			for (int i = 0; i < n; i++) {
				drop[i] = s.nextInt();
			}	
						
			topDead = new int[n + 1];
			Arrays.fill(topDead, m);
			bottomDead = new int[n + 1];
			Arrays.fill(bottomDead, 0);
			
			pipeLocations = new ArrayList<Integer>();
			for (int i = 0; i < k; i++) {
				s.close();
				line = input.readLine();
				s = new Scanner(line);
				
				int time = s.nextInt();
				pipeLocations.add(time);
				bottomDead[time] = Math.max(s.nextInt(), bottomDead[time]);
				topDead[time] = Math.min(s.nextInt(), topDead[time]);
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
	}
	
	public int getInitialAltitude() {
		return initialAltitude;
	}
	
	public int getWidth() {
		return n;
	}
	
	public int getHeight() {
		return m;
	}
	
	public boolean isSurvivablePosition(int column, int height) {
		if (bottomDead[column] < height && height < topDead[column]) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getFloor(int column) {
		return bottomDead[column];	
	}
	
	public int getCeiling(int column) {
		return topDead[column];
	}
	
	public int getAltitudeChange(int column, int taps) {
		
		int result;
		
		if (taps < 0) {
			throw new RuntimeException("Taps cannot be negative");
		} else if (taps == 0) {
			result = -drop[column];
		} else {
			result =  taps * tapIncrease[column];
		}
		
		return result;
	}
	
	public int pipesPassed(int lastReached) {
		int result = 0;
		
		for (int i : pipeLocations) {
			if (lastReached > i) {
				result++;
			}
		}
		
		return result;
	}
}

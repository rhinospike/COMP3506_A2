package adsa2p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FlappyMap {
	
	private int initialAltitude, n, m, k;
	private int[] tapIncrease, drop, topDead, bottomDead;
		
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
						
			topDead = new int[n];
			Arrays.fill(topDead, m);
			bottomDead = new int[n];
			Arrays.fill(bottomDead, 0);
			
			for (int i = 0; i < k; i++) {
				s.close();
				line = input.readLine();
				s = new Scanner(line);
				
				int time = s.nextInt();
				bottomDead[time] = s.nextInt();
				topDead[time] = s.nextInt();
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
	
	public int getAltitudeChange(int column, int taps) {
		
		if (taps < 0) {
			throw new RuntimeException("Taps cannot be negative");
		} else if (taps == 0) {
			return drop[column];
		} else {
			return taps * tapIncrease[column];
		}
	}
}

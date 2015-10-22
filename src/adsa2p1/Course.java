package adsa2p1;

import java.util.*;

public class Course {

	private String name;
	private List<TimeSlot> timeSlots;
	private List<Course> clashes;
	private int room = 0;
	
	public Course(String name, List<TimeSlot> timeSlots, List<Course> others) throws Exception {
		
		if (timeSlots.size() == 0) {
			throw new Exception(name + " has no TimeSlots.");
		}
		
		this.name = name;
		this.timeSlots = timeSlots;

		this.timeSlots.sort(timeSlots.get(0));
		
		for (int i = 1; i < timeSlots.size(); i++) {
			if (timeSlots.get(i - 1).overlaps(timeSlots.get(i))) {
				throw new Exception(name + " has overlapping TimeSlots.");
			}
		}
				
		clashes = new ArrayList<Course>();
		
		for (Course c : others) {
			if (overlaps(c)) {
				clashes.add(c);
				c.getClashes().add(this);
			}
		}
		
	}
	
	private Boolean overlaps(Course c) {
		
		for (TimeSlot ts : timeSlots) {
			for (TimeSlot cts : c.getTimeSlots()) {
				if (ts.overlaps(cts)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean roomAlreadyBooked(int room) {
		for (Course c : clashes) {
			if (c.room == room) {
				return true;
			}
		}
		return false;
	}
	
	public int lowestAvailable() {
		int room = 1;
		while (roomAlreadyBooked(room)) {
			room++;
		}
		return room;
	}
	
	public void allocate(int room) {
		this.room = room;
	}
	
	public List<TimeSlot> getTimeSlots() {
		return timeSlots;
	}
	
	public List<Course> getClashes() {
		return clashes;
	}
	
	public Boolean clashes(Course c) {
		return clashes.contains(c);
	}
	
	@Override
	public String toString() {
		return String.format("%s %d", name, room);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Course && ((Course)o).name.equals(name)) { 
			return true;
		} else {
			return false;
		}
	}

}

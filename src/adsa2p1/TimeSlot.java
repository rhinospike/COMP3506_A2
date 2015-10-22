package adsa2p1;

import java.util.Comparator;

public class TimeSlot implements Comparator<TimeSlot>, Comparable<TimeSlot>{
	
	private int startTime;
	private int endTime;
	
	public TimeSlot(int startTime, int endTime) throws Exception {
		
		if (startTime >= endTime) {
			throw new Exception("End time must be after start time.");
		}
		
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Gets the time when this TimeSlot starts.
	 * 
	 * @return the time when this TimeSlot starts.
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * Gets the time when this TimeSlot ends.
	 * 
	 * @return the time when this TimeSlot ends.
	 */
	public int getEndTime() {
		return endTime;
	}
	
	/**
	 * Check if this TimeSlot overlaps with another.
	 * 
	 * @param other The TimeSlot to check
	 * @return True if the TimeSlots overlap, false otherwise
	 */
	public Boolean overlaps(TimeSlot other) {
		if ((other.getStartTime() <= startTime && startTime < other.getEndTime()) || 
				(startTime <= other.getStartTime() && other.getStartTime() < endTime)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%d-%d", startTime, endTime);
	}

	@Override
	public int compareTo(TimeSlot arg0) {
		return startTime - arg0.startTime;
	}

	@Override
	public int compare(TimeSlot arg0, TimeSlot arg1) {
		return arg0.startTime - arg1.startTime;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TimeSlot)) {
			return false;
		} else {
			TimeSlot ts = (TimeSlot)o;
			if (endTime == ts.endTime && startTime == ts.startTime) {
				return true;
			} else {
				return false;
			}
		}
	}
}

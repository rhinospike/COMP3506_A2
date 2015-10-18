package adsa2p1;

public class TimeSlot {
	
	private int startTime;
	private int endTime;
	
	public TimeSlot(int startTime, int endTime) {
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
	public Boolean Overlaps(TimeSlot other) {
		if ((other.getStartTime() <= startTime && startTime < other.getEndTime()) || 
				(startTime <= other.getStartTime() && other.getStartTime() < endTime)) {
			return true;
		} else {
			return false;
		}
	}
}

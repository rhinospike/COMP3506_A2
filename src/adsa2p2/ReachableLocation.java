package adsa2p2;

/**
 * A location in a map that can be reached with a certain number of taps.
 */
class ReachableLocation extends Location {
	
	/** The number of taps required to reach this location. */
	int taps;
	
	/**
	 * Returns a formatted string of the number of taps required to reach this location.
	 */
	@Override
	public String toString() {
		return String.format(" %d", taps);
	}
	
	/**
	 * Instantiates a new reachable location.
	 *
	 * @param taps the number of taps required to reach the location
	 */
	public ReachableLocation(int taps) {
		this.taps = taps;
	}
	
}

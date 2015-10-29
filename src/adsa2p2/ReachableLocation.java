package adsa2p2;

class ReachableLocation extends Location {
	int taps;
	
	@Override
	public String toString() {
		return String.format(" %d", taps);
	}
	
	public ReachableLocation(int taps) {
		this.taps = taps;
	}
	
}

package me.zombie_striker.pixelprinter.data;

public enum Direction {
	UP_SOUTH("South"),
	UP_NORTH("North"),
	UP_EAST("East"),
	UP_WEST("West"),
	FLAT_NORTHEAST("FlatNorthEast"),
	FLAT_SOUTHEAST("FlatSouthEast"),
	FLAT_NORTHWEST("FlatNorthWest"),
	FLAT_SOUTHWEST("FlatSouthWest");

	String dir;

	Direction(String dir) {
		this.dir = dir;
	}

	public static Direction getDir(String s) {
		for (Direction dir : Direction.values()) {
			if (dir.getName().equalsIgnoreCase(s))
				return dir;
		}
		return null;
	}

	public String getName() {
		return dir;
	}
}

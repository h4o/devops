package fr.unice.polytech.ogl.islbd;

/**
 * Enumaration of all possibilities of movement
 * @author user
 *
 */
public enum Direction {
	N("N"),W("W"),E("E"),S("S");
	
	private String name;
	
	private Direction(String name) {
		this.name = name;
	}
 
	/**
	 * Return the direction associated to the one passed in parameter
	 * @param cond
	 * @return
	 */
	public static Direction getDirection(String cond) {
		for (Direction d : Direction.values()) {
			if (cond.toUpperCase().equals(d.toString().toUpperCase())) {
				return d;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return name ;
	}
}

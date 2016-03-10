package fr.unice.polytech.ogl.islbd.action;

/**
 * Land our bot in a creek of the island.
 * @author user
 *
 */
public class Land extends Action {
	
	/**
	 * Constructor - Need the creek where we land and the number of the people which will go out of the boat ! Pirate !
	 * @param creekID
	 * @param nbPeople
	 */
	public Land(String creekID, int nbPeople){
		super("land");
		addParameter("creek", creekID);
		addParameter("people", nbPeople);
	}

	/**
	 * Return how much men gone out of the boat
	 * @return
	 */
	public int getPeople() {
		return (int) this.getParameter("people");
	}

	/**
	 * Return the creek where we landed
	 * @return
	 */
	public String getCreek() {
		return (String) this.getParameter("creek");
	}
}

package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.Direction;

/**
 * 
 * Move the bot in the island (in a direction)
 * @author user
 *
 */
public class Move extends Action{

	/**
	 * Constructor - We call the action constructor with the type of the action (move_to) and we add the direction parameter
	 * @param direction
	 */
	public Move(Direction direction){
		super("move_to");
		if (direction!= null)
        	addParameter("direction", direction.toString());
        else if (direction == null)
        	addParameter("direction", "N");
	}
	
	/**
	 * Return the direction we gone
	 * @return
	 */
	public Direction getDirection(){
		return  Direction.getDirection(this.getParameter("direction").toString());
	}

}

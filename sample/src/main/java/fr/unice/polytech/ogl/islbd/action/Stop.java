package fr.unice.polytech.ogl.islbd.action;

/**
 * The last action we have to use. We come back to the creek and stop the game. It costs less then the equivalent path in move_to. Furthermore it calculates the best path (using tiles known). 
 * @author user
 *
 */
public class Stop  extends Action {
	
	
	public Stop(){
		super("stop");
	}

}

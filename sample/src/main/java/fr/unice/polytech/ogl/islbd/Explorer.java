package fr.unice.polytech.ogl.islbd;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.ogl.islbd.action.Action;
import fr.unice.polytech.ogl.islbd.action.Land;
import fr.unice.polytech.ogl.islbd.action.Stop;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import fr.unice.polytech.ogl.islbd.strategy.BehaviourSimple;
import fr.unice.polytech.ogl.islbd.strategy.IBehaviourStrategy;
import org.json.simple.parser.ParseException;

/**
 * The main class of this program. It's the one which will interact with the island.
 * @author user
 *
 */
public class Explorer implements IExplorerRaid {

	private Memory memory;
	private IBehaviourStrategy behaviour;

	public boolean hasLand = false;

	public Explorer() {
		setMemory(new Memory());
		behaviour = new BehaviourSimple(getMemory());
	}

	/**
	 * Initialize puts all the data in the memory (to know the budget allowed for this mission etc...)
	 * @param s
	 */
	@Override
	public void initialize(String s) {
		try {
			getMemory().rememberInitialData(s);

		} catch (ParseException e) {

		}
	}

	/**
	 * Take the decision of which action we want to return to the Island. To do
	 * this the method have two possibilities :
	 * 
	 * - Take the decision itself (like quitting the game)
	 * 
	 * - Let the robot take the decision. In this case the method just choose
	 * the behavior of the robot and retrieve its decision.
	 * 
	 * Finally the decision is returned to the Island.
	 */
	@Override
	public String takeDecision() {
		Action decision = null;

		int avgCostToLeave = memory.getAverageCost("move_to");

		if (memory.getUndoneObj().size() == 0) {
			Action stop = new Stop();
			memory.rememberAction(stop);
			return new Stop().toString();
		}

        double getShortestDistanceToCreek = memory.getShortestDistanceTo(new Coordinate(0, 0));
		if (memory.getCurrentBudget() > 0.015 * memory.getInitialBudget()) {

			// Nobody on the island -> decide to Land
			if (memory.getMenOnIsland() == 0)
				// TODO : implémenter un vrai choix du nombre de personnes à
				// débarquer
				decision = new Land(memory.getInitialCreek(), 1);

			// Here we choose the behavior that the bot must have
			else {
				behaviour = new BehaviourSimple(getMemory());
			}
		} else {
			// Stops the game if there is not enough action points
			decision = new Stop();
		}

		// If there is no decision taken yet, the behavior choose the action to
		// return
		if (decision == null)
			decision = behaviour.chooseNextAction();


        // Case, for example when we've reach the top right corner. Because the bot don't consider taking a detour so findOrientation doesn't return a correct value
        if (decision == null) {
            decision = new Stop();
        }
            // The action is saved in the memory

        // Exploit and stop use some kind of land, so if we barely can afford an exploit, no chance to go home
        if (decision.getName().equals("exploit") && memory.getCurrentBudget() < 4 * getShortestDistanceToCreek) {
            decision = new Stop();
        }

        memory.rememberAction(decision);
        return decision.toString();
	}

	@Override
	public void acknowledgeResults(String s) {
		try {
			getMemory().rememberConsequences(s);

		} catch (ParseException e) {
		}
		// Return du genre { "status": "OK", "cost": 12, "extras": {
		// "resources": ["WOOD", "FUR"], "altitude": -23 } }
	}

	/**
	 * @return the memory
	 */
	public Memory getMemory() {
		return memory;
	}

	/**
	 * @param memory
	 *            the memory to set
	 */
	public void setMemory(Memory memory) {
		this.memory = memory;
	}
}

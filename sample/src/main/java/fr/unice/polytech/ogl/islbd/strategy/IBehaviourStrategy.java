package fr.unice.polytech.ogl.islbd.strategy;

import fr.unice.polytech.ogl.islbd.action.Action;

public interface IBehaviourStrategy {
	public abstract Action chooseNextAction();
}

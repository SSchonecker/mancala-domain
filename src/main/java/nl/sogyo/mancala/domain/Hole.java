package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public abstract class Hole {
	ArrayList<Stone> myStones;
	protected Hole nextHole;
	Player myOwner;
	
	int numberOfStones() { 
		return myStones.size();
	}
	
	void add(Stone extraStone) {
		myStones.add(extraStone);
	}
	
	void setNextHole(Hole neighbor) {
		nextHole = neighbor;
	}
	
	abstract void receive(ArrayList<Stone> givenStones);
}
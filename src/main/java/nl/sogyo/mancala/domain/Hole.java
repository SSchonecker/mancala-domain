package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public abstract class Hole {
	protected ArrayList<Stone> myStones;
	protected Hole nextHole;
	protected Player myOwner;
	
	int numberOfStones() { 
		return myStones.size();
	}
	
	void add(Stone extraStone) {
		myStones.add(extraStone);
	}
	
	void setNextHole(Hole neighbor) {
		nextHole = neighbor;
	}
	
	void setMyOwner(Player theOwner) {
		myOwner = theOwner;
	}
	
	abstract void receive(ArrayList<Stone> givenStones);
}
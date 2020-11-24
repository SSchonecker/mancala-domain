package nl.sogyo.mancala.domain;

public abstract class Hole {
	protected int myStones;
	protected Hole nextHole;
	protected Player myOwner;
	
	void add() {
		myStones++;
	}
	
	void setNextHole(Hole neighbor) {
		nextHole = neighbor;
	}
	
	void setMyOwner(Player theOwner) {
		myOwner = theOwner;
	}
	
	abstract void receive(int givenStones);
}
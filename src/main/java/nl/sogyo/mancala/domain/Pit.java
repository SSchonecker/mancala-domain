package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Pit extends Hole {
	
	public Pit(ArrayList<Stone> startStones) {
		myStones = startStones;
	}
	
	ArrayList<Stone> clear() {
		ArrayList<Stone> temp = new ArrayList<Stone>(myStones);
		myStones.clear();
		return temp;
	}
	
	public void passStones() {
		ArrayList<Stone> temp = new ArrayList<Stone>(myStones);
		myStones.clear();
		nextHole.receive(temp);
	}
	
	public void receive(ArrayList<Stone> givenStones) {
		Stone extra = givenStones.remove(0);
		myStones.add(extra);
	}
}
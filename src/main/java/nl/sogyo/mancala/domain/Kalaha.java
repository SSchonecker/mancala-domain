package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Kalaha extends Hole {

	public Kalaha() {
		myStones = new ArrayList<Stone>();
	}

	@Override
	void receive(ArrayList<Stone> givenStones) {
		
		if (myOwner.hasTurn()) {
			Stone extra = givenStones.remove(0);
			myStones.add(extra);}
		
		if (givenStones.size() > 0) {
			nextHole.receive(givenStones);
		}
		else {
			myOwner.keepTurn();
		}
		
	}
	
	void takeAllStones(ArrayList<Stone> givenStones) {
		for (Stone elem : givenStones) {
			add(elem);
		}
	}
	

}
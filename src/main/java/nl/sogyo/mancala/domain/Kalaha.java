package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Kalaha extends Hole {

	public Kalaha() {
		/**
		 * The Kalaha is constructed with an empty stones list
		 */
		myStones = new ArrayList<Stone>();
	}

	@Override
	void receive(ArrayList<Stone> givenStones) {
		/**
		 * From a list of stones, take one if the Kalaha's owner is at play.
		 * Pass the list to neighbour if it's not empty
		 * If it is, the owner keeps their turn
		 */
		if (myOwner.isMyTurn) {
			Stone extra = givenStones.remove(0);
			myStones.add(extra);}
		
		if (givenStones.size() > 0) {
			nextHole.receive(givenStones);
		}
		else {
			myOwner.isMyTurn = true;
		}
		
	}
	
	void takeAllStones(ArrayList<Stone> givenStones) {
		/**
		 * Add all given stones to own stones (in case of stealing)
		 */
		for (Stone elem : givenStones) {
			add(elem);
		}
	}
	

}
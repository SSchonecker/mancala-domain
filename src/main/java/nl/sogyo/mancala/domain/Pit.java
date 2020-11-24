package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Pit extends Hole {
	
	private Pit opponentPit;
	Kalaha playersKalaha;

	public Pit(ArrayList<Stone> startStones) {
		/**
		 * Create a pit with (a cloned list of) stones
		 */
		myStones = new ArrayList<Stone>(startStones);
	}
	
	public void setOpponentPit(Pit opponentPit) {
		this.opponentPit = opponentPit;
	}

	public void setPlayersKalaha(Kalaha theKalaha) {
		playersKalaha = theKalaha;
	}
	
	ArrayList<Stone> giveupStones() {
		/**
		 * Return a copy of the pit's stones, removing them
		 */
		ArrayList<Stone> temp = new ArrayList<Stone>(myStones);
		myStones.clear();
		return temp;
	}
	
	public void passStones() {
		/**
		 * Move pit's stones to neighbour
		 */
		nextHole.receive(giveupStones());
	}
	
	public void receive(ArrayList<Stone> givenStones) {
		/**
		 * From a list of stones, take one and pass the list to neighbour
		 * if possible.
		 * If not, check pit's state and switch turns
		 */
		Stone extra = givenStones.remove(0);
		myStones.add(extra);
		if (givenStones.size() > 0) {
			nextHole.receive(givenStones);
		}
		else {
			checkState();
			myOwner.switchTurn();
		}
	}

	private void checkState() {
		/**
		 * Check if the pit was empty before the move and
		 * the owner has the turn, to steal the opponents stones
		 */
		if (myStones.size() == 1 && myOwner.isMyTurn) {
			stealStones();
		}		
	}

	private void stealStones() {
		/**
		 * Take all the stones from the opposing pit into own stone list
		 * and move all stones to the Kalaha if opponent had stones
		 */
		myStones.addAll(opponentPit.giveupStones());
		if (myStones.size() > 1) {
			playersKalaha.takeAllStones(giveupStones());
		}
	}
}
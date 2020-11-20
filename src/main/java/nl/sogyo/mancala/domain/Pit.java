package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Pit extends Hole {
	
	private Pit opponentPit;
	private Kalaha playersKalaha;

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
		if (givenStones.size() == 0) {
			if (myStones.size() == 1 && myOwner.hasTurn()) {
				stealStones();
			}
		}
		else {
			nextHole.receive(givenStones);
		}
	}

	private void stealStones() {
		ArrayList<Stone> stolenStones = opponentPit.clear();
		myStones.addAll(stolenStones);
		
		ArrayList<Stone> allStones = new ArrayList<Stone>(myStones);
		myStones.clear();
		playersKalaha.takeAllStones(allStones);
	}

	public void setOpponent(Pit opponentPit) {
		this.opponentPit = opponentPit;
	}

	public void setMyKalaha(Kalaha theKalaha) {
		playersKalaha = theKalaha;
	}
}
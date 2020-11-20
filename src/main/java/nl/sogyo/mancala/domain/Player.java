package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Pit> pitList = new ArrayList<Pit>();
	private Kalaha myKalaha;
	boolean myTurn = false;

	public void givePits(ArrayList<Pit> pitsInput) {
		pitList = pitsInput;
		for (Pit eachPit : pitList) {
			eachPit.setMyOwner(this);
		}
	}
	
	public void giveKalaha(Kalaha myKalaha) {
		this.myKalaha = myKalaha;
		this.myKalaha.setMyOwner(this);
		for (Pit eachPit : pitList) {
			eachPit.setMyKalaha(this.myKalaha);
		}
	}

	public int stonesOwned() {
		int totalStones = 0;
		for (Pit myPit : pitList) {
			totalStones += myPit.numberOfStones();
		}
		totalStones += myKalaha.numberOfStones();
		return totalStones;
	}

	public boolean hasTurn() {
		return myTurn;
	}
	
}
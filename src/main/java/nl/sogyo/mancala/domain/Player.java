package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Pit> pitList = new ArrayList<Pit>();
	Kalaha myKalaha;
	boolean myTurn = false;
	Player nextPlayer;

	public void givePits(ArrayList<Pit> pitsInput) {
		pitList = pitsInput;
		for (int i = 0; i < pitList.size() - 1; i++) {
			pitList.get(i).setMyOwner(this);
			pitList.get(i).setNextHole(pitList.get(i + 1));
		}
		pitList.get(pitList.size() - 1).setMyOwner(this);
	}
	
	public void giveKalaha(Kalaha myKalaha) {
		this.myKalaha = myKalaha;
		this.myKalaha.setMyOwner(this);
		for (Pit eachPit : pitList) {
			eachPit.setMyKalaha(this.myKalaha);
		}
		pitList.get(pitList.size() - 1).setNextHole(this.myKalaha);
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
	
	public void switchTurn() {
		myTurn = !myTurn;
		nextPlayer.myTurn = !nextPlayer.myTurn;
	}

	public void selectPit(int i) {
		pitList.get(i - 1).passStones();
	}

	public void setNextPlayer(Player opponent) {
		nextPlayer = opponent;
		myKalaha.setNextHole(nextPlayer.pitList.get(0));
		//setPitsOpponents();
	}

	public void keepTurn() {
		myTurn = true;
	}
	
}
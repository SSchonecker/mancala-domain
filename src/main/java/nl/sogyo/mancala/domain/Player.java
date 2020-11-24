package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Pit> pitList = new ArrayList<Pit>();
	Kalaha myKalaha;
	boolean isMyTurn = false;
	Player nextPlayer;

	public void givePits(ArrayList<Pit> pitsInput) {
		/**
		 * Give the list of pits, link them (setNextHole)
		 * and make them own (setMyOwner)
		 */
		pitList = pitsInput;
		for (int i = 0; i < pitList.size() - 1; i++) {
			pitList.get(i).setMyOwner(this);
			pitList.get(i).setNextHole(pitList.get(i + 1));
		}
		// The last pit will later on receive the Kalaha as nextHole
		pitList.get(pitList.size() - 1).setMyOwner(this);
	}
	
	public void giveKalaha(Kalaha myKalaha) {
		/**
		 * Give the kalaha, make it own and give it to pits
		 */
		this.myKalaha = myKalaha;
		this.myKalaha.setMyOwner(this);
		for (Pit eachPit : pitList) {
			eachPit.setPlayersKalaha(this.myKalaha);
		}
		pitList.get(pitList.size() - 1).setNextHole(this.myKalaha);
	}
	
	public void setNextPlayer(Player opponent) {
		/**
		 * Set the opponent and link own kalaha to their first pit
		 */
		nextPlayer = opponent;
		myKalaha.setNextHole(nextPlayer.pitList.get(0));
	}

	public int stonesOwned() {
		/**
		 * Return all stones on player's side
		 */
		int totalStones = stonesInPits();
		totalStones += myKalaha.numberOfStones();
		return totalStones;
	}
	
	public int stonesInPits() {
		/**
		 * Return number of stones in player's pits
		 */
		int totalStones = 0;
		for (Pit myPit : pitList) {
			totalStones += myPit.numberOfStones();
		}
		return totalStones;
	}
	
	public void switchTurn() {
		isMyTurn = !isMyTurn;
		nextPlayer.isMyTurn = !nextPlayer.isMyTurn;
	}

	public void selectPit(int pitNr) {
		/**
		 * Player selects a pit to start turn
		 * (can eventually take other type of/user input)
		 */
		pitList.get(pitNr - 1).passStones();
	}
	
}
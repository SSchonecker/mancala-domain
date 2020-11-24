package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Board {
	
	final int numberOfPlayers = 2;
	final int pitsPerPlayer = 6;
	final int stonesPerPit = 4;
	ArrayList<Player> playerList = new ArrayList<Player>(numberOfPlayers);
	
	public Board() {
		initiate();
	}
	
	public void initiate() {
		/**
		 * Create the players and holes with stones in start state
		 */
		for (int p = 0; p < numberOfPlayers; p++) {
			Player aPlayer = new Player();
			ArrayList<Pit> playersPits = new ArrayList<Pit>(pitsPerPlayer);
			
			for (int i = 0; i < pitsPerPlayer; i++) {
				playersPits.add(new Pit(stonesPerPit));
			}
			
			aPlayer.givePits(playersPits);
			aPlayer.giveKalaha(new Kalaha());
			playerList.add(aPlayer);
		}
		createOpponents();
		playerList.get(0).isMyTurn = true;
	}
	
	private void createOpponents() {
		/**
		 * Set the players opponent and links the opposing pits
		 */
		for (int p = 0; p < numberOfPlayers; p++) {
			Player thisPlayer = playerList.get(p);
			Player opponent = playerList.get((p+1)%numberOfPlayers);
			thisPlayer.setNextPlayer(opponent);
			for (int i = 0; i < pitsPerPlayer; i++) {
				Pit opposingPit = opponent.pitList.get(pitsPerPlayer - 1 - i);
				Pit thisPit = thisPlayer.pitList.get(i);
				thisPit.setOpponentPit(opposingPit);
			}
		}
	}

	public void setLayout(int[] nrStonesInHoles) {
		/**
		 * Set up the pits and kahala's with the amount of stones
		 * given by each element of the input list
		 */
		int holeIndex = 0;
		for (Player aplayer : playerList) {
			for (Pit eachPit : aplayer.pitList) {
				eachPit.myStones = nrStonesInHoles[holeIndex];
				holeIndex++;
			}
			aplayer.myKalaha.myStones = nrStonesInHoles[holeIndex];
			holeIndex++;
		}
	}

	public Player giveWinner() {
		/**
		 * Return the player with the most stones
		 */
		Player winner = playerList.get(0);
		for (Player aplayer : playerList) {
			if (aplayer.stonesOwned() > winner.stonesOwned()) {
				winner = aplayer;
			}
		}
		return winner;
	}

	public boolean playGame() {
		/**
		 * Check for sides with empty pits
		 */
		for (Player aplayer : playerList) {
			if (aplayer.stonesInPits() == 0) {
				return false;
			}
		}
		return true;
	}
	
}
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
		for (int p = 0; p < numberOfPlayers; p++) {
			Player aPlayer = new Player();
			ArrayList<Pit> playersPits = new ArrayList<Pit>(pitsPerPlayer);
			
			for (int i = 0; i < pitsPerPlayer; i++) {
				ArrayList<Stone> newStones = new ArrayList<Stone>(stonesPerPit);
				for (int j = 0; j < stonesPerPit; j++) {
					newStones.add(new Stone());
				}
				playersPits.add(new Pit(newStones));
			}
			
			aPlayer.givePits(playersPits);
			aPlayer.giveKalaha(new Kalaha());
			playerList.add(aPlayer);
		}
		createOpponents();
	}
	
	private void createOpponents() {
		for (int p = 0; p < numberOfPlayers; p++) {
			Player thisPlayer = playerList.get(p);
			Player opponent = playerList.get((p+1)%numberOfPlayers);
			thisPlayer.setNextPlayer(opponent);
			for (int i = 0; i < pitsPerPlayer; i++) {
				Pit opposingPit = opponent.pitList.get(pitsPerPlayer - 1 - i);
				Pit thisPit = thisPlayer.pitList.get(i);
				thisPit.setOpponent(opposingPit);
			}
		}
	}

	public void setLayout(int[] nrStonesInHoles) {
		ArrayList<Stone> allStones = clearBoard();
		int holeIndex = 0;
		for (Player aplayer : playerList) {
			for (Pit eachPit : aplayer.pitList) {
				for (int i = 0; i < nrStonesInHoles[holeIndex]; i++) {
					eachPit.myStones.add(allStones.remove(0));
				}
				holeIndex++;
			}
			for (int i = 0; i < nrStonesInHoles[holeIndex]; i++) {
				aplayer.myKalaha.myStones.add(allStones.remove(0));
			}
			holeIndex++;
		}
		
	}

	private ArrayList<Stone> clearBoard() {
		ArrayList<Stone> allStones = new ArrayList<Stone>(48);
		for (Player aplayer : playerList) {
			for (Pit eachPit : aplayer.pitList) {
				allStones.addAll(eachPit.clear());
			}
		}
		return allStones;
	}
	
}
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
			Player myPlayer = new Player();
			for (int i = 0; i < pitsPerPlayer; i++) {
				ArrayList<Stone> newStones = new ArrayList<Stone>(stonesPerPit);
				for (int j = 0; j < stonesPerPit; j++) {
					newStones.add(new Stone());
				}
				myPlayer.pitList.add(new Pit(newStones));
			}
			playerList.add(myPlayer);
		}
	}
	
}
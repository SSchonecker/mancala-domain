package nl.sogyo.mancala.domain;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Pit> pitList = new ArrayList<Pit>();
	Kalaha myKalaha;
	boolean isMyTurn = false;
	Player nextPlayer;
	public int score = 0;
	
	public void switchTurn() {
		isMyTurn = !isMyTurn;
		nextPlayer.isMyTurn = !nextPlayer.isMyTurn;
	}
	
}
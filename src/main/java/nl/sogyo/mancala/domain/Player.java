package nl.sogyo.mancala.domain;

public class Player {
	
	boolean isMyTurn = false;
	Player nextPlayer;
	int score = 0;
	
	void switchTurn() {
		isMyTurn = !isMyTurn;
		nextPlayer.isMyTurn = !nextPlayer.isMyTurn;
	}
	
}
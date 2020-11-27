package nl.sogyo.mancala.domain;

public class Player {
	
	private boolean isMyTurn = false;
	private int score = 0;
	private Player nextPlayer;

	void switchTurn() {
		isMyTurn = !isMyTurn;
		nextPlayer.flipTurn();
	}
	
	void flipTurn() {
		isMyTurn = !isMyTurn;
	}
	
	public boolean hasTurn() {
		return isMyTurn;
	}
	
	void getTurn() {
		isMyTurn = true;
	}
	
	void addToScore(int pitsStones) {
		score += pitsStones;
	}
	
	public int getScore() {
		return score;
	}
	
	void setOpponent(Player theOpponent) {
		nextPlayer = theOpponent;
	}
	
	public Player getOpponent() {
		return nextPlayer;
	}
	
}
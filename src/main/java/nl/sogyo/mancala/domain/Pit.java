package nl.sogyo.mancala.domain;

public class Pit extends Hole {
	
	private Pit opponentPit;
	Kalaha playersKalaha;

	public Pit(int startStones) {
		/**
		 * Create a pit with stones
		 */
		myStones = startStones;
	}
	
	public void setOpponentPit(Pit opponentPit) {
		this.opponentPit = opponentPit;
	}

	public void setPlayersKalaha(Kalaha theKalaha) {
		playersKalaha = theKalaha;
	}
	
	int giveupStones() {
		/**
		 * Return a copy of the pit's stones, removing them
		 */
		int temp = myStones;
		myStones = 0;
		return temp;
	}
	
	public void passStones() {
		/**
		 * Move pit's stones to neighbour
		 */
		nextHole.receive(giveupStones());
	}
	
	public void receive(int givenStones) {
		/**
		 * From a list of stones, take one and pass the list to neighbour
		 * if possible.
		 * If not, check pit's state and switch turns
		 */
		givenStones--;
		myStones++;
		if (givenStones > 0) {
			nextHole.receive(givenStones);
		}
		else {
			checkState();
			myOwner.switchTurn();
		}
	}

	private void checkState() {
		/**
		 * Check if the pit was empty before the move and
		 * the owner has the turn, to steal the opponents stones
		 */
		if (myStones == 1 && myOwner.isMyTurn) {
			stealStones();
		}		
	}

	private void stealStones() {
		/**
		 * Take all the stones from the opposing pit into own stone list
		 * and move all stones to the Kalaha if opponent had stones
		 */
		myStones += opponentPit.giveupStones();
		if (myStones > 1) {
			playersKalaha.takeAllStones(giveupStones());
		}
	}
}
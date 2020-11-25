package nl.sogyo.mancala.domain;

public class Pit extends Hole {
	
	private int totalNrOfPits;

	public Pit(int startStones, int nrOfPits) {
		myOwner = new Player();
		myOwner.isMyTurn = true;
		myStones = 4;
		totalNrOfPits = nrOfPits;
		nextHole = new Pit(startStones, nrOfPits - 2, 
				totalNrOfPits, myOwner, this);
	}
	
	public Pit(int startStones, int nrOfPits, int totalNrOfPits, 
			Player theOwner, Pit initPit) {
		/**
		 * Create a pit with stones
		 */
		myStones = startStones;
		myOwner = theOwner;
		this.totalNrOfPits = totalNrOfPits;
		if (nrOfPits > 0) {
			nextHole = new Pit(startStones, nrOfPits - 1, 
					totalNrOfPits, myOwner, initPit);
		}
		else {
			nextHole = new Kalaha(startStones, totalNrOfPits, 
					myOwner, initPit);
		}
	}
	
	public void passStones() {
		/**
		 * Move pit's stones to neighbour
		 */
		int temp = myStones;
		myStones = 0;
		nextHole.receive(temp);
	}
	
	public void receive(int givenStones) {
		/**
		 * From a list of stones, take one and pass the list to neighbour
		 * if possible.
		 * If not, check pit's state and switch turns
		 */
		myStones++;
		givenStones--;
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
		 * Send all stones to the Kalaha (if opponent had stones)
		 * and tell it to empty the opponent pit
		 */
		myStones = nextHole.giveToKalaha(myStones, 0);
	}
	
	@Override
	protected int giveToKalaha(int nrOfStones, int distance) {
		return nextHole.giveToKalaha(nrOfStones, distance + 1);
	}
	
	@Override
	protected int getStones(int distance) {
		if (distance > 0) {
			return nextHole.getStones(distance - 1);
		}
		int temp = myStones;
		myStones = 0;
		return temp;
	}
	
	@Override
	protected boolean emptySide() {
		if (myStones == 0) {
			return nextHole.emptySide();
		}
		return false;
	}
	
	@Override
	protected void setScore() {
		int temp = myStones;
		myStones = 0;
		myOwner.score += temp;
		nextHole.setScore();
	}
}
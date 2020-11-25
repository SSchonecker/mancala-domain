package nl.sogyo.mancala.domain;

public class Kalaha extends Hole {

	public Kalaha(int startStones, int totalNrOfPits, Player theOwner, 
			Pit initPit) {
		/**
		 * The Kalaha is constructed without stones.
		 * The first kalaha makes the second side with a new player,
		 * the second kalaha links to the first pit
		 */
		myStones = 0;
		myOwner = theOwner;
		
		if (myOwner.nextPlayer == null) {
			Player theNewOwner = new Player();
			myOwner.nextPlayer = theNewOwner;
			theNewOwner.nextPlayer = myOwner;
			
			nextHole = new Pit(startStones, totalNrOfPits, 
					totalNrOfPits, theNewOwner, initPit);
		}
		else {
			nextHole = initPit;
		}
	}

	@Override
	void receive(int givenStones) {
		/**
		 * Take one stone if the Kalaha's owner is at play,
		 * pass on the (remaining) stones
		 * or let the player keep their turn
		 */
		if (myOwner.isMyTurn) {
			givenStones--;
			myStones++;}
		
		if (givenStones > 0) {
			nextHole.receive(givenStones);
		}
		else {
			myOwner.isMyTurn = true;
		}
		
	}
	
	@Override
	protected int initiateStealing(int nrOfStones, int distance) {
		int stonesStolen = nextHole.stealStones(distance);
		if (stonesStolen == 0) {
			return nrOfStones;
		}
		
		myStones += (nrOfStones + stonesStolen);
		return 0;		
	}
	
	@Override
	protected boolean emptySide() {
		return true;
	}
	
	@Override
	protected void setScore() {
		myOwner.score += myStones;
		if (nextHole.myOwner.score == 0) {
			nextHole.setScore();
		}
	}
	
	// The following methods should not be called on kalaha's
	@Override
	public void passStones() throws Exception {
		throw new IndexOutOfBoundsException("Not a valid move.");
	}
	
	@Override
	protected int stealStones(int distance) {
		return 0;
	}

}
package nl.sogyo.mancala.domain;

public class Kalaha extends Hole {

	Kalaha(int totalNrOfPits, Player theOwner, Pit initPit) {
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
			
			nextHole = new Pit(totalNrOfPits, theNewOwner, initPit);
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
	int initiateStealing(int nrOfStones, int distance) {
		/**
		 * Get the stones of the opponents hole, check if there are any,
		 * send back the stone to the initiating pit
		 * or add all stones to self
		 */
		int stonesStolen = nextHole.stealStones(distance);
		if (stonesStolen == 0) {
			return nrOfStones;
		}
		
		myStones += (nrOfStones + stonesStolen);
		return 0;		
	}
	
	@Override
	boolean emptySide() {
		/**
		 * If a pit.emptySide()-sequence gets here, the side is empty
		 */
		return true;
	}
	
	@Override
	void setScore() {
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
	int stealStones(int distance) {
		return 0;
	}

}
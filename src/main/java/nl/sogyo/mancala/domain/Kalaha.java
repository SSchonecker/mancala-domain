package nl.sogyo.mancala.domain;

public class Kalaha extends Hole {

	public Kalaha(int startStones, int totalNrOfPits, Player theOwner, 
			Pit initPit) {
		/**
		 * The Kalaha is constructed without stones
		 */
		myStones = 0;
		myOwner = theOwner;
		if (myOwner.nextPlayer == null) {
			Player theNewOwner = new Player();
			myOwner.nextPlayer = theNewOwner;
			theNewOwner.nextPlayer = myOwner;
			nextHole = new Pit(startStones, totalNrOfPits - 1, 
					totalNrOfPits, theNewOwner, initPit);
		}
		else {
			nextHole = initPit;
		}
		
	}

	@Override
	void receive(int givenStones) {
		/**
		 * From a list of stones, take one if the Kalaha's owner is at play.
		 * Pass the list to neighbour if it's not empty
		 * If it is, the owner keeps their turn
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
	public void passStones() {
		
	}
	
	@Override
	protected int getStones(int distance) {
		return -1;
	}
	
	@Override
	protected int giveToKalaha(int nrOfStones, int distance) {
		int stonesStolen = nextHole.getStones(distance);
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

}
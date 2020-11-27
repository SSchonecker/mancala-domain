package nl.sogyo.mancala.domain;

public class Kalaha extends Hole {

	Kalaha(int totalNrOfPits, Player theOwner, Pit initPit) {
		myStones = 0;
		myOwner = theOwner;
		
		if (myOwner.getOpponent() == null) {
			Player theNewOwner = new Player();
			myOwner.setOpponent(theNewOwner);
			theNewOwner.setOpponent(myOwner);
			
			nextHole = new Pit(totalNrOfPits, theNewOwner, initPit);
		}
		else {
			nextHole = initPit;
		}
	}

	@Override
	void receive(int givenStones) {
		if (myOwner.hasTurn()) {
			givenStones--;
			myStones++;}
		
		if (givenStones > 0) {
			nextHole.receive(givenStones);
		}
		else {
			myOwner.getTurn();
		}
	}
	
	@Override
	int initiateStealing(int nrOfStones, int distance) {
		int stonesStolen = nextHole.stealStones(distance);
		if (stonesStolen == 0) {
			return nrOfStones;
		}
		
		myStones += (nrOfStones + stonesStolen);
		return 0;		
	}
	
	@Override
	boolean emptySide() {
		return true;
	}
	
	@Override
	void setScore() {
		myOwner.addToScore(myStones);
		if (nextHole.getOwner().getScore() == 0) {
			nextHole.setScore();
		}
	}
	
	// The following methods should not be called on kalaha's
	@Override
	public boolean passStones() {
		return false;
	}
	
	@Override
	int stealStones(int distance) {
		return 0;
	}

}
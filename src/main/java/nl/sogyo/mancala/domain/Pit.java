package nl.sogyo.mancala.domain;

public class Pit extends Hole {
	
	private final int START_STONES = 4;
	private final int TOTAL_PITS = 6;

	public Pit() {
		myOwner = new Player();
		myOwner.getTurn();
		myStones = START_STONES;
		nextHole = new Pit(TOTAL_PITS - 1, myOwner, this);
	}
	
	Pit(int pitsToGo, Player theOwner, Pit initPit) {
		myStones = START_STONES;
		myOwner = theOwner;
		if (pitsToGo > 1) {
			nextHole = new Pit(pitsToGo - 1, myOwner, initPit);
		}
		else {
			nextHole = new Kalaha(TOTAL_PITS, myOwner, initPit);
		}
	}
	
	public boolean passStones() {
		if (myStones == 0 || !myOwner.hasTurn()) {
			return false;
		}
		
		int temp = myStones;
		myStones = 0;
		nextHole.receive(temp);
		return true;
	}
	
	void receive(int givenStones) {
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
		if (myStones == 1 && myOwner.hasTurn()) {
			// If the opposing pit is empty, the stone will return
			myStones = nextHole.initiateStealing(myStones, 0);
		}		
	}
	
	@Override
	int initiateStealing(int nrOfStones, int distance) {
		return nextHole.initiateStealing(nrOfStones, distance + 1);
	}
	
	@Override
	int stealStones(int distance) {
		if (distance > 0) {
			return nextHole.stealStones(distance - 1);
		}
		
		int temp = myStones;
		myStones = 0;
		return temp;
	}
	
	@Override
	public boolean emptySide() {
		if (myStones == 0) {
			return nextHole.emptySide();
		}
		return false;
	}
	
	@Override
	public void setScore() {
		myOwner.addToScore(myStones);
		nextHole.setScore();
	}
}
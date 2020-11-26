package nl.sogyo.mancala.domain;

public class Pit extends Hole {

	public Pit(int startStones, int totalPits) {
		/**
		 * Create the first pit, with a new Player
		 * and start the pit-chain at its neighbour
		 */
		myOwner = new Player();
		myOwner.isMyTurn = true;
		myStones = 4;
		nextHole = new Pit(startStones, totalPits - 1, 
				totalPits, myOwner, this);
	}
	
	public Pit(int startStones, int pitsToGo, int totalNrOfPits, 
			Player theOwner, Pit initPit) {
		/**
		 * Create an additional pit with stones
		 * as long as additional pits need to be created
		 */
		myStones = startStones;
		myOwner = theOwner;
		if (pitsToGo > 1) {
			nextHole = new Pit(startStones, pitsToGo - 1, 
					totalNrOfPits, myOwner, initPit);
		}
		else {
			nextHole = new Kalaha(startStones, totalNrOfPits, 
					myOwner, initPit);
		}
	}
	
	public void passStones() throws Exception {
		/**
		 * Give all pit's stones to neighbour
		 */
		if (myStones == 0 || !myOwner.isMyTurn) {
			throw new IndexOutOfBoundsException("Not a valid move");
		}
		int temp = myStones;
		myStones = 0;
		nextHole.receive(temp);
	}
	
	public void receive(int givenStones) {
		/**
		 * Take one stone and pass the rest to the neighbour
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
		 * If the pit was empty before the move and
		 * the owner has the turn, 
		 * tell the kalaha to steal the opponent's stones
		 */
		if (myStones == 1 && myOwner.isMyTurn) {
			// If the opposing pit is empty, the stone will return
			myStones = nextHole.initiateStealing(myStones, 0);
			}		
	}
	
	@Override
	protected int initiateStealing(int nrOfStones, int distance) {
		/**
		 * Pass on an empty pit's stone and distance to kalaha
		 */
		return nextHole.initiateStealing(nrOfStones, distance + 1);
	}
	
	@Override
	protected int stealStones(int distance) {
		/**
		 * Pass on the call from kalaha to empty opposing pit
		 * at same distance as initial empty pit
		 */
		if (distance > 0) {
			return nextHole.stealStones(distance - 1);
		}
		
		int temp = myStones;
		myStones = 0;
		return temp;
	}
	
	@Override
	protected boolean emptySide() {
		/**
		 * Check if all pits up to the next kalaha are empty
		 */
		if (myStones == 0) {
			return nextHole.emptySide();
		}
		return false;
	}
	
	@Override
	protected void setScore() {
		/**
		 * Give the amount of stones to the player's score
		 */
		myOwner.score += myStones;
		nextHole.setScore();
	}
}
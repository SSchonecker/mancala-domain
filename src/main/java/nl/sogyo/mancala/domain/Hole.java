package nl.sogyo.mancala.domain;

abstract class Hole {
	int myStones;
	Hole nextHole;
	Player myOwner;
	
	public Hole neighbour() {
		return nextHole;
	}
	
	public Hole neighbour(int distance) {
		/**
		 * Give the pit or kalaha at "distance" spaces counterclockwise
		 */
		if (distance > 1) {
			return nextHole.neighbour(distance - 1);
		}
		return nextHole;
	}
	
	abstract void receive(int givenStones);

	abstract void passStones() throws Exception;

	abstract int initiateStealing(int nrOfStones, int distance);

	abstract int stealStones(int distance);

	abstract boolean emptySide();
	
	abstract void setScore();
}
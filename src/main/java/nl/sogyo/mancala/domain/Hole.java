package nl.sogyo.mancala.domain;

public abstract class Hole {
	protected int myStones;
	protected Hole nextHole;
	protected Player myOwner;
	
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

	protected abstract void passStones() throws Exception;

	protected abstract int initiateStealing(int nrOfStones, int distance);

	protected abstract int stealStones(int distance);

	protected abstract boolean emptySide();
	
	protected abstract void setScore();
}
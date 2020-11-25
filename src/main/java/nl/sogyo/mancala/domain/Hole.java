package nl.sogyo.mancala.domain;

public abstract class Hole {
	protected int myStones;
	protected Hole nextHole;
	protected Player myOwner;
	
	void add() {
		myStones++;
	}
	
	public Hole neighbour() {
		return nextHole;
	}
	
	public Hole neighbour(int nthNghb) {
		if (nthNghb > 1) {
			return nextHole.neighbour(nthNghb - 1);
		}
		return nextHole;
	}
	
	abstract void receive(int givenStones);

	protected abstract void passStones();

	protected abstract int giveToKalaha(int nrOfStones, int distance);

	protected abstract int getStones(int distance);

	protected abstract boolean emptySide();
	
	protected abstract void setScore();
}
package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class KalahaTest {
	@Test
	public void kalahaContainsStones() {
		/**
		 * Check that the Kalaha knows how many stones it contains
		 */
		var thisKalaha = new Kalaha();
		var contained = thisKalaha.numberOfStones();
		assertEquals(0, contained);
	}
	
	@Test
	public void kalahaAddMultipleStones() {
		var thisKalaha = new Kalaha();
		ArrayList<Stone> toBeAdded = new ArrayList<Stone>(3);
		toBeAdded.add(new Stone());
		toBeAdded.add(new Stone());
		toBeAdded.add(new Stone());
		
		thisKalaha.takeAllStones(toBeAdded);
		assertEquals(3,thisKalaha.numberOfStones());
	}
	
	@Test
	public void kalahasOwner() {
		var thisKalaha = new Kalaha();
		var owner = new Player();
		owner.giveKalaha(thisKalaha);
		assertEquals(owner,thisKalaha.myOwner);
	}
	
	@Test
	public void pitPassKalahaReceive() {
		/**
		 * Check that the Stones from one pit are passed to its neighbouring kalaha
		 * which adds the first one to its own list
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone theStone = new Stone();
		Stoneslist.add(theStone);
		var pit1 = new Pit(Stoneslist);
		var theKalaha = new Kalaha();
		pit1.setNextHole(theKalaha);
		var start = theKalaha.numberOfStones();
		
		pit1.passStones();
		
		assertEquals(start+1,theKalaha.numberOfStones());
		assertTrue(theKalaha.myStones.contains(theStone));
	}
	
	@Test
	public void kalahaPassPitReceive() {
		/**
		 * Check that the Stones from one kalaha are passed to its neighbour
		 * which adds the first one to its own list
		 */
		var theKalaha = new Kalaha();

		ArrayList<Stone> Stoneslist2 = new ArrayList<Stone>(4);
		Stoneslist2.add(new Stone());
		Stoneslist2.add(new Stone());
		var pit2 = new Pit(Stoneslist2);
		
		theKalaha.setNextHole(pit2);
		var start = pit2.numberOfStones();
		
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone theStone = new Stone();
		Stoneslist.add(new Stone());
		Stoneslist.add(theStone);
		theKalaha.receive(Stoneslist);
		
		assertEquals(start+1,pit2.numberOfStones());
		assertTrue(pit2.myStones.contains(theStone));
	}
}
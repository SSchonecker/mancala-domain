package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PitTest {
	
	Pit setPit() {
		/**
		 * Create a new pit with 4 stones for testing
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(4);
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		return new Pit(Stoneslist);
	}

	@Test
	public void pit_contains_stones() {
		/**
		 * Check that the pit knows how many stones it contains
		 */
		var thisPit = setPit();
		var contained = thisPit.numberOfStones();
		assertEquals(4, contained);
	}
	
	public static void pit_contains_stones(Pit thisPit, int expectedNumber) {
		/**
		 * Check that the pit knows how many stones it contains
		 */
		var contained = thisPit.numberOfStones();
		assertEquals(expectedNumber, contained);
	}
    
	@Test
	public void pit_clears() {
		/**
		 * Check that the pit can empty itself,
		 * returning its stones
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		ArrayList<Stone> start = new ArrayList<Stone>(Stoneslist);
		var thisPit = new Pit(Stoneslist);
		/* Note that the Stoneslist passed to pit also gets cleared
		so a cloned list is needed for comparison */
		
		var returnedStones = thisPit.clear();
		
		assertTrue(start.containsAll(returnedStones));
		assertEquals(0, thisPit.numberOfStones());
	}
	
	@Test
	public void pit_add() {
		var thisPit = setPit();
		var start = thisPit.numberOfStones();
		thisPit.add(new Stone());
		assertEquals(start+1, thisPit.numberOfStones());
	}

	@Test
	public void pit_pass_and_receive() {
		/**
		 * Check that the Stones from one pit are passed to its neighbour
		 * which adds the first one to its own list
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone theStone = new Stone();
		Stoneslist.add(theStone);
		Stoneslist.add(new Stone());
		var pit1 = new Pit(Stoneslist);
		var pit2 = setPit();
		pit1.setNextHole(pit2);
		var start = pit2.numberOfStones();
		
		pit1.passStones();
		
		assertEquals(start+1,pit2.numberOfStones());
		assertTrue(pit2.myStones.contains(theStone));
	}

	/*public static void pit_pass_and_receive(ArrayList<Pit> thePits) {
	*	/**
	*	 * Check that the Stones from the pits are passed to their neighbours
	*	 */
	/*	
	*	thePits.get(0).passStones();
	*	
	*	assertEquals(start+1,pit2.numberOfStones());
	*	assertTrue(pit2.myStones.contains(theStone));
	}*/
}
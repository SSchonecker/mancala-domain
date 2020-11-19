package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PitTest {
	
	private Pit setPit() {
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(4);
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		return new Pit(Stoneslist);
	}

	@Test
	public void pit_contains_stones() {
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(4);
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		var thisPit = new Pit(Stoneslist);
		var contained = thisPit.numberOfStones();
		assertEquals(Stoneslist.size(), contained);
	}
    
	@Test
	public void pit_clears() {
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stoneslist.add(new Stone());
		Stoneslist.add(new Stone());
		ArrayList<Stone> start = new ArrayList<Stone>(Stoneslist);
		var thisPit = new Pit(Stoneslist);
		var returnedStones = thisPit.clear();
		assertEquals(start, returnedStones);
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
	public void pit_set_neighbor() {
		var pit1 = setPit();
		var pit2 = setPit();
		pit1.setNextHole(pit2);
		assertEquals(pit2, pit1.nextHole);
	}
	
	@Test
	public void pit_pass_neighbor() {
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone myStone = new Stone();
		Stoneslist.add(myStone);
		Stoneslist.add(new Stone());
		ArrayList<Stone> start = new ArrayList<Stone>(Stoneslist);
		var pit1 = new Pit(Stoneslist);
		var pit2 = setPit();
		pit1.setNextHole(pit2);
		pit1.passStones();
		assertTrue(pit2.myStones.contains(start.get(0)));
		assertEquals(0, pit1.numberOfStones());
	}

	@Test
	public void pit_receive_and_pass() {
		var thisPit = setPit();
		var start = thisPit.numberOfStones();
		
	}
}
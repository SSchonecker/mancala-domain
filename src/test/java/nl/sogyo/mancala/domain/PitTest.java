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
	public void pitContainsStones() {
		/**
		 * Check that the pit knows how many stones it contains
		 */
		var thisPit = setPit();
		var contained = thisPit.numberOfStones();
		assertEquals(4, contained);
	}
	
	@Test
	public void pitsOwner() {
		var pit1 = setPit();
		var pit2 = setPit();
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		var owner = new Player();
		owner.givePits(pitList);
		assertEquals(owner,pit1.myOwner);
		assertEquals(owner,pit2.myOwner);
	}
    
	@Test
	public void pitClears() {
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
		
		assertTrue(returnedStones.containsAll(start));
		assertEquals(0, thisPit.numberOfStones());
	}
	
	@Test
	public void pitAdd() {
		var thisPit = setPit();
		var start = thisPit.numberOfStones();
		thisPit.add(new Stone());
		assertEquals(start+1, thisPit.numberOfStones());
	}

	@Test
	public void pitPassAndReceive() {
		/**
		 * Check that all Stones from one pit are passed to its neighbour
		 * which adds the first one to its own list and passes further
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone theStone = new Stone();
		Stoneslist.add(theStone);
		Stoneslist.add(new Stone());
		var pit1 = new Pit(Stoneslist);
		var pit2 = setPit();
		var pit3 = setPit();
		pit1.setNextHole(pit2);
		pit2.setNextHole(pit3);
		var initNr2 = pit2.numberOfStones();
		var initNr3 = pit3.numberOfStones();
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		pitList.add(pit3);
		
		Player aPlayer = new Player();
		aPlayer.givePits(pitList);
		Player bPlayer = new Player();
		aPlayer.nextPlayer = bPlayer;
		
		pit1.passStones();
		
		assertEquals(initNr2+1, pit2.numberOfStones());
		assertTrue(pit2.myStones.contains(theStone));
		assertEquals(initNr3+1, pit3.numberOfStones());
	}
	
	@Test
	public void lastPitStealsStones() {
		ArrayList<Stone> emptylist = new ArrayList<Stone>(0);
		var pit1 = new Pit(emptylist);
		var pit2 = setPit();
		var pit3 = setPit();
		pit1.setNextHole(pit2);
		pit1.setOpponent(pit3);
		
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		
		Player startingPlayer = new Player();
		startingPlayer.myTurn = true;
		startingPlayer.givePits(pitList);
		Kalaha ownedKalaha = new Kalaha();
		startingPlayer.giveKalaha(ownedKalaha);

		Player bPlayer = new Player();
		startingPlayer.nextPlayer = bPlayer;
		
		ArrayList<Stone> Stonelist = new ArrayList<Stone>(1);
		Stonelist.add(new Stone());
		
		pit1.receive(Stonelist);
		
		assertEquals(0, pit3.numberOfStones());
		assertEquals(0, pit1.numberOfStones());
		assertEquals(5, ownedKalaha.numberOfStones());
		assertEquals(9, startingPlayer.stonesOwned()); // 4 from pit2, 4 from pit3, 1 added
	}
	
	@Test
	public void lastPitNotEmpty() {
		var pit1 = setPit();
		var pit2 = setPit();
		pit1.setNextHole(pit2);
		
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		
		Player startingPlayer = new Player();
		startingPlayer.myTurn = true;
		startingPlayer.givePits(pitList);
		Kalaha ownedKalaha = new Kalaha();
		startingPlayer.giveKalaha(ownedKalaha);

		Player bPlayer = new Player();
		startingPlayer.nextPlayer = bPlayer;
		
		ArrayList<Stone> Stonelist = new ArrayList<Stone>(1);
		Stonelist.add(new Stone());
		
		pit1.receive(Stonelist);
		
		assertEquals(5, pit1.numberOfStones());
		assertEquals(4, pit2.numberOfStones());
		assertEquals(9, startingPlayer.stonesOwned()); // 4 from pit1, 4 from pit2, 1 added
	}
}
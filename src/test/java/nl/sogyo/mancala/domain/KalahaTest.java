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
	
	/*@Test
	public void pitPassKalahaReceive() {
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
	}*/
	
	@Test
	public void pitPassKalahaCorrectOwner() {
		/**
		 * Check that the Stones from one pit are passed to its neighbouring kalaha
		 * which adds the first one to its own list if it's its owners turn
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(1);
		Stone theStone = new Stone();
		Stoneslist.add(theStone);
		var pit1 = new Pit(Stoneslist);

		var theKalaha = new Kalaha();
		pit1.setNextHole(theKalaha);
		
		Player player1 = new Player();
		player1.myTurn = true;
		player1.pitList.add(pit1);
		player1.giveKalaha(theKalaha);
		
		pit1.passStones();
		assertEquals(1, theKalaha.numberOfStones());
		assertTrue(theKalaha.myStones.contains(theStone));
	}
	
	@Test
	public void pitPassKalahaFalseOwner() {
		/**
		 * Check that the Stones from one pit are passed to its neighbouring kalaha
		 * which adds the first one to its own list if it's its owners turn
		 */
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone theStone = new Stone();
		Stoneslist.add(theStone);
		var pit1 = new Pit(Stoneslist);
		
		ArrayList<Stone> Stoneslist2 = new ArrayList<Stone>(2);
		Stone theStone2 = new Stone();
		Stoneslist.add(theStone2);
		var pit2 = new Pit(Stoneslist2);
		
		var theKalaha = new Kalaha();
		
		pit2.setNextHole(theKalaha);
		theKalaha.setNextHole(pit1);
		Player player1 = new Player();
		Player player2 = new Player();
		player1.myTurn = true;
		
		player1.pitList.add(pit1);
		player2.giveKalaha(theKalaha);
		player2.pitList.add(pit2);
		var start = theKalaha.numberOfStones();
		
		pit2.passStones();
		assertEquals(start, theKalaha.numberOfStones());
		assertTrue(pit1.myStones.contains(theStone2));
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
		Player player1 = new Player();
		Player player2 = new Player();
		player1.myTurn = true;

		player1.giveKalaha(theKalaha);
		player2.pitList.add(pit2);
		var start = pit2.numberOfStones();
		
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(2);
		Stone theStone = new Stone();
		Stoneslist.add(new Stone());
		Stoneslist.add(theStone);
		theKalaha.receive(Stoneslist);
		
		assertEquals(start+1, pit2.numberOfStones());
		assertTrue(pit2.myStones.contains(theStone));
	}
	
	@Test
	public void lastHoleOwnKalaha() {
		var kalaha1 = new Kalaha();
		
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(0);
		var pit1 = new Pit(Stoneslist);
		ArrayList<Pit> pitList = new ArrayList<Pit>();
		pitList.add(pit1);
		
		Player startingPlayer = new Player();
		startingPlayer.myTurn = true;
		startingPlayer.givePits(pitList);
		startingPlayer.giveKalaha(kalaha1);
		
		ArrayList<Stone> Stoneslist2 = new ArrayList<Stone>(1);
		Stoneslist2.add(new Stone());
		
		kalaha1.receive(Stoneslist2);
		
		assertEquals(0, pit1.numberOfStones());
		assertEquals(1, kalaha1.numberOfStones());
		assertEquals(1, startingPlayer.stonesOwned());
	}
	
	@Test
	public void lastHoleOpponentKalaha() {
		var kalaha1 = new Kalaha();
		
		ArrayList<Stone> Stoneslist = new ArrayList<Stone>(1);
		Stoneslist.add(new Stone());
		var pit1 = new Pit(Stoneslist);
		ArrayList<Pit> pitList = new ArrayList<Pit>();
		pitList.add(pit1);

		kalaha1.setNextHole(pit1);
		
		Player startingPlayer = new Player();
		startingPlayer.myTurn = false;
		startingPlayer.giveKalaha(kalaha1);
		
		Player opposingPlayer = new Player();
		opposingPlayer.myTurn = true;
		opposingPlayer.givePits(pitList);
		
		ArrayList<Stone> Stoneslist3 = new ArrayList<Stone>(1);
		Stoneslist3.add(new Stone());
		
		kalaha1.receive(Stoneslist3);
		
		assertEquals(2, pit1.numberOfStones());
		assertEquals(0, kalaha1.numberOfStones());
		assertEquals(0, startingPlayer.stonesOwned());
	}
}
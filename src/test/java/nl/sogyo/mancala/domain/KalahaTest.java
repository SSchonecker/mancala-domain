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
		var contained = thisKalaha.myStones;
		assertEquals(0, contained);
	}
	
	@Test
	public void kalahaAddMultipleStones() {
		var thisKalaha = new Kalaha();
		thisKalaha.takeAllStones(3);
		assertEquals(3, thisKalaha.myStones);
	}
	
	@Test
	public void kalahasOwner() {
		var thisKalaha = new Kalaha();
		var owner = new Player();
		var pit1 = new Pit(1);
		owner.pitList.add(pit1);
		owner.giveKalaha(thisKalaha);
		assertEquals(owner,thisKalaha.myOwner);
	}
	
	@Test
	public void pitPassKalahaCorrectOwner() {
		/**
		 * Check that the Stones from one pit are passed to 
		 * its neighbouring kalaha, which adds the first one to its own list
		 * if it's its owners turn
		 */
		var pit1 = new Pit(1);
		var theKalaha = new Kalaha();
		pit1.setNextHole(theKalaha);
		
		Player player1 = new Player();
		player1.isMyTurn = true;
		player1.pitList.add(pit1);
		player1.giveKalaha(theKalaha);
		
		pit1.passStones();
		assertEquals(1, theKalaha.myStones);
	}
	
	@Test
	public void pitPassKalahaFalseOwner() {
		/**
		 * Check that the stones from one pit are passed to
		 * its neighbouring kalaha, which doesn't add one to its own list
		 * if it's not its owners turn
		 */
		var pit1 = new Pit(2);
		var pit2 = new Pit(1);
		var theKalaha = new Kalaha();
		
		pit2.setNextHole(theKalaha);
		theKalaha.setNextHole(pit1);
		Player player1 = new Player();
		Player player2 = new Player();
		player2.nextPlayer = player1;
		player1.isMyTurn = true;
		pit1.myOwner = player2;
		
		player1.pitList.add(pit1);
		player2.pitList.add(pit2);
		player2.giveKalaha(theKalaha);
		var start = theKalaha.myStones;
		
		pit2.passStones();
		assertEquals(start, theKalaha.myStones);
	}
	
	@Test
	public void kalahaPassPitReceive() {
		/**
		 * Check that the Stones from one kalaha are passed to its neighbour
		 * which adds the first one to its own list
		 */
		var theKalaha = new Kalaha();
		var pit1 = new Pit(2);
		var pit2 = new Pit(2);
		
		theKalaha.setNextHole(pit2);
		Player player1 = new Player();
		Player player2 = new Player();
		player1.isMyTurn = true;
		player2.nextPlayer = player1;

		player1.pitList.add(pit1);
		pit1.myOwner = player1;
		player1.giveKalaha(theKalaha);
		player2.pitList.add(pit2);
		pit2.myOwner = player2;
		var start = pit2.myStones;

		theKalaha.receive(2);
		
		assertEquals(start+1, pit2.myStones);
	}
	
	@Test
	public void lastHoleOwnKalaha() {
		var kalaha1 = new Kalaha();
		var pit1 = new Pit(0);
		ArrayList<Pit> pitList = new ArrayList<Pit>();
		pitList.add(pit1);
		
		Player startingPlayer = new Player();
		startingPlayer.isMyTurn = true;
		startingPlayer.givePits(pitList);
		startingPlayer.giveKalaha(kalaha1);
		kalaha1.receive(1);
		
		assertEquals(0, pit1.myStones);
		assertEquals(1, kalaha1.myStones);
		assertEquals(1, startingPlayer.stonesOwned());
		assertTrue(startingPlayer.isMyTurn);
	}
	
	@Test
	public void lastHoleOpponentKalaha() {
		var kalaha1 = new Kalaha();
		var pit1 = new Pit(1);
		ArrayList<Pit> pitList = new ArrayList<Pit>();
		pitList.add(pit1);
		var pit2 = new Pit(1);

		kalaha1.setNextHole(pit1);
		
		Player startingPlayer = new Player();
		startingPlayer.pitList.add(pit2);
		startingPlayer.giveKalaha(kalaha1);
		
		Player opposingPlayer = new Player();
		opposingPlayer.isMyTurn = true;
		opposingPlayer.givePits(pitList);
		opposingPlayer.giveKalaha(new Kalaha());
		opposingPlayer.setNextPlayer(startingPlayer);
		kalaha1.receive(1);
		
		assertEquals(2, pit1.myStones);
		assertEquals(0, kalaha1.myStones);
		assertEquals(1, startingPlayer.stonesOwned());
	}
}
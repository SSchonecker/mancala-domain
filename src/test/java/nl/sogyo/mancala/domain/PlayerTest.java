package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
	
	private Board theBoard;
	private Player player1;
	private Player player2;
	
	@BeforeEach
	public void initiateBoard() {
		theBoard = new Board();
		player1 = theBoard.playerList.get(0);
		player2 = theBoard.playerList.get(1);
	}
	
	@Test
	public void setLayoutTest() {
		int[] stonesLayout = {4,3,5,4,4,4,0,3,3,6,4,4,4,0};
		theBoard.setLayout(stonesLayout);
		assertEquals(4, player1.pitList.get(0).numberOfStones());
		assertEquals(3, player1.pitList.get(1).numberOfStones());
		assertEquals(5, player1.pitList.get(2).numberOfStones());
		assertEquals(4, player1.pitList.get(3).numberOfStones());
		assertEquals(4, player1.pitList.get(4).numberOfStones());
		assertEquals(4, player1.pitList.get(5).numberOfStones());
		assertEquals(0, player1.myKalaha.numberOfStones());
		assertEquals(3, player2.pitList.get(0).numberOfStones());
		assertEquals(3, player2.pitList.get(1).numberOfStones());
		assertEquals(6, player2.pitList.get(2).numberOfStones());
		assertEquals(4, player2.pitList.get(3).numberOfStones());
		assertEquals(4, player2.pitList.get(4).numberOfStones());
		assertEquals(4, player2.pitList.get(5).numberOfStones());
		assertEquals(0, player2.myKalaha.numberOfStones());
	}

	@Test
	public void completePlayers() {
		/** 
		 * Check if the created players each have 6 pits, 24 stones
		 * and own a Kalaha, that is known to the pits as well
		 */
	    for (Player thePlayer : theBoard.playerList) {
			assertEquals(6, thePlayer.pitList.size());
			assertEquals(24, thePlayer.stonesOwned());
			assertEquals(thePlayer, thePlayer.myKalaha.myOwner);
			for (Pit eachPit : thePlayer.pitList) {
				assertEquals(thePlayer, eachPit.myOwner);
				assertEquals(thePlayer.myKalaha, eachPit.playersKalaha);
			}
		}
	    assertEquals(player1, player2.nextPlayer);
	    assertEquals(player2, player1.nextPlayer);
	}
	
	@Test
	public void selectFirstPit() {
		/**
		 * Check if the stones get correctly redistributed
		 */
		player1.selectPit(1);
		assertEquals(0, player1.pitList.get(0).numberOfStones());
		assertEquals(5, player1.pitList.get(1).numberOfStones());
		assertEquals(5, player1.pitList.get(2).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(5, player1.pitList.get(4).numberOfStones());
		assertEquals(4, player1.pitList.get(5).numberOfStones());
	}
	
	@Test
	public void playerSwitchTurn() {
		/**
		 * Check that the other player has the turn
		 */
		boolean initTurn1 = player1.isMyTurn;
		boolean initTurn2 = player2.isMyTurn;
		player1.selectPit(1);
		
		assertEquals(!initTurn1, player1.isMyTurn);
		assertEquals(!initTurn2, player2.isMyTurn);
	}
	
	@Test
	public void selectThirdPit() {
		/**
		 * Check that if the last stone ends in the Kalaha,
		 * the player keeps their turn
		 */
		player1.selectPit(3);
		assertEquals(0, player1.pitList.get(2).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(1, player1.myKalaha.numberOfStones());
		assertTrue(player1.isMyTurn);
		assertEquals(false, player2.isMyTurn);
	}
	
	@Test
	public void selectFourthPit() {
		/**
		 * Check that the turns are correctly changed if a move ends
		 * with a stone in the other player's pit
		 */
		player1.selectPit(4);
		assertEquals(0, player1.pitList.get(3).numberOfStones());
		assertEquals(5, player1.pitList.get(4).numberOfStones());
		assertEquals(1, player1.myKalaha.numberOfStones());
		assertEquals(5, player2.pitList.get(0).numberOfStones());
		assertEquals(false, player1.isMyTurn);
		assertTrue(player2.isMyTurn);
	}
	
	@Test
	public void endInEmptyPit() {
		/**
		 * Test for the stealing of stones of opposing pit
		 * and adding stones to own Kalaha
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,5,5,5,4,4,4,0};
		theBoard.setLayout(stonesLayout);
		player1.selectPit(2);
		
		assertEquals(0, player1.pitList.get(1).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(0, player1.pitList.get(5).numberOfStones());
		assertEquals(7, player1.myKalaha.numberOfStones());
		assertEquals(0, player2.pitList.get(0).numberOfStones());
		assertEquals(5, player2.pitList.get(2).numberOfStones());
		assertEquals(false, player1.isMyTurn);
		assertTrue(player2.isMyTurn);
	}
	
	@Test
	public void endInEmptyPitEmptyOpponent() {
		/**
		 * Test for the stealing of stones of opposing empty pit
		 * and not adding stones to own Kalaha
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		theBoard.setLayout(stonesLayout);
		player1.selectPit(2);
		
		assertEquals(0, player1.pitList.get(1).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(1, player1.pitList.get(5).numberOfStones());
		assertEquals(1, player1.myKalaha.numberOfStones());
		assertEquals(0, player2.pitList.get(0).numberOfStones());
		assertEquals(10, player2.pitList.get(1).numberOfStones());
		assertEquals(false, player1.isMyTurn);
		assertTrue(player2.isMyTurn);
	}
	
	@Test
	public void selectWrongPit() {
		/**
		 * In case a player selects a wrong or non-existing pit
		 * an error is thrown that could be dealt with later on
		 * (in an input-checker or sth)
		 */
		int[] stonesLayout = {4,0,5,5,5,0,7,0,5,5,4,4,4,0};
		theBoard.setLayout(stonesLayout);
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> player1.selectPit(7));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> player1.selectPit(-1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> player1.selectPit(2));
	}
	
	@Test
	public void opponentsEmptyPit() {
		/**
		 * Ensure nothing weird happens when ending in opponents empty pit
		 */
		int[] stonesLayout = {1,5,6,4,2,4,5,2,1,0,1,1,3,13};
		theBoard.setLayout(stonesLayout);
		player1.selectPit(6);
		
		assertEquals(0, player1.pitList.get(5).numberOfStones());
		assertEquals(6, player1.myKalaha.numberOfStones());
		assertEquals(3, player2.pitList.get(0).numberOfStones());
		assertEquals(2, player2.pitList.get(1).numberOfStones());
		assertEquals(1, player2.pitList.get(2).numberOfStones());
		assertEquals(false, player1.isMyTurn);
		assertTrue(player2.isMyTurn);
	}
	
	@Test
	public void passTwoKalahas() {
		/**
		 * Test the case where a move distributes stones past both kalaha's
		 */
		int[] stonesLayout = {1,1,3,1,10,0,8,2,4,4,4,4,4,2};
		theBoard.setLayout(stonesLayout);
		player1.selectPit(5);
		
		assertEquals(2, player1.pitList.get(1).numberOfStones());
		assertEquals(3, player1.pitList.get(2).numberOfStones());
		assertEquals(0, player1.pitList.get(4).numberOfStones());
		assertEquals(9, player1.myKalaha.numberOfStones());
		assertEquals(2, player2.myKalaha.numberOfStones());
		assertEquals(false, player1.isMyTurn);
		assertTrue(player2.isMyTurn);
	}
	
	@Test
	public void allStonesInOnePit() {
		/**
		 * Not very likely to happen, but if all stones end up in one pit,
		 * they still need to be correctly distributed in a move
		 */
		int[] stonesLayout = {0,0,0,48,0,0,0,0,0,0,0,0,0,0};
		theBoard.setLayout(stonesLayout);
		player1.selectPit(4);
		
		assertEquals(3, player1.pitList.get(0).numberOfStones());
		assertEquals(3, player1.pitList.get(3).numberOfStones());
		assertEquals(4, player1.pitList.get(4).numberOfStones());
		assertEquals(4, player1.myKalaha.numberOfStones());
		assertEquals(4, player2.pitList.get(5).numberOfStones());
		assertEquals(0, player2.myKalaha.numberOfStones());
		assertEquals(false, player1.isMyTurn);
		assertTrue(player2.isMyTurn);
	}
	
	@Test
	public void allPitsEmptyEnd() {
		/**
		 * If a player has only empty pits at the end, the game ends
		 * (It is assumed that the situation where a player starts with only
		 * empty pits will be dealt with eventually in a function responsible
		 * for letting the player make a turn, i.e. at turn start)
		 */
		int[] stonesLayout = {0,0,0,0,0,2,0,0,0,46,0,0,0,0};
		theBoard.setLayout(stonesLayout);
		player1.selectPit(6);

		assertEquals(false, theBoard.playGame());
		assertEquals(player2, theBoard.giveWinner());
	}
	
}
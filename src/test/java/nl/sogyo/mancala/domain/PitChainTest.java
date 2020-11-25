package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PitChainTest {
	
	private Pit initPit;
	
	@BeforeEach
	private void setPit() {
		initPit = new Pit(4,6);
	}
	
	private void setLayout(int[] stonesLayout) {
		initPit.myStones = stonesLayout[0];
		for (int i = 1; i < stonesLayout.length; i++) {
			initPit.neighbour(i).myStones = stonesLayout[i];
		}
	}
	
	@Test
	public void createPitChain() {		
		assertEquals(4, initPit.myStones);
		assertEquals(initPit.neighbour(), initPit.nextHole);
		assertEquals(4, initPit.neighbour().myStones);
		assertEquals(initPit.neighbour(), initPit.neighbour(1));
		assertEquals(0, initPit.neighbour(6).myStones);
		assertEquals(4, initPit.neighbour(7).myStones);
		assertEquals(4, initPit.neighbour(12).myStones);
		assertEquals(0, initPit.neighbour(13).myStones);
		
		assertEquals(-1, initPit.neighbour(6).getStones(0));
		assertEquals(-1, initPit.neighbour(13).getStones(0));
		
		assertEquals(initPit, initPit.neighbour(14));
	}
	
	@Test
	public void ownThePit() {
		Player player1 = initPit.myOwner;
		Player player2 = initPit.neighbour(7).myOwner;
		
		assertEquals(player1, player2.nextPlayer);
		assertEquals(player2, player1.nextPlayer);
		assertEquals(player2, initPit.neighbour(13).myOwner);
		assertEquals(player1, initPit.neighbour(14).myOwner);
	}
	
	@Test
	public void selectPit1() {
		initPit.passStones();
		
		assertEquals(0, initPit.myStones);
		assertEquals(5, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(4, initPit.neighbour(5).myStones);
		assertEquals(0, initPit.neighbour(6).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
		
		assertEquals(5, initPit.neighbour(4).myStones);
	}
	
	@Test
	public void selectPit2() {
		initPit.neighbour().passStones();
		
		assertEquals(4, initPit.myStones);
		assertEquals(0, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(0, initPit.neighbour(6).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void moveEndInKalaha() {

		initPit.neighbour(2).passStones();
		
		assertEquals(4, initPit.myStones);
		assertEquals(4, initPit.neighbour().myStones);
		assertEquals(0, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		
		assertTrue(initPit.myOwner.isMyTurn);
		assertFalse(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void selectPit4() {
		
		initPit.neighbour(3).passStones();
		
		assertEquals(4, initPit.myStones);
		assertEquals(4, initPit.neighbour().myStones);
		assertEquals(4, initPit.neighbour(2).myStones);
		assertEquals(0, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		assertEquals(5, initPit.neighbour(7).myStones);
		assertEquals(4, initPit.neighbour(8).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void setLayoutTest() {
		int[] stonesLayout = {4,3,5,4,4,4,0,3,3,6,4,4,4,0};
		setLayout(stonesLayout);
		assertEquals(4, initPit.myStones);
		assertEquals(3, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(4, initPit.neighbour(3).myStones);
		assertEquals(4, initPit.neighbour(4).myStones);
		assertEquals(4, initPit.neighbour(5).myStones);
		assertEquals(0, initPit.neighbour(6).myStones);
		assertEquals(3, initPit.neighbour(7).myStones);
		assertEquals(3, initPit.neighbour(8).myStones);
		assertEquals(6, initPit.neighbour(9).myStones);
		assertEquals(4, initPit.neighbour(10).myStones);
		assertEquals(4, initPit.neighbour(11).myStones);
		assertEquals(4, initPit.neighbour(12).myStones);
		assertEquals(0, initPit.neighbour(13).myStones);
	}
	
	@Test
	public void endInEmptyPit() {
		int[] stonesLayout = {4,4,4,4,4,0,1,5,5,5,4,4,4,0};
		setLayout(stonesLayout);
		initPit.neighbour().passStones();
		
		assertEquals(0, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(0, initPit.neighbour(5).myStones);
		assertEquals(7, initPit.neighbour(6).myStones);
		assertEquals(0, initPit.neighbour(7).myStones);
		assertEquals(5, initPit.neighbour(8).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void endInEmptyPitOfOpponent() {
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		setLayout(stonesLayout);
		initPit.neighbour(3).passStones();
		
		assertEquals(0, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(1, initPit.neighbour(5).myStones);
		assertEquals(2, initPit.neighbour(6).myStones);
		assertEquals(1, initPit.neighbour(7).myStones);
		assertEquals(10, initPit.neighbour(8).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void endInEmptyPitEmptyOpponent() {
		/**
		 * Test for the stealing of stones of opposing empty pit
		 * and not adding stones to own Kalaha
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		setLayout(stonesLayout);
		initPit.neighbour().passStones();
		
		assertEquals(0, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(1, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		assertEquals(0, initPit.neighbour(7).myStones);
		assertEquals(10, initPit.neighbour(8).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void passTwoKalahas() {
		/**
		 * Test the case where a move distributes stones past both kalaha's
		 */
		int[] stonesLayout = {1,1,3,1,10,0,8,2,4,4,4,4,4,2};
		setLayout(stonesLayout);
		initPit.neighbour(4).passStones();
		
		assertEquals(2, initPit.myStones);
		assertEquals(2, initPit.neighbour().myStones);
		assertEquals(3, initPit.neighbour(2).myStones);
		assertEquals(0, initPit.neighbour(4).myStones);
		assertEquals(9, initPit.neighbour(6).myStones); // own Kalaha
		assertEquals(2, initPit.neighbour(13).myStones); // opponent Kalaha
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(8).myOwner.isMyTurn);
	}
	
	@Test
	public void allPitsEmptyEnd() {
		/**
		 * If a player has only empty pits at the end, the game ends
		 * (It is assumed that the situation where a player starts with only
		 * empty pits will be dealt with eventually in a function responsible
		 * for letting the player make a turn, i.e. at turn start)
		 */
		boolean endGame = (initPit.emptySide() || 
				initPit.neighbour(7).emptySide());
		assertFalse(endGame);
		
		int[] stonesLayout = {0,0,0,0,0,2,0,0,0,46,0,0,0,0};
		setLayout(stonesLayout);
		initPit.neighbour(5).passStones();
		
		endGame = (initPit.emptySide() || 
				initPit.neighbour(7).emptySide());
		assertTrue(endGame);
		
		initPit.setScore();
		Player winner = initPit.myOwner;
		if (winner.nextPlayer.score > winner.score) {
			winner = winner.nextPlayer;
		}
		assertEquals(1, initPit.myOwner.score);
		assertEquals(47,initPit.neighbour(7).myOwner.score);
		assertEquals(initPit.neighbour(7).myOwner, winner);
	}
	
	@Test
	public void allPitsEmptyEnd2() {
		/**
		 * If a player has only empty pits at the end, the game ends
		 * (It is assumed that the situation where a player starts with only
		 * empty pits will be dealt with eventually in a function responsible
		 * for letting the player make a turn, i.e. at turn start)
		 */
	
		int[] stonesLayout = {0,0,4,0,8,2,0,0,0,0,0,0,0,34};
		setLayout(stonesLayout);
		
		boolean endGame = (initPit.emptySide() || 
				initPit.neighbour(7).emptySide());
		assertTrue(endGame);
		
		initPit.setScore();
		Player winner = initPit.myOwner;
		if (winner.nextPlayer.score > winner.score) {
			winner = winner.nextPlayer;
		}
		assertEquals(14, initPit.myOwner.score);
		assertEquals(34,initPit.neighbour(7).myOwner.score);
		assertEquals(initPit.neighbour(7).myOwner, winner);
	}
}
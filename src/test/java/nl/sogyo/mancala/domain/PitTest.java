package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PitTest {
	
	private Pit initPit;
	
	@BeforeEach
	private void setPit() {
		initPit = new Pit();
	}
	
	private void setLayout(int[] stonesLayout) {
		initPit.myStones = stonesLayout[0];
		for (int i = 1; i < stonesLayout.length; i++) {
			initPit.neighbour(i).myStones = stonesLayout[i];
		}
	}
	
	@Test
	public void createPitChain() {
		/**
		 * Ensure the right amount of starting stones
		 * in the first pit of each side,
		 * functioning neighbour methods,
		 * kalaha's at the expected positions
		 * and the return to the initial pit.
		 */
		assertEquals(4, initPit.myStones);
		assertEquals(initPit.nextHole, initPit.neighbour());
		assertEquals(initPit.nextHole.nextHole, initPit.neighbour(2));
		assertEquals(4, initPit.neighbour(7).myStones);
		
		assertTrue(initPit.neighbour(6) instanceof Kalaha);
		assertTrue(initPit.neighbour(13) instanceof Kalaha);
		
		assertEquals(initPit, initPit.neighbour(14));
	}
	
	@Test
	public void ownThePit() {
		/**
		 * Ensure the players are set and linked correctly
		 */
		Player player1 = initPit.myOwner;
		Player player2 = initPit.neighbour(7).myOwner;
		
		assertEquals(player1, player2.nextPlayer);
		assertEquals(player2, player1.nextPlayer);
		assertNotEquals(player1, player2);
		assertEquals(player2, initPit.neighbour(13).myOwner);
	}
	
	@Test
	public void selectPit1() {
		/**
		 * Ensure the selected pit correctly distributes stones
		 * and the players' turns are switched
		 */
		try {
			initPit.passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(0, initPit.myStones);
		assertEquals(5, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(4, initPit.neighbour(5).myStones);
		assertEquals(0, initPit.neighbour(6).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(7).myOwner.isMyTurn);
	}
	
	@Test
	public void moveEndInKalaha() {
		/**
		 * Ensure the selected pit correctly distributes stones
		 * and the players' turns are not switched
		 */
		try {
			initPit.neighbour(2).passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(4, initPit.myStones);
		assertEquals(4, initPit.neighbour().myStones);
		assertEquals(0, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		
		assertTrue(initPit.myOwner.isMyTurn);
		assertFalse(initPit.neighbour(7).myOwner.isMyTurn);
	}
	
	@Test
	public void movePastKalaha() {
		/**
		 * Ensure the stones are past over the kalaha
		 * and the players' turns are switched correctly
		 */
		try {
			initPit.neighbour(3).passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(0, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		assertEquals(5, initPit.neighbour(7).myStones);
		assertEquals(4, initPit.neighbour(8).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(7).myOwner.isMyTurn);
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
	public void passTwoKalahas() {
		/**
		 * Test the case where a move distributes stones past both kalaha's
		 */
		int[] stonesLayout = {1,1,3,1,10,0,8,2,4,4,4,4,4,2};
		setLayout(stonesLayout);
		try {
			initPit.neighbour(4).passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(2, initPit.myStones);
		assertEquals(2, initPit.neighbour().myStones);
		assertEquals(3, initPit.neighbour(2).myStones);
		assertEquals(0, initPit.neighbour(4).myStones);
		assertEquals(9, initPit.neighbour(6).myStones); // own Kalaha
		assertEquals(2, initPit.neighbour(13).myStones); // opponent Kalaha
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(7).myOwner.isMyTurn);
	}
	
	@Test
	public void selectWrongPit() {
		/**
		 * Ensure the correct Exceptions are thrown
		 * if a kalaha, empty pit or opponent's pit is selected
		 * (these can be dealt with later on)
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,5,5,0,4,4,4,5};
		setLayout(stonesLayout);
		assertThrows(IndexOutOfBoundsException.class, 
				() -> initPit.neighbour(6).passStones()); // The kalaha
		assertThrows(IndexOutOfBoundsException.class, 
				() -> initPit.neighbour(5).passStones()); // Empty pit
		assertThrows(IndexOutOfBoundsException.class, 
				() -> initPit.neighbour(7).passStones()); // Opponents pit
		assertThrows(IndexOutOfBoundsException.class, 
				() -> initPit.neighbour(9).passStones()); // Opponents empty pit
	}
	
	@Test
	public void endInEmptyPit() {
		/**
		 * Test the stealing behaviour
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,5,5,5,4,4,4,0};
		setLayout(stonesLayout);
		try {
			initPit.neighbour().passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(0, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(0, initPit.neighbour(5).myStones);
		assertEquals(7, initPit.neighbour(6).myStones);
		assertEquals(0, initPit.neighbour(7).myStones);
		assertEquals(5, initPit.neighbour(8).myStones);
		
		assertFalse(initPit.myOwner.isMyTurn);
		assertTrue(initPit.neighbour(7).myOwner.isMyTurn);
	}
	
	@Test
	public void endInEmptyPitOfOpponent() {
		/**
		 * Ensure no stones are stolen if ending in
		 * opponent's empty pit
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		setLayout(stonesLayout);
		try {
			initPit.neighbour(3).passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		 * Ensure no stones are added to the kalaha if
		 * opponent's pit is already empty
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		setLayout(stonesLayout);
		try {
			initPit.neighbour().passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public void allPitsEmptyEnd() {
		/**
		 * If a player has only empty pits at the end, the game ends
		 * (It is assumed that the situation where a player starts with only
		 * empty pits will be dealt with eventually in a function responsible
		 * for letting the player make a turn, i.e. at turn start)
		 */
		int[] stonesLayout = {0,0,0,0,0,2,0,0,0,46,0,0,0,0};
		setLayout(stonesLayout);
		try {
			initPit.neighbour(5).passStones();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		boolean endGame = (initPit.emptySide() || 
				initPit.neighbour(7).emptySide());
		assertTrue(endGame);
	}
	
	@Test
	public void noPitsEmptyEnd() {
		boolean endGame = (initPit.emptySide() || 
			initPit.neighbour(7).emptySide());
		assertFalse(endGame);
	}
	
	@Test
	public void allPitsEmptyEnd2() {
		/**
		 * If a player has only empty pits at the end, the game ends
		 * and the winner is determined from the players' scores
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
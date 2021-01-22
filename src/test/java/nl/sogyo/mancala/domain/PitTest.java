package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertEquals(4, initPit.neighbour(7).myStones);
		
		assertEquals(initPit.nextHole, initPit.neighbour());
		assertEquals(initPit.nextHole.nextHole, initPit.neighbour(2));
		
		assertTrue(initPit.neighbour(6) instanceof Kalaha);
		assertTrue(initPit.neighbour(13) instanceof Kalaha);
		
		assertEquals(initPit, initPit.neighbour(14));
	}
	
	@Test
	public void correctPlayers() {
		/**
		 * Ensure the players are linked correctly
		 */
		Player player1 = initPit.getOwner();
		Player player2 = initPit.neighbour(7).getOwner();
		
		assertEquals(player1, player2.getOpponent());
		assertEquals(player2, player1.getOpponent());
		assertNotEquals(player1, player2);
	}
	
	@Test
	public void selectPit1() {
		/**
		 * Ensure the selected pit correctly distributes stones
		 * and the players' turns are switched
		 */
		initPit.passStones();

		assertEquals(0, initPit.myStones);
		assertEquals(5, initPit.neighbour().myStones);
		assertEquals(5, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		
		assertFalse(initPit.getOwner().hasTurn());
		assertTrue(initPit.neighbour(7).getOwner().hasTurn());
	}
	
	@Test
	public void endMoveInKalaha() {
		/**
		 * Ensure the selected pit correctly distributes stones
		 * and the players' turns are not switched
		 */
		initPit.neighbour(2).passStones();

		assertEquals(0, initPit.neighbour(2).myStones);
		assertEquals(5, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		
		assertTrue(initPit.getOwner().hasTurn());
		assertFalse(initPit.neighbour(7).getOwner().hasTurn());
	}
	
	@Test
	public void movePastKalaha() {
		/**
		 * Ensure the stones are past over the kalaha
		 * and the players' turns are switched correctly
		 */
		initPit.neighbour(3).passStones();
		
		assertEquals(0, initPit.neighbour(3).myStones);
		assertEquals(5, initPit.neighbour(4).myStones);
		assertEquals(5, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		assertEquals(5, initPit.neighbour(7).myStones);
		
		assertFalse(initPit.getOwner().hasTurn());
		assertTrue(initPit.neighbour(7).getOwner().hasTurn());
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
		initPit.neighbour(4).passStones();

		assertEquals(2, initPit.neighbour().myStones);
		assertEquals(3, initPit.neighbour(2).myStones);
		assertEquals(0, initPit.neighbour(4).myStones);
		assertEquals(9, initPit.neighbour(6).myStones); // own Kalaha
		assertEquals(2, initPit.neighbour(13).myStones); // opponent Kalaha
	}
	
	@Test
	public void passSelectedPit() {
		/**
		 * Test the case where a move distributes stones
		 * past the initially selected pit
		 */
		int[] stonesLayout = {1,1,3,1,16,0,2,2,4,4,4,4,4,2};
		setLayout(stonesLayout);
		initPit.neighbour(4).passStones();

		assertEquals(1, initPit.neighbour(4).myStones);
	}
	
	@Test
	public void selectWrongPit() {
		/**
		 * Ensure the passStones() gives false and doesn't change the state
		 * if a kalaha, empty pit or opponent's pit is selected
		 * (these can be dealt with later on)
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,5,5,0,4,4,4,5};
		setLayout(stonesLayout);
		assertFalse(initPit.neighbour(6).passStones()); // The kalaha
		assertEquals(1, initPit.neighbour(6).myStones);
		
		assertFalse(initPit.neighbour(5).passStones()); // Empty pit
		
		assertFalse(initPit.neighbour(7).passStones()); // Opponents pit
		assertEquals(5, initPit.neighbour(7).myStones);
		
		assertFalse(initPit.neighbour(9).passStones()); // Opponents empty pit
	}
	
	@Test
	public void endInEmptyPit() {
		/**
		 * Test the stealing behaviour: if a move ends in an empty pit
		 * its stones and its opponent's stones are added to the kalaha
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,5,5,5,4,4,4,0};
		setLayout(stonesLayout);
		initPit.neighbour().passStones();
		
		assertEquals(0, initPit.neighbour().myStones);
		assertEquals(7, initPit.neighbour(6).myStones);
		assertEquals(0, initPit.neighbour(7).myStones);
	}
	
	@Test
	public void endInEmptyPitOfOpponent() {
		/**
		 * Ensure no stones are stolen if ending in
		 * opponent's empty pit
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		setLayout(stonesLayout);
		initPit.neighbour(3).passStones();

		assertEquals(2, initPit.neighbour(6).myStones);
		assertEquals(1, initPit.neighbour(7).myStones);
	}
	
	@Test
	public void endInEmptyPitEmptyOpponent() {
		/**
		 * Ensure no stones are moved from the originally empty pit 
		 * and added to the kalaha if opponent's pit is already empty
		 */
		int[] stonesLayout = {4,4,4,4,4,0,1,0,10,5,4,4,4,0};
		setLayout(stonesLayout);
		initPit.neighbour().passStones();

		assertEquals(1, initPit.neighbour(5).myStones);
		assertEquals(1, initPit.neighbour(6).myStones);
		assertEquals(0, initPit.neighbour(7).myStones);
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
		initPit.neighbour(5).passStones();
		
		boolean endGame = (initPit.emptySide() || 
				initPit.neighbour(7).emptySide());
		assertTrue(endGame);
	}
	
	@Test
	public void noPitsEmpty() {
		boolean endGame = (initPit.emptySide() || 
			initPit.neighbour(7).emptySide());
		
		assertFalse(endGame);
	}
	
	@Test
	public void correctWinner() {
		/**
		 * Ensure the correct score is set
		 * and the correct winner is determined
		 */
		int[] stonesLayout = {0,0,4,0,8,2,0,0,0,0,0,0,0,34};
		setLayout(stonesLayout);
		initPit.setScore();
		Player winner = initPit.getOwner();
		if (winner.getOpponent().getScore() > winner.getScore()) {
			winner = initPit.getOwner().getOpponent();
		}
		
		assertEquals(14, initPit.getOwner().getScore());
		assertEquals(34,initPit.neighbour(7).getOwner().getScore());
		assertEquals(initPit.neighbour(7).getOwner(), winner);
	}
	
	@Test
	public void correctWinnerOnTie() {
		/**
		 * Ensure the correct winner is determined
		 * in case both sides have equal amount of stones
		 */
		int[] stonesLayout = {0,0,1,0,1,0,22,0,0,0,0,0,0,24};
		setLayout(stonesLayout);
		initPit.setScore();
		Player winner = initPit.getOwner();
		int winnerIndex = 0;
		if (winner.getOpponent().getScore() > winner.getScore() ||
				(winner.getOpponent().getScore() == winner.getScore() &&
				initPit.neighbour(13).myStones > initPit.neighbour(6).myStones) ) {
			winner = winner.getOpponent();
			winnerIndex = 1;
		}
		
		assertEquals(winner.getScore(), winner.getOpponent().getScore());
		assertEquals(initPit.neighbour(7).getOwner(), winner);
		assertEquals(1, winnerIndex);
	}
	
	@Test
	public void correctWinnerOnTieOpposite() {
		/**
		 * Ensure the correct winner is determined
		 * in case both sides have equal amount of stones
		 */
		int[] stonesLayout = {0,0,0,0,0,0,24,0,1,0,1,0,0,22};
		setLayout(stonesLayout);
		initPit.setScore();
		Player winner = initPit.getOwner();
		int winnerIndex = 0;
		if (winner.getOpponent().getScore() > winner.getScore() ||
				(winner.getOpponent().getScore() == winner.getScore() &&
				initPit.neighbour(13).myStones > initPit.neighbour(6).myStones) ) {
			winner = winner.getOpponent();
			winnerIndex = 1;
		}
		
		assertEquals(winner.getScore(), winner.getOpponent().getScore());
		assertEquals(initPit.getOwner(), winner);
		assertEquals(0, winnerIndex);
	}
}
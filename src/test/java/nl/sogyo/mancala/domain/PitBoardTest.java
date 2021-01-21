package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PitBoardTest {
	
	Mancala myBoard;
	
	@BeforeEach
	private void setBoard() {
		myBoard = new PitBoard();
		myBoard.setPlayerName("Ash", 1);
		myBoard.setPlayerName("Ben", 2);
	}
	
	@Test
	public void checkNames() {
		assertEquals("Ash", myBoard.getPlayerName(1));
		assertEquals("Ben", myBoard.getPlayerName(2));
	}
	
	@Test
	public void wrongNameIndeces() {
		assertThrows(IllegalStateException.class, () -> myBoard.getPlayerName(3));
		assertThrows(IllegalStateException.class, () -> myBoard.setPlayerName("Charlie", 3));
	}
	
	@Test
	public void playerHasTurnTest() {
		assertTrue(myBoard.isToMovePlayer(1));
		assertFalse(myBoard.isToMovePlayer(2));
		assertThrows(IllegalStateException.class, () -> myBoard.isToMovePlayer(3));
	}
	
	@Test
	public void receiveCorrectGameState() {
		
		int[] expectedLayout = {4,4,4,0,5,5,1,5,4,4,4,4,4,0,2};
		
		assertArrayEquals(expectedLayout, myBoard.playRecess(4));
		assertArrayEquals(expectedLayout, myBoard.playRecess(5));
	}
	
	@Test
	public void correctNrOfStonesFromPitRequest() {
		myBoard.playRecess(1);
		
		assertEquals(0, myBoard.getStonesForPit(1));
	}
	
	@Test
	public void endOfGameTest() {
		// This has been tested in the PitTest
	}

}

package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PlayerTest {

	@Test
	public void complete_Players() {
		Board theBoard = new Board();
	    for (Player thePlayer : theBoard.playerList) {
			assertEquals(6, thePlayer.pitList.size());
			assertEquals(24, thePlayer.stonesOwned());
			assertEquals(thePlayer, thePlayer.myKalaha.myOwner);
			for (Pit eachPit : thePlayer.pitList) {
				assertEquals(thePlayer, eachPit.myOwner);
				assertEquals(thePlayer.myKalaha, eachPit.playersKalaha);
			}
			assertNotNull(thePlayer.nextPlayer);
		}
	}
	
	@Test
	public void selectFirstPit() {
		Board theBoard = new Board();
		Player player1 = theBoard.playerList.get(0);
		player1.selectPit(1);
		assertEquals(0, player1.pitList.get(0).numberOfStones());
		assertEquals(5, player1.pitList.get(1).numberOfStones());
		assertEquals(5, player1.pitList.get(1).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(5, player1.pitList.get(4).numberOfStones());
		assertEquals(4, player1.pitList.get(5).numberOfStones());
	}
	
	@Test
	public void playerSwitchTurn() {
		Board theBoard = new Board();
		Player player1 = theBoard.playerList.get(0);
		Player player2 = theBoard.playerList.get(1);
		player1.myTurn = true;
		boolean initTurn1 = player1.myTurn;
		boolean initTurn2 = player2.myTurn;
		player1.selectPit(1);
		
		assertEquals(!initTurn1, player1.myTurn);
		assertEquals(!initTurn2, player2.myTurn);
	}
	
	@Test
	public void selectThirdPit() {
		Board theBoard = new Board();
		Player player1 = theBoard.playerList.get(0);
		player1.myTurn = true;
		player1.selectPit(3);
		assertEquals(0, player1.pitList.get(2).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(1, player1.myKalaha.numberOfStones());
		assertTrue(player1.myTurn);
	}
	
	@Test
	public void selectFourthPit() {
		Board theBoard = new Board();
		Player player1 = theBoard.playerList.get(0);
		Player player2 = theBoard.playerList.get(1);
		player1.myTurn = true;
		player1.selectPit(4);
		assertEquals(0, player1.pitList.get(3).numberOfStones());
		assertEquals(5, player1.pitList.get(4).numberOfStones());
		assertEquals(1, player1.myKalaha.numberOfStones());
		assertEquals(5, player2.pitList.get(0).numberOfStones());
		assertEquals(false, player1.myTurn);
	}
	
	@Test
	public void endInEmptyPit() {
		Board theBoard = new Board();
		Player player1 = theBoard.playerList.get(0);
		Player player2 = theBoard.playerList.get(1);
		player1.myTurn = true;
		player1.selectPit(6);
		player1.myTurn = true;
		player1.selectPit(2);
		
		assertEquals(0, player1.pitList.get(1).numberOfStones());
		assertEquals(0, player1.pitList.get(5).numberOfStones());
		assertEquals(0, player2.pitList.get(0).numberOfStones());
		assertEquals(5, player1.pitList.get(3).numberOfStones());
		assertEquals(7, player1.myKalaha.numberOfStones());
		assertEquals(5, player2.pitList.get(2).numberOfStones());
		assertEquals(false, player1.myTurn);
	}
	
	@Test
	public void setLayoutTest() {
		Board theBoard = new Board();
		int[] stonesLayout = {4,3,5,4,4,4,0,3,3,6,4,4,4,0};
		theBoard.setLayout(stonesLayout);
		
		Player player1 = theBoard.playerList.get(0);
		Player player2 = theBoard.playerList.get(1);
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
	public void selectWrongPit() {
		Board theBoard = new Board();
		int[] stonesLayout = {4,0,5,5,5,0,7,0,5,5,4,4,4,0};
		theBoard.setLayout(stonesLayout);
		Player player1 = theBoard.playerList.get(0);
		player1.myTurn = true;
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> player1.selectPit(7));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> player1.selectPit(-1));
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> player1.selectPit(2));
	}
	
	@Test
	public void opponentsEmptyPit() {
		Board theBoard = new Board();
		int[] stonesLayout = {1,5,6,4,2,4,5,2,1,0,1,1,3,13};
		theBoard.setLayout(stonesLayout);
		Player player1 = theBoard.playerList.get(0);
		Player player2 = theBoard.playerList.get(1);
		player1.myTurn = true;
		player1.selectPit(6);
		
		assertEquals(0, player1.pitList.get(5).numberOfStones());
		assertEquals(6, player1.myKalaha.numberOfStones());
		assertEquals(3, player2.pitList.get(0).numberOfStones());
		assertEquals(2, player2.pitList.get(1).numberOfStones());
		assertEquals(1, player2.pitList.get(2).numberOfStones());
		assertEquals(false, player1.myTurn);
		assertTrue(player2.myTurn);
	}
	
}
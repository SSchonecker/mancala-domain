package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PitTest {

	@Test
	public void pitContainsStones() {
		/**
		 * Check that the pit knows how many stones it contains
		 */
		var thisPit = new Pit(4);
		var contained = thisPit.myStones;
		assertEquals(4, contained);
	}
	
	@Test
	public void pitsOwner() {
		var pit1 = new Pit(4);
		var pit2 = new Pit(4);
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		var owner = new Player();
		owner.givePits(pitList);
		assertEquals(owner,pit1.myOwner);
		assertEquals(owner,pit2.myOwner);
	}

	@Test
	public void pitPassAndReceive() {
		/**
		 * Check that all Stones from one pit are passed to its neighbour
		 * which adds one to itself and passes further
		 */
		var pit1 = new Pit(2);
		var pit2 = new Pit(4);
		var pit3 = new Pit(4);
		pit1.setNextHole(pit2);
		pit2.setNextHole(pit3);
		var initNr2 = pit2.myStones;
		var initNr3 = pit3.myStones;
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		pitList.add(pit3);
		
		Player aPlayer = new Player();
		aPlayer.givePits(pitList);
		Player bPlayer = new Player();
		aPlayer.nextPlayer = bPlayer;
		
		pit1.passStones();
		
		assertEquals(initNr2+1, pit2.myStones);
		assertEquals(initNr3+1, pit3.myStones);
	}
	
	@Test
	public void lastPitStealsStones() {
		var pit1 = new Pit(0);
		var pit2 = new Pit(4);
		var pit3 = new Pit(4);
		pit1.setNextHole(pit2);
		pit1.setOpponentPit(pit3);
		
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		
		Player startingPlayer = new Player();
		startingPlayer.isMyTurn = true;
		startingPlayer.givePits(pitList);
		Kalaha ownedKalaha = new Kalaha();
		startingPlayer.giveKalaha(ownedKalaha);

		Player bPlayer = new Player();
		startingPlayer.nextPlayer = bPlayer;
		
		pit1.receive(1);
		
		assertEquals(0, pit3.myStones);
		assertEquals(0, pit1.myStones);
		assertEquals(5, ownedKalaha.myStones);
		assertEquals(9, startingPlayer.stonesOwned()); // 4 from pit2, 4 from pit3, 1 added
	}
	
	@Test
	public void lastPitNotEmpty() {
		var pit1 = new Pit(4);
		var pit2 = new Pit(4);
		pit1.setNextHole(pit2);
		
		ArrayList<Pit> pitList = new ArrayList<Pit>(2);
		pitList.add(pit1);
		pitList.add(pit2);
		
		Player startingPlayer = new Player();
		startingPlayer.isMyTurn = true;
		startingPlayer.givePits(pitList);
		Kalaha ownedKalaha = new Kalaha();
		startingPlayer.giveKalaha(ownedKalaha);

		Player bPlayer = new Player();
		startingPlayer.nextPlayer = bPlayer;
		
		pit1.receive(1);
		
		assertEquals(5, pit1.myStones);
		assertEquals(4, pit2.myStones);
		assertEquals(9, startingPlayer.stonesOwned()); // 4 from pit1, 4 from pit2, 1 added
	}
}
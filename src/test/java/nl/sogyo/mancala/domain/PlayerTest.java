package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PlayerTest {

	public static void complete_Player(Player thePlayer) {
		//TODO check if player is completely initiated
		assertEquals(6,thePlayer.pitList.size());
		assertEquals(24,thePlayer.stonesOwned());
		assertEquals(thePlayer, thePlayer.myKalaha.myOwner);
	}
	
}
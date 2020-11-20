package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void initiated_Board_correct()
    {
       Board theBoard = new Board();
       for (Player eachPlayer : theBoard.playerList) {
    	   PlayerTest.complete_Player(eachPlayer);
       }
    }

}
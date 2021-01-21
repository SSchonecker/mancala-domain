package nl.sogyo.mancala.domain;

public class PitBoard implements Mancala {
	
	private Pit thePits;
	private String[] playerNames = new String[2];
	private Player[] thePlayers = new Player[2];
	
	/**
	 * Main method to be used in the docker container,
	 * necessary for the GitLab-CI exercise
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Long live my pits!");
	}
	
	public PitBoard() {
		thePits = new Pit();
		thePlayers[0] = thePits.getOwner();
		thePlayers[1] = thePits.neighbour(8).getOwner();
	}

	/**
	 * Method for returning the specified players name. 
	 * 
	 * @param playerIndex Index of the player, 1 for the first player and 2 for the second player
	 * @return Name of the specified player.
	 * @throws IllegalStateException If the players index is not 1 or 2.
	 */
	public String getPlayerName(int playerIndex) throws IllegalStateException {
		if (playerIndex != 1 && playerIndex != 2) {
			throw new IllegalStateException();
		}
		return playerNames[playerIndex - 1];
	}
	
	/**
	 * Method for giving the specified player his or her name.
	 * 
	 * @param name Name for the player.
	 * @param playerIndex Index of the player, 1 for the first player and 2 for the second player
	 * @throws IllegalStateException If the players index is not 1 or 2.
	 */
	public void setPlayerName(String name, int playerIndex) throws IllegalStateException {
		if (playerIndex != 1 && playerIndex != 2) {
			throw new IllegalStateException();
		}
		else {
			playerNames[playerIndex - 1] = name;
		}
	}
	
	/**
	 * Method indicating if the specified player has the next turn of not.
	 * 
	 * @param playerIndex Index of the player, 1 for the first player and 2 for the second player.
	 * @return True if the specified player has the next turn, otherwise False.
	 * @throws IllegalStateException If the players index is not 1 or 2.
	 */
	public boolean isToMovePlayer(int playerIndex) throws IllegalStateException {
		if (playerIndex != 1 && playerIndex != 2) {
			throw new IllegalStateException();
		}
		return thePlayers[playerIndex - 1].hasTurn();
	}
	
	/**
	 * Method for playing the specified recess. Index is as specified below:
	 * 
	 *    13 12 11 10  9  8
	 * 14                    7
	 *     1  2  3  4  5  6
	 * 
	 * @param index Index of the recess to be played.
	 * @return 15 item long Array with the current state of the game. The 15th item indicates which player has the next turn (possible values are 1 or 2).
	 */
	public int[] playRecess(int index) {
		
		if (index == 1) {
			thePits.passStones();
		}
		else {
			thePits.neighbour(index - 1).passStones();
		}
		
		return exportGameState();
	}
	
	/**
	 * Method for returning the amount of stones in the specified pit. Index is as specified below:
	 * 
	 *    13 12 11 10  9  8
	 * 14                    7
	 *     1  2  3  4  5  6
	 * 
	 * @param index Index of the pit.
	 * @return Amount of stone.
	 */
	public int getStonesForPit(int index) {
		if (index == 1) {
			return thePits.myStones;
		}
		return thePits.neighbour(index - 1).myStones;
	}
	
	/**
	 * Method for retrieving the current state of the game. 
	 * 
	 *    13 12 11 10  9  8
	 * 14                    7
	 *     1  2  3  4  5  6
	 * 
	 * @return 15 item long Array with the current state of the game. The 15th item indicates which player has the next turn (possible values are 1 or 2).
	 */
	public int[] exportGameState() {
		int[] gameState = new int[15];
		
		gameState[0] = thePits.myStones;
		for (int i = 1; i < 14; i++) {
			gameState[i] = thePits.neighbour(i).myStones;
		}
		
		gameState[14] = 1;
		if (thePlayers[1].hasTurn()) {
			gameState[14] = 2;
		}
		
		return gameState;
	}

	/**
	 * Method for retrieving whether the game has ended or not.
	 * 
	 * @return True is the game has ended otherwise False.
	 */
	public boolean isEndOfGame() {
		return (thePits.emptySide() || 
				thePits.neighbour(7).emptySide());
	}

	/**
	 * Method for retrieving the name of the player that has won the game.
	 * 
	 * @return Name of the winner, or 'null' if the game has not ended yet.
	 */
	public String getWinnersName() {
		if (isEndOfGame()) {
			thePits.setScore();
			Player winner = thePlayers[0];
			int winnerIndex = 0;
			if (winner.getOpponent().getScore() > winner.getScore()) {
				winner = winner.getOpponent();
				winnerIndex = 1;
			}
			
			return playerNames[winnerIndex];
		}
		return null;
	}
}
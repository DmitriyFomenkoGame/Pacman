package Pacman;

public class PacmanGame {
	
	private Board board;
	private PacmanScore score;
	private int gameStatus;
	private int maxGameticks;
	
	public static final int DIR_UP    = 0,
							DIR_RIGHT = 1,
							DIR_DOWN  = 2,
							DIR_LEFT  = 3;
	
	public static final int GAME_BUSY    = 0,
							GAME_OVER    = 1,
							GAME_END     = 2,
							GAME_TIMEOUT = 3;
	
	public PacmanGame(int maxGameticks) { //Value <0 enables infinit gameticks
		board 			  = new Board();
		score 			  = new PacmanScore();
		gameStatus		  = GAME_BUSY;
		this.maxGameticks = maxGameticks;
	}
	
	public void doMove(int direction) {
		if (gameStatus != GAME_BUSY) {return;}
		score.addScore(board.doMove(direction));
		if (maxGameticks > 0) {
			if (score.getGameticks() > maxGameticks) {
				gameStatus = GAME_TIMEOUT;
			}
		}
	}
	
	public Board getBoard() {
		return (Board) board.clone();
	}
	
	public PacmanScore getScore() {
		return (PacmanScore) score.clone();
	}
	
	
	
	
}

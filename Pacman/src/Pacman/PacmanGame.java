package Pacman;

public class PacmanGame {	
	public static final byte DIR_UP    = 0,
						 	 DIR_RIGHT = 1,
							 DIR_DOWN  = 2,
							 DIR_LEFT  = 3;
	
	public static final byte GAME_BUSY    = 0,
						 	 GAME_OVER    = 1,
							 GAME_END     = 2,
							 GAME_TIMEOUT = 3;
	
	public static final int GTPS = 10; //GameTicksPerSecond
	public static final int TIME_SCATTER     =  7 * GTPS,
							TIME_CHASE       = 20 * GTPS,
							TIME_FRIGHTENED  =  5 * GTPS,
							TIME_DOTEATING   =  4 * GTPS;
	
	private Board board;
	private PacmanScore score;
	private byte gameStatus;
	private int maxGameticks;
	private int chaseScatterTimer,
				frightenedTimer,
				dotTimer;
	private byte ghostMode;
	
	public PacmanGame(int maxGameticks) { //Value <0 enables infinit gameticks
		board 			  = new Board();
		score 			  = new PacmanScore();
		
		gameStatus		  = GAME_BUSY;
		this.maxGameticks = maxGameticks;

		chaseScatterTimer = TIME_SCATTER;
		ghostMode = Ghost.MODE_SCATTER;
		board.setModes(ghostMode);
		dotTimer = TIME_DOTEATING;
	}
	
	public void doMove(byte direction) {
		if (gameStatus != GAME_BUSY) {return;}
		board.doMove(direction);
		board.updateGhosts();
		PacmanScore stepscore = board.getScore();
		score.addScore(stepscore);
		
		if (stepscore.getEnergizers() > 0) {
			frightenedTimer = TIME_FRIGHTENED;
			board.setModes(Ghost.MODE_FRIGHTENED);
		}
		
		if (score.getDots() >= 30) {
			board.activateGhost(Ghost.GHOST_INKY);
		}
		if (score.getDots() >= 90) {
			board.activateGhost(Ghost.GHOST_CLYDE);
			dotTimer = 0;
		}
		
		if (stepscore.getDots() > 0 && dotTimer != 0) {
			dotTimer = TIME_DOTEATING;
		}
		
		if (maxGameticks > 0 && score.getGameticks() > maxGameticks) {
			gameStatus = GAME_TIMEOUT;
		}
		
		if (score.getDeaths() > 0) 		   { gameStatus = GAME_OVER; }
		if (board.getDotsRemaining() <= 0) { gameStatus = GAME_END;  }
		
		updateTimers();
	}
	public void updateTimers() {
		if (dotTimer != 0) {
			dotTimer--;
			if (dotTimer == 0) {
				if (!board.ghostIsActive(Ghost.GHOST_INKY)) {
					board.activateGhost(Ghost.GHOST_INKY);
					dotTimer = TIME_DOTEATING;
				} else if (!board.ghostIsActive(Ghost.GHOST_CLYDE)) {
					board.activateGhost(Ghost.GHOST_CLYDE);
					dotTimer = TIME_DOTEATING;
				}
			}
		}
		if (frightenedTimer != 0) {
			frightenedTimer--;
			if (frightenedTimer == 0) {
				board.setModes(ghostMode);
			}
		} else {
			chaseScatterTimer--;
			if (chaseScatterTimer == 0) {
				ghostMode = (ghostMode == Ghost.MODE_CHASE) ? Ghost.MODE_SCATTER : Ghost.MODE_CHASE;
				board.setModes(ghostMode);
				chaseScatterTimer = (ghostMode == Ghost.MODE_CHASE) ? TIME_CHASE : TIME_SCATTER;
			}
		}
	}
	
	public Board getBoard() {
		return (Board) board.clone();
	}
	public PacmanScore getScore() {
		return (PacmanScore) score.clone();
	}
	public byte getStatus() {
		return this.gameStatus;
	}

	public String getModeString() {
		if (frightenedTimer > 0) {
			return "FRIGHT";
		} else {
			return (ghostMode == Ghost.MODE_CHASE) ? "CHASE" : "SCATTER"; 
		}
	}
}

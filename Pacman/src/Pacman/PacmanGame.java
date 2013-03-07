package Pacman;

import Pacman.Ghost.Mode;

public class PacmanGame {	
	public enum Dir {
		UP, RIGHT, DOWN, LEFT;
	}
	public enum Status {
		BUSY, GAME_OVER, GAME_END, TIMEOUT;
	}
	public enum Type {
	    DEFAULT, SQUARE, SIMPLE
	}
	
	public static final int GTPS = 44; //GameTicksPerSecond
	public static final int TIME_SCATTER       =  7 * GTPS,
							TIME_SCATTER_SMALL =  5 * GTPS,
							TIME_CHASE         = 20 * GTPS,
							TIME_FRIGHTENED    =  5 * GTPS,
							TIME_DOTEATING     =  4 * GTPS;
	public static final int CHASE_SCATTER_SWITCHES = 7;
	
	private Board board;
	private PacmanScore score;
	private Status gameStatus;
	private int maxGameticks;
	private int chaseScatterTimer, frightenedTimer, dotTimer;
	private int chaseScatterCounter;
	private Mode ghostMode;
	private Type gameType;
		
	public PacmanGame(int maxGameticks) { //Value <0 enables infinit gameticks
		this(maxGameticks, Type.DEFAULT);
	}
	public PacmanGame(int maxGameticks, Type type) {
		gameType		  = type;
		switch (gameType) {
			case SQUARE: board = new SquareBoard(); break;
			case SIMPLE: board = new SimpleMazeBoard(); break;
			default:     board = new Board();
		}
		score 			  = new PacmanScore();
		
		gameStatus		  = Status.BUSY;
		this.maxGameticks = maxGameticks;

		chaseScatterTimer = TIME_SCATTER;
		ghostMode = Ghost.Mode.SCATTER;
		board.setModes(ghostMode);
		dotTimer = TIME_DOTEATING;
		chaseScatterCounter = 0;
	}
	
	public void doMove(Dir direction) {
		if (gameStatus != Status.BUSY) {return;}
		board.doMove(direction);
		board.updateGhosts();
		PacmanScore stepscore = board.getScore();
		score.addScore(stepscore);
		
		if (stepscore.getEnergizers() > 0) {
			frightenedTimer = TIME_FRIGHTENED;
			board.setModes(Ghost.Mode.FRIGHTENED);
		}
		
		if (score.getDots() >= 30) {
			board.activateGhost(Ghost.INKY);
		}
		if (score.getDots() >= 90) {
			board.activateGhost(Ghost.CLYDE);
			dotTimer = 0;
		}
		
		if (stepscore.getDots() > 0 && dotTimer != 0) {
			dotTimer = TIME_DOTEATING;
		}
		
		if (maxGameticks > 0 && score.getGameticks() > maxGameticks) {
			gameStatus = Status.TIMEOUT;
		}
		
		if (score.getDeaths() > 0) 		   { gameStatus = Status.GAME_OVER; }
		if (board.getDotsRemaining() <= 0) { gameStatus = Status.GAME_END;  }
		
		updateTimers();
	}
	public void updateTimers() {
		if (dotTimer != 0) {
			dotTimer--;
			if (dotTimer == 0) {
				if (!board.ghostIsActive(Ghost.INKY)) {
					board.activateGhost(Ghost.INKY);
					dotTimer = TIME_DOTEATING;
				} else if (!board.ghostIsActive(Ghost.CLYDE)) {
					board.activateGhost(Ghost.CLYDE);
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
				ghostMode = (ghostMode == Ghost.Mode.CHASE) ? Ghost.Mode.SCATTER : Ghost.Mode.CHASE;
				board.setModes(ghostMode);
				chaseScatterCounter++;
				if (chaseScatterCounter < CHASE_SCATTER_SWITCHES) {
					chaseScatterTimer = (ghostMode == Ghost.Mode.CHASE) ? TIME_CHASE : (chaseScatterCounter < CHASE_SCATTER_SWITCHES/2) ? TIME_SCATTER : TIME_SCATTER_SMALL;
				}
			}
		}
	}
	
	public Board getBoard() {
		return (Board) board.clone();
	}
	public PacmanScore getScore() {
		return (PacmanScore) score.clone();
	}
	public Status getStatus() {
		return this.gameStatus;
	}

	public String getModeString() {
		if (frightenedTimer > 0) {
			return "FRIGHT";
		} else {
			return (ghostMode == Ghost.Mode.CHASE) ? "CHASE" : "SCATTER"; 
		}
	}
}

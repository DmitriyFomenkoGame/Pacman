package Pacman;

import java.awt.geom.Point2D;

public class Board implements Cloneable {

	public static final byte DOT_NONE 	   = 0,
							 DOT_DOT 	   = 1,
							 DOT_ENERGIZER = 2;
	public static final byte GHOST_NONE	   = 0,
							 GHOST_NORMAL  = 1,
							 GHOST_EDIBLE  = 2;
	public static final int  WIDTH         = 28,
							 HEIGHT        = 31;
	
	private Pacman pacman;
	private Ghost[] ghosts;
	private boolean locked;
	private boolean[][] wallgrid;
	private byte[][] dotgrid;
	private String boarddata = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW" +
							   "WDDDDDDDDDDDDWWDDDDDDDDDDDDW" +
							   "WDWWWWDWWWWWDWWDWWWWWDWWWWDW" +
							   "WEWWWWDWWWWWDWWDWWWWWDWWWWEW" +
							   "WDWWWWDWWWWWDWWDWWWWWDWWWWDW" +
							   "WDDDDDDDDDDDDDDDDDDDDDDDDDDW" +
							   "WDWWWWDWWDWWWWWWWWDWWDWWWWDW" +
							   "WDWWWWDWWDWWWWWWWWDWWDWWWWDW" +
							   "WDDDDDDWWDDDDWWDDDDWWDDDDDDW" +
							   "WWWWWWDWWWWW WW WWWWWDWWWWWW" +
							   "WWWWWWDWWWWW WW WWWWWDWWWWWW" +
							   "WWWWWWDWW    G     WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WWWWWWDWW W      W WWDWWWWWW" +
							   "      D   W G G GW   D      " +
							   "WWWWWWDWW W      W WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WWWWWWDWW          WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WDDDDDDDDDDDDWWDDDDDDDDDDDDW" +
							   "WDWWWWDWWWWWDWWDWWWWWDWWWWDW" +
							   "WDWWWWDWWWWWDWWDWWWWWDWWWWDW" +
							   "WEDDWWDDDDDDDPDDDDDDDDWWDDEW" +
							   "WWWDWWDWWDWWWWWWWWDWWDWWDWWW" +
							   "WWWDWWDWWDWWWWWWWWDWWDWWDWWW" +
							   "WDDDDDDWWDDDDWWDDDDWWDDDDDDW" +
							   "WDWWWWWWWWWWDWWDWWWWWWWWWWDW" +
							   "WDWWWWWWWWWWDWWDWWWWWWWWWWDW" +
							   "WDDDDDDDDDDDDDDDDDDDDDDDDDDW" +
							   "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";
	
	public Board() {
		pacman = new Pacman();
		ghosts = new Ghost[4];
		ghosts[Ghost.GHOST_BLINKY] = new Ghost(pacman, Ghost.GHOST_BLINKY);
		ghosts[Ghost.GHOST_PINKY]  = new Ghost(pacman, Ghost.GHOST_PINKY);
		ghosts[Ghost.GHOST_INKY]   = new Ghost(pacman, Ghost.GHOST_INKY);
		ghosts[Ghost.GHOST_CLYDE]  = new Ghost(pacman, Ghost.GHOST_CLYDE);
		locked = false;
		
		initGrids();
	}
	
	private void initGrids() {
		initWalls();
		initDots();
	}
	private void initWalls() {
		wallgrid = new boolean[WIDTH][HEIGHT];
		for(int j = 0; j < HEIGHT; j++) {
			for(int i = 0; i < WIDTH; i++) {
				wallgrid[i][j] = (boarddata.charAt(j * WIDTH + i) == 'W');
			}
		}
	}
	private void initDots() {
		dotgrid = new byte[WIDTH][HEIGHT];
		for(int j = 0; j < HEIGHT; j++) {
			for(int i = 0; i < WIDTH; i++) {
				char c = boarddata.charAt(j * WIDTH + i);
				dotgrid[i][j] = (c == 'D') ? DOT_DOT : ((c == 'E') ? DOT_ENERGIZER : DOT_NONE);
			}
		}		
	}

	public PacmanScore doMove(byte direction) {
		if (locked) {throw new Error("Board clones are readonly");}
		if (direction < PacmanGame.DIR_UP || direction > PacmanGame.DIR_LEFT) {
			throw new Error("Unknown direction for pacman. (" + String.valueOf(direction) + ")");
		}
		PacmanScore s = new PacmanScore();
		Point2D newpos = pacman.doMove(direction);
		//if newpos is @ wall bla bla
		//	pacman.undoMove();
		//else if newpos is @ dot etccccc
		//  ...
		//move ghosts!!!
		updateGhosts(); //Or before moving pacman? 
		return null;
	}
	
	public void bypassMove() {
		updateGhosts();
	}
	
	private void updateGhosts() {
		//foreach ghost, check if next position is intersection, ifso, calculate direction for that tile, ifnot, continue in same direction if not in corner
		/*for(int g = Ghost.GHOST_BLINKY; g <= Ghost.GHOST_CLYDE; g++) {
			ghosts[g].continueMove();
		}*/
		ghosts[Ghost.GHOST_BLINKY].continueMove();
	}
	
	public Point2D.Double getGhostPosition(int ghost) { //Positions are real numbers, not integers
		if (ghost < Ghost.GHOST_BLINKY || ghost > Ghost.GHOST_CLYDE) {
			throw new Error("Ghost identifier is unknown! (" + String.valueOf(ghost) + ")");
		}
		return ghosts[ghost].getPosition();
	}
	public Point2D.Double getBlinkyPosition() {
		return getGhostPosition(Ghost.GHOST_BLINKY);
	}
	public Point2D.Double getPinkyPosition() {
		return getGhostPosition(Ghost.GHOST_PINKY);
	}
	public Point2D.Double getInkyPosition() {
		return getGhostPosition(Ghost.GHOST_INKY);
	}
	public Point2D.Double getClydePosition() {
		return getGhostPosition(Ghost.GHOST_CLYDE);
	}
	
	public boolean[][] getWalls() {
		return wallgrid.clone();
	}
	
	public Object clone(){ //Clones are readonly
		try{
			Board cloned = (Board) super.clone();
			cloned.pacman = (Pacman) pacman.clone();
			cloned.ghosts = new Ghost[4];
			cloned.ghosts[Ghost.GHOST_BLINKY] = (Ghost) ghosts[Ghost.GHOST_BLINKY].clone();
			cloned.ghosts[Ghost.GHOST_PINKY]  = (Ghost) ghosts[Ghost.GHOST_PINKY].clone();
			cloned.ghosts[Ghost.GHOST_INKY]   = (Ghost) ghosts[Ghost.GHOST_INKY].clone();
			cloned.ghosts[Ghost.GHOST_CLYDE]  = (Ghost) ghosts[Ghost.GHOST_CLYDE].clone();
			cloned.locked = true;
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
}

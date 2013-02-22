package Pacman;

import java.awt.Point;
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
		ghosts[Ghost.GHOST_BLINKY].continueMove(this);
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
	
	public boolean isCorner(Point p) {
		switch (getDirections(p)) {
			case 3: case 6: case 9: case 12:
				return true;
		}
		return false;
	}
	public boolean isCorner(Point2D.Double p) {
		return isCorner(new Point((int) Math.round(p.x), (int) Math.round(p.y)));
	}
	public boolean isCrossing(Point p) {
		switch (getDirections(p)) {
			case 7: case 11: case 13: case 14: case 15: 
				return true;
		}
		return false;
	}
	public boolean isCrossing(Point2D.Double p) {
		return isCrossing(new Point((int) Math.round(p.x), (int) Math.round(p.y)));
	}
	public boolean isWall(Point p) {
		if (p.x >= 0 && p.x < WIDTH && p.y >= 0 && p.y < HEIGHT) {
			return wallgrid[p.x][p.y];
		}
		return false;
	}
	public int getDirections(Point p) {
		int directions = 0;
		directions = (isWall(new Point(p.x - 1, p.y))) ? (directions | 1) : (directions & ~1);
		directions <<= 1;
		directions = (isWall(new Point(p.x, p.y + 1))) ? (directions | 1) : (directions & ~1);
		directions <<= 1;
		directions = (isWall(new Point(p.x + 1, p.y))) ? (directions | 1) : (directions & ~1);
		directions <<= 1;
		directions = (isWall(new Point(p.x, p.y - 1))) ? (directions | 1) : (directions & ~1);
		return directions;
	}
	public Point getNextTile(Point p, int direction) {
		if (direction < PacmanGame.DIR_UP || direction > PacmanGame.DIR_LEFT) {
			throw new Error("Unknown direction. (" + String.valueOf(direction) + ")");
		}
		switch (direction) {
			case PacmanGame.DIR_UP:    return new Point(p.x, p.y - 1);
			case PacmanGame.DIR_RIGHT: return new Point(p.x + 1, p.y);
			case PacmanGame.DIR_DOWN:  return new Point(p.x, p.y + 1);
			case PacmanGame.DIR_LEFT:  return new Point(p.x - 1, p.y);		
		}
		return null;
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

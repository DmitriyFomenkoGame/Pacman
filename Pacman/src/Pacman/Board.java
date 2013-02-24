package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

import Pacman.Ghosts.Blinky;
import Pacman.Ghosts.Clyde;
import Pacman.Ghosts.Inky;
import Pacman.Ghosts.Pinky;

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
	private Random generator;
	private boolean locked;
	private boolean[][] wallgrid;
	private byte[][] dotgrid;
	private int dotsremaining;
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
							   "WWWWWWDWW          WWDWWWWWW" +
							   "WWWWWWDWW WWW WWWW WWDWWWWWW" +
							   "WWWWWWDWW WWW WWWW WWDWWWWWW" +
							   "      D   WWW WWWW   D      " +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WWWWWWDWW          WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WWWWWWDWW WWWWWWWW WWDWWWWWW" +
							   "WDDDDDDDDDDDDWWDDDDDDDDDDDDW" +
							   "WDWWWWDWWWWWDWWDWWWWWDWWWWDW" +
							   "WDWWWWDWWWWWDWWDWWWWWDWWWWDW" +
							   "WEDDWWDDDDDDDDDDDDDDDDWWDDEW" +
							   "WWWDWWDWWDWWWWWWWWDWWDWWDWWW" +
							   "WWWDWWDWWDWWWWWWWWDWWDWWDWWW" +
							   "WDDDDDDWWDDDDWWDDDDWWDDDDDDW" +
							   "WDWWWWWWWWWWDWWDWWWWWWWWWWDW" +
							   "WDWWWWWWWWWWDWWDWWWWWWWWWWDW" +
							   "WDDDDDDDDDDDDDDDDDDDDDDDDDDW" +
							   "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";
	
	public static final int DIRS_U    = 1, DIRS_R   = 2,  DIRS_D   = 4,  DIRS_L = 8,
							DIRS_UR   = 3, DIRS_RD  = 6,  DIRS_DL  = 12, DIRS_LU  = 9,
					  		DIRS_URD  = 7, DIRS_RDL = 14, DIRS_DLU = 13, DIRS_LUR = 11,
					  		DIRS_URDL = 15;
	
	public Board() {
		pacman = new Pacman();
		ghosts = new Ghost[4];
		ghosts[Ghost.GHOST_BLINKY] = new Blinky(this);
		ghosts[Ghost.GHOST_PINKY]  = new Pinky(this);
		ghosts[Ghost.GHOST_INKY]   = new Inky(this);
		ghosts[Ghost.GHOST_CLYDE]  = new Clyde(this);
		locked = false;
		
		generator = new Random(0);
		
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
		dotsremaining = 0;
		for(int j = 0; j < HEIGHT; j++) {
			for(int i = 0; i < WIDTH; i++) {
				char c = boarddata.charAt(j * WIDTH + i);
				dotgrid[i][j] = (c == 'D') ? DOT_DOT : ((c == 'E') ? DOT_ENERGIZER : DOT_NONE);
				dotsremaining += (c == 'D' || c == 'E') ? 1 : 0;
			}
		}		
	}

	public void doMove(byte direction) {
		if (locked) {throw new Error("Board clones are readonly");}
		if (direction < PacmanGame.DIR_UP || direction > PacmanGame.DIR_LEFT) {
			throw new Error("Unknown direction for pacman. (" + String.valueOf(direction) + ")");
		}
		pacman.doMove(this, direction);
	}
	public void updateGhosts() {
		for(int g = Ghost.GHOST_BLINKY; g <= Ghost.GHOST_CLYDE; g++) {
			ghosts[g].move();
		}
	}
	public void activateGhost(int g) {
		if (g < Ghost.GHOST_BLINKY || g > Ghost.GHOST_CLYDE) {
			throw new Error("Unknown ghost to activate (" + String.valueOf(g) + ")");
		}
		ghosts[g].activate();
	}
	public PacmanScore getScore() {
		Point newpos = pointToGrid(pacman.getPosition());
		PacmanScore s = new PacmanScore();
		if (newpos.x >= 0 && newpos.x < WIDTH) {
			if (dotgrid[newpos.x][newpos.y] == DOT_DOT) {
				s.addDot();
				dotsremaining -= 1;
			} else if (dotgrid[newpos.x][newpos.y] == DOT_ENERGIZER) {
				s.addEnergizer();
				dotsremaining -= 1;
			}
			dotgrid[newpos.x][newpos.y] = DOT_NONE;
			for(int g = Ghost.GHOST_BLINKY; g <= Ghost.GHOST_CLYDE; g++) {
				Ghost ghost = ghosts[g];
				Point gpos = pointToGrid(ghosts[g].getPosition());
				if (newpos.x == gpos.x && newpos.y == gpos.y) {
					byte gmode = ghost.getMode();
					if (gmode == Ghost.MODE_CHASE || gmode == Ghost.MODE_SCATTER) {
						s.addDeath();						
					} else if (gmode == Ghost.MODE_FRIGHTENED) {
						ghost.die();
					}
				}
			}
		}
		return s;
	}

	public void setMode(byte modeScatter, int ghost) {
		if (ghost < Ghost.GHOST_BLINKY || ghost > Ghost.GHOST_CLYDE) {
			throw new Error("Unknown ghost type (" + String.valueOf(ghost) + ")");
		}
		ghosts[ghost].setMode(modeScatter);
	}
	public void setModes(byte modeScatter) {
		for (int ghost = Ghost.GHOST_BLINKY; ghost <= Ghost.GHOST_CLYDE; ghost++) {
			ghosts[ghost].setMode(modeScatter);					
		}
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
	public Point2D.Double getPacmanPosition() {
		return pacman.getPosition();
	}
	public Point tilesAheadOfPacman(int tiles) {
		Point targetPos = Board.pointToGrid(pacman.getPosition());
		if (tiles == 0) {
			return targetPos;
		}
		byte dir = pacman.getDirection();
		switch(dir) {
			case PacmanGame.DIR_UP:    return new Point(targetPos.x - tiles, targetPos.y - tiles);
			case PacmanGame.DIR_RIGHT: return new Point(targetPos.x + tiles, targetPos.y - 0	);
			case PacmanGame.DIR_DOWN:  return new Point(targetPos.x - 0, 	 targetPos.y + tiles);
			default:  				   return new Point(targetPos.x - tiles, targetPos.y - 0	);
		}
	}
	
	public boolean isCorner(Point p) {
		switch (getDirections(p)) {
			case DIRS_UR: case DIRS_RD: case DIRS_DL: case DIRS_LU: 
				return true;
		}
		return false;
	}
	public boolean isCorner(Point2D.Double p) {
		return isCorner(pointToGrid(p));
	}
	public boolean isCrossing(Point p) {
		switch (getDirections(p)) {
			case DIRS_URD: case DIRS_RDL: case DIRS_DLU: case DIRS_LUR: case DIRS_URDL: 
				return true;
		}
		return false;
	}
	public boolean isCrossing(Point2D.Double p) {
		return isCrossing(pointToGrid(p));
	}
	public boolean isWall(Point p) {
		int xx = Math.max(0, Math.min(p.x, WIDTH - 1)),
			yy = Math.max(0, Math.min(p.y, HEIGHT - 1));
		return wallgrid[xx][yy];
	}
	public int getDirections(Point p) {
		int directions = 0;
		directions += (!isWall(new Point(p.x, p.y - 1))) ? DIRS_U : 0;
		directions += (!isWall(new Point(p.x + 1, p.y))) ? DIRS_R : 0;
		directions += (!isWall(new Point(p.x, p.y + 1))) ? DIRS_D : 0;
		directions += (!isWall(new Point(p.x - 1, p.y))) ? DIRS_L : 0;
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
	public Point getNextTile(Point2D.Double p, int direction) {
		return getNextTile(pointToGrid(p), direction);
	}
	
	public byte getCornerDir(Point p, byte direction) {
		//Assumes valid current direction was given...
		if (!isCorner(p)) {
			throw new Error("getCornerDir called on a non-corner");
		}
		switch (getDirections(p)) {
			case DIRS_UR: return (direction == PacmanGame.DIR_LEFT)  ? PacmanGame.DIR_UP   : PacmanGame.DIR_RIGHT;
			case DIRS_RD: return (direction == PacmanGame.DIR_LEFT)  ? PacmanGame.DIR_DOWN : PacmanGame.DIR_RIGHT;
			case DIRS_DL: return (direction == PacmanGame.DIR_RIGHT) ? PacmanGame.DIR_DOWN : PacmanGame.DIR_LEFT;
			case DIRS_LU: return (direction == PacmanGame.DIR_RIGHT) ? PacmanGame.DIR_UP   : PacmanGame.DIR_LEFT;
		}
		return direction;
	}
	public byte getCornerDir(Point2D.Double p, byte direction) {
		return getCornerDir(pointToGrid(p), direction);
	}
	public byte getCrossingDir(Point p, byte direction, Point t) {
		//Assumes valid current direction was given...
		if (!isCrossing(p)) {
			throw new Error("getCrossingDir called on non-crossing");
		}
		int dirs  = getDirections(p);
		int blockdir = (direction == PacmanGame.DIR_UP) ? DIRS_D : (direction == PacmanGame.DIR_RIGHT) ? DIRS_L : (direction == PacmanGame.DIR_DOWN) ? DIRS_U : DIRS_R; 
		if ((dirs & blockdir) != 0) {dirs -= blockdir;}
		if (p.y == 11 || (p.y == 23 && p.x > 9 && p.x < 19)) { //Moving up restriction
			if ((dirs & DIRS_U) != 0) {dirs -= DIRS_U;}
		}
		double bestdist = Double.MAX_VALUE;
		byte bestdir = direction;
		for (byte d = PacmanGame.DIR_UP; d <= PacmanGame.DIR_LEFT; d++) {
			if ((dirs & 1) != 0) {
				double dist = getNextTile(p, d).distance(t);
				if (dist < bestdist) {
					bestdist = dist;
					bestdir  = d;
				}
			}
			dirs >>= 1;			
		}
		return bestdir;
	}
	public byte getCrossingDir(Point2D.Double p, byte direction, Point target) {
		return getCrossingDir(pointToGrid(p), direction, target);
	}

	public boolean directionFree(Point2D.Double p, byte direction) {
		Point q = pointToGrid(p);
		if (q.x == 0 || q.x == WIDTH - 1) {
			if (direction == PacmanGame.DIR_LEFT || direction == PacmanGame.DIR_RIGHT) {
				return true;
			}else {
				return false;
			}
		}
		if(q.equals(new Point(13,11)) && direction == PacmanGame.DIR_DOWN){
			return false;
		}
		switch (direction) {
			case PacmanGame.DIR_UP:	   return !wallgrid[q.x][q.y - 1];
			case PacmanGame.DIR_RIGHT: return !wallgrid[q.x + 1][q.y];
			case PacmanGame.DIR_DOWN:  return !wallgrid[q.x][q.y + 1];
			case PacmanGame.DIR_LEFT:  return !wallgrid[q.x - 1][q.y];
		}
		return false;
	}
	public boolean directionFreeExclude(Point p, byte direction, byte excludeddirection) {
		if ((direction == PacmanGame.DIR_UP    && excludeddirection == PacmanGame.DIR_DOWN) ||
			(direction == PacmanGame.DIR_DOWN  && excludeddirection == PacmanGame.DIR_UP) ||
			(direction == PacmanGame.DIR_LEFT  && excludeddirection == PacmanGame.DIR_RIGHT) ||
			(direction == PacmanGame.DIR_RIGHT && excludeddirection == PacmanGame.DIR_LEFT)) {
			return false;
		}
		return directionFree(new Point2D.Double(p.x, p.y), direction);
	}
	public byte getRandomDir() {
		return (byte) generator.nextInt(4);
	}
	
	public static Point pointToGrid(Point2D.Double p) {
		return new Point((int) Math.round(p.x), (int) Math.round(p.y));
	}
	
	public boolean[][] getWalls() {
		return wallgrid.clone();
	}
	public byte[][] getDots() {
		return dotgrid.clone();
	}
	
	public int getDotsRemaining() {
		return dotsremaining;
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

package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

import Pacman.Ghosts.*;

import Pacman.PacmanGame.Dir;

public class Board implements Cloneable {
	public enum Dot {
		NONE, DOT, ENERGIZER
	}
/*	public static final byte DOT_NONE 	   = 0,
							 DOT_DOT 	   = 1,
							 DOT_ENERGIZER = 2;*/
	public static final int  WIDTH         = 28,
							 HEIGHT        = 31;
	
	private Pacman pacman;
	protected Ghost[] ghosts;
	private Random generator;
	private boolean locked;
	private boolean[][] wallgrid;
	private Dot[][] dotgrid;
	private int dotsremaining;
	protected String boarddata = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW" +
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
		ghosts[Ghost.BLINKY] = new Blinky(this);
		ghosts[Ghost.PINKY]  = new Pinky(this);
		ghosts[Ghost.INKY]   = new Inky(this);
		ghosts[Ghost.CLYDE]  = new Clyde(this);
		locked = false;
				
		generator = new Random(0);
		
		initGrids();
	}
	
	protected void initGrids() {
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
		dotgrid = new Dot[WIDTH][HEIGHT];
		dotsremaining = 0;
		for(int j = 0; j < HEIGHT; j++) {
			for(int i = 0; i < WIDTH; i++) {
				char c = boarddata.charAt(j * WIDTH + i);
				dotgrid[i][j] = (c == 'D') ? Dot.DOT : ((c == 'E') ? Dot.ENERGIZER : Dot.NONE);
				dotsremaining += (c == 'D' || c == 'E') ? 1 : 0;
			}
		}		
	}

	public void doMove(Dir direction) {
		if (locked) {throw new Error("Board clones are readonly");}
		pacman.doMove(this, direction);
	}
	public void updateGhosts() {
		for(int g = Ghost.BLINKY; g <= Ghost.CLYDE; g++) {
			ghosts[g].move();
		}
	}
	public void activateGhost(int g) {
		if (g < Ghost.BLINKY || g > Ghost.CLYDE) {
			throw new Error("Unknown ghost to activate (" + String.valueOf(g) + ")");
		}
		ghosts[g].activate();
	}
	public boolean ghostIsActive(int g) {
		if (g < Ghost.BLINKY || g > Ghost.CLYDE) {
			throw new Error("Unknown ghost to activate (" + String.valueOf(g) + ")");
		}
		return ghosts[g].isActive();
	}
	public boolean ghostIsEdible(int g) {
		if (g < Ghost.BLINKY || g > Ghost.CLYDE) {
			throw new Error("Unknown ghost to activate (" + String.valueOf(g) + ")");
		}
		return ghosts[g].isEdible();
	}
	public boolean ghostIsDead(int g) {
		if (g < Ghost.BLINKY || g > Ghost.CLYDE) {
			throw new Error("Unknown ghost to activate (" + String.valueOf(g) + ")");
		}
		return ghosts[g].isDead();	
	}
	public PacmanScore getScore() {
		Point newpos = pointToGrid(pacman.getPosition());
		PacmanScore s = new PacmanScore();
		if (newpos.x >= 0 && newpos.x < WIDTH) {
			if (dotgrid[newpos.x][newpos.y] == Dot.DOT) {
				s.addDot();
				dotsremaining -= 1;
			} else if (dotgrid[newpos.x][newpos.y] == Dot.ENERGIZER) {
				s.addEnergizer();
				dotsremaining -= 1;
			}
			dotgrid[newpos.x][newpos.y] = Dot.NONE;
			for(int g = Ghost.BLINKY; g <= Ghost.CLYDE; g++) {
				Ghost ghost = ghosts[g];
				Point gpos = pointToGrid(ghosts[g].getPosition());
				if (newpos.x == gpos.x && newpos.y == gpos.y) {
					Ghost.Mode gmode = ghost.getMode();
					if (gmode == Ghost.Mode.CHASE || gmode == Ghost.Mode.SCATTER) {
						s.addDeath();						
					} else if (gmode == Ghost.Mode.FRIGHTENED) {
						ghost.die();
					}
				}
			}
		}
		return s;
	}

	public void setMode(Ghost.Mode modeScatter, int ghost) {
		if (ghost < Ghost.BLINKY || ghost > Ghost.CLYDE) {
			throw new Error("Unknown ghost type (" + String.valueOf(ghost) + ")");
		}
		ghosts[ghost].setMode(modeScatter);
	}
	public void setModes(Ghost.Mode modeScatter) {
		for (int ghost = Ghost.BLINKY; ghost <= Ghost.CLYDE; ghost++) {
			ghosts[ghost].setMode(modeScatter);					
		}
	}
		
	public Point2D.Double getGhostPosition(int ghost) { //Positions are real numbers, not integers
		if (ghost < Ghost.BLINKY || ghost > Ghost.CLYDE) {
			throw new Error("Ghost identifier is unknown! (" + String.valueOf(ghost) + ")");
		}
		return ghosts[ghost].getPosition();
	}
	public Point2D.Double getBlinkyPosition() {
		return getGhostPosition(Ghost.BLINKY);
	}
	public Point2D.Double getPinkyPosition() {
		return getGhostPosition(Ghost.PINKY);
	}
	public Point2D.Double getInkyPosition() {
		return getGhostPosition(Ghost.INKY);
	}
	public Point2D.Double getClydePosition() {
		return getGhostPosition(Ghost.CLYDE);
	}
	public Point2D.Double getPacmanPosition() {
		return pacman.getPosition();
	}
	public Dir getBlinkyDirection() {
		return ghosts[Ghost.BLINKY].getDirection();
	}
	public Dir getInkyDirection() {
		return ghosts[Ghost.BLINKY].getDirection();
	}
	public Dir getPinkyDirection() {
		return ghosts[Ghost.BLINKY].getDirection();
	}
	public Dir getClydeDirection() {
		return ghosts[Ghost.BLINKY].getDirection();
	}
	public Dir getPacmanDirection() {
		return pacman.getDirection();
	}
	public Point tilesAheadOfPacman(int tiles) {
		Point targetPos = Board.pointToGrid(pacman.getPosition());
		if (tiles == 0) {
			return targetPos;
		}
		Dir dir = pacman.getDirection();
		switch (dir) {
			case UP:    return new Point(targetPos.x - tiles, targetPos.y - tiles);
			case RIGHT: return new Point(targetPos.x + tiles, targetPos.y - 0	);
			case DOWN:  return new Point(targetPos.x - 0, 	 targetPos.y + tiles);
			default:  	return new Point(targetPos.x - tiles, targetPos.y - 0	);
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
	public boolean isDot(Point p) {
		int xx = Math.max(0, Math.min(p.x, WIDTH - 1)),
			yy = Math.max(0, Math.min(p.y, HEIGHT - 1));
		return dotgrid[xx][yy] != Dot.NONE;		
	}
	public int getDirections(Point p) {
		int directions = 0;
		directions += (!isWall(new Point(p.x, p.y - 1))) ? DIRS_U : 0;
		directions += (!isWall(new Point(p.x + 1, p.y))) ? DIRS_R : 0;
		directions += (!isWall(new Point(p.x, p.y + 1))) ? DIRS_D : 0;
		directions += (!isWall(new Point(p.x - 1, p.y))) ? DIRS_L : 0;
		return directions;
	}
	public Point getNextTile(Point p, Dir direction) {
		switch (direction) {
			case UP:    return new Point(p.x, p.y - 1);
			case RIGHT: return new Point(p.x + 1, p.y);
			case DOWN:  return new Point(p.x, p.y + 1);
			case LEFT:  return new Point(p.x - 1, p.y);		
		}
		return null;
	}
	public Point getNextTile(Point2D.Double p, Dir direction) {
		return getNextTile(pointToGrid(p), direction);
	}
	
	public Dir getCornerDir(Point p, Dir direction) {
		//Assumes valid current direction was given...
		if (!isCorner(p)) {
			throw new Error("getCornerDir called on a non-corner");
		}
		switch (getDirections(p)) {
			case DIRS_UR: return (direction == Dir.LEFT)  ? Dir.UP   : Dir.RIGHT;
			case DIRS_RD: return (direction == Dir.LEFT)  ? Dir.DOWN : Dir.RIGHT;
			case DIRS_DL: return (direction == Dir.RIGHT) ? Dir.DOWN : Dir.LEFT;
			case DIRS_LU: return (direction == Dir.RIGHT) ? Dir.UP   : Dir.LEFT;
		}
		return direction;
	}
	public Dir getCornerDir(Point2D.Double p, Dir direction) {
		return getCornerDir(pointToGrid(p), direction);
	}
	public Dir getCrossingDir(Point p, Dir direction, Point t, Ghost.Mode mode, boolean noup) {
		//Assumes valid current direction was given...
		if (!isCrossing(p)) {
			throw new Error("getCrossingDir called on non-crossing");
		}
		int dirs  = getDirections(p);
		int blockdir = (direction == Dir.UP) ? DIRS_D : (direction == Dir.RIGHT) ? DIRS_L : (direction == Dir.DOWN) ? DIRS_U : DIRS_R; 
		if ((dirs & blockdir) != 0) {dirs -= blockdir;}
		if (noup && (dirs & DIRS_U) != 0) { dirs -= DIRS_U; }
		double bestdist = Double.MAX_VALUE;
		Dir bestdir = direction;
		for (Dir d : Dir.values()) {
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
	public Dir getCrossingDir(Point2D.Double p, Dir direction, Point target, Ghost.Mode mode, boolean noup) {
		return getCrossingDir(pointToGrid(p), direction, target, mode, noup);
	}

	public boolean directionFree(Point2D.Double p, Dir direction) {
		Point q = pointToGrid(p);
		if (q.x == 0 || q.x == WIDTH - 1) {
			if (direction == Dir.LEFT || direction == Dir.RIGHT) {
				return true;
			}else {
				return false;
			}
		}
		switch (direction) {
			case UP:	return !wallgrid[q.x][q.y - 1];
			case RIGHT: return !wallgrid[q.x + 1][q.y];
			case DOWN:  return !wallgrid[q.x][q.y + 1];
			case LEFT:  return !wallgrid[q.x - 1][q.y];
		}
		return false;
	}
	public boolean directionFreeExclude(Point p, Dir direction, Dir excludeddirection) {
		if ((direction == Dir.UP    && excludeddirection == Dir.DOWN) ||
			(direction == Dir.DOWN  && excludeddirection == Dir.UP) ||
			(direction == Dir.LEFT  && excludeddirection == Dir.RIGHT) ||
			(direction == Dir.RIGHT && excludeddirection == Dir.LEFT)) {
			return false;
		}
		return directionFree(new Point2D.Double(p.x, p.y), direction);
	}
	public Dir getRandomDir() {
		return Dir.values()[generator.nextInt(4)];
	}
	
	public static Point pointToGrid(Point2D.Double p) {
		return new Point((int) Math.round(p.x), (int) Math.round(p.y));
	}
	
	public boolean[][] getWalls() {
		return wallgrid.clone();
	}
	public Dot[][] getDots() {
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
			cloned.ghosts[Ghost.BLINKY] = (Ghost) ghosts[Ghost.BLINKY].clone();
			cloned.ghosts[Ghost.PINKY]  = (Ghost) ghosts[Ghost.PINKY].clone();
			cloned.ghosts[Ghost.INKY]   = (Ghost) ghosts[Ghost.INKY].clone();
			cloned.ghosts[Ghost.CLYDE]  = (Ghost) ghosts[Ghost.CLYDE].clone();
			cloned.locked = true;
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}


}

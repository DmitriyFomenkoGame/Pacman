package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Ghost implements Cloneable {

	public static final int GHOST_BLINKY 	= 0,
	GHOST_PINKY  	= 1,
	GHOST_INKY   	= 2,
	GHOST_CLYDE  	= 3;
	public static final int MODE_CHASE      = 0,
	MODE_SCATTER    = 1,
	MODE_FRIGHTENED = 2;

	public static double GHOST_SPEED = 0.25; //must fit equally in 1 

	private Pacman pacman;
	private int type;
	private int direction, nextdirection;
	private Point2D.Double position;
	private int mode;
	private Point target, nexttile, scattertarget;

	public Ghost(Pacman pacman, int type) {
		this.pacman    = pacman;
		this.type      = type;
		switch (type) {
			case GHOST_BLINKY: 
				position      = new Point2D.Double(13, 11);
				scattertarget = new Point(25, -4);
				this.direction = PacmanGame.DIR_LEFT;
			break;
			case GHOST_PINKY:
				position 	  = new Point2D.Double(13, 14);
				scattertarget = new Point(2, -4);
				this.direction = PacmanGame.DIR_UP;
			break;
			case GHOST_INKY:
				position 	  = new Point2D.Double(13, 14);
				scattertarget = new Point(27, 31);
				this.direction = PacmanGame.DIR_UP;
			break;
			case GHOST_CLYDE:
				position 	  = new Point2D.Double(13, 14);
				scattertarget = new Point(0, 31);
				this.direction = PacmanGame.DIR_UP;
			break;
		}
		this.nextdirection = direction;
		//		this.mode = MODE_CHASE;
		//TODO: BYPASS
		this.mode = MODE_SCATTER;
	}

	public void setMode(int mode) {
		if (mode < MODE_CHASE || mode > MODE_FRIGHTENED) {
			throw new Error("Unknown mode for ghost. (" + String.valueOf(mode) + ")");
		}
		this.mode = mode;
	}

	public Point2D.Double getPosition() {
		return (Point2D.Double) position.clone();
	}

	public void continueMove(Board b) {
		System.out.printf("%f %f\n", position.x, position.y);
		if (nexttile == null) {
			nexttile = b.getNextTile(position, direction);
			if (b.isCorner(nexttile)) {
				nextdirection = b.getCornerDir(nexttile, direction);
			}
			if (b.isCrossing(nexttile)) {
				nextdirection = b.getCrossingDir(nexttile, direction, scattertarget);
			}
		} else if (atTile(nexttile)) {
			if (position.x == nexttile.x && position.y == nexttile.y) {
				position.setLocation(nexttile.x, nexttile.y);
				direction = nextdirection;
				nexttile = b.getNextTile(position, direction);
				if (b.isCorner(nexttile)) {
					nextdirection = b.getCornerDir(nexttile, direction);
				}
				if (b.isCrossing(nexttile)) {
					nextdirection = b.getCrossingDir(nexttile, direction, scattertarget);
				}				
			}
		}

		double dx = 0, dy = 0;
		switch (direction) {
			case PacmanGame.DIR_UP:    dy = -GHOST_SPEED; break;
			case PacmanGame.DIR_RIGHT: dx =  GHOST_SPEED; break;
			case PacmanGame.DIR_DOWN:  dy =  GHOST_SPEED; break;
			case PacmanGame.DIR_LEFT:  dx = -GHOST_SPEED; break;
		}
		moveRelative(dx, dy);
	}
	private boolean atTile(Point t) {
		if (t.equals(Board.pointToGrid(position))) {
			return true;
		} else {
			return false;
		}
	}
	private void moveRelative(double dx, double dy) {
		position.setLocation(position.getX() + dx, position.getY() + dy);
	}

	public Object clone(){
		try{
			Ghost cloned = (Ghost) super.clone();
			cloned.pacman    = (Pacman) pacman.clone();
			cloned.type      = type;
			cloned.direction = direction;
			cloned.position  = (Point2D.Double) position.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
}

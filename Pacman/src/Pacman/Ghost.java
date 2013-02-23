package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Ghost implements Cloneable {
	
	public static final int GHOST_BLINKY = 0,
	GHOST_PINKY = 1,
	GHOST_INKY = 2,
	GHOST_CLYDE = 3;

	public static final int MODE_CHASE      = 0,
	MODE_SCATTER    = 1,
	MODE_FRIGHTENED = 2;

	public static double GHOST_SPEED = 0.20; //must fit equally in 1 

	protected Pacman pacman;
	protected int direction, nextdirection;
	protected Point2D.Double position;
	protected int mode;
	protected Point nexttile, scattertarget, currenttarget;

	public Ghost(Pacman pacman) {
		this.pacman    = pacman;
		this.nextdirection = direction;
		this.mode = MODE_CHASE;
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
		switch(mode){
			case MODE_SCATTER:    currenttarget = scattertarget; 			break;
			case MODE_CHASE: 	  currenttarget = new Point(chaseTarget(b)); break;
			case MODE_FRIGHTENED: currenttarget = scattertarget; 			break;
		}
		if (nexttile == null) {
			updateNextTile(b);
		} else if (Math.abs(position.x - nexttile.x) < 0.01 && Math.abs(position.y - nexttile.y) < 0.01) {
			position.setLocation(nexttile.x, nexttile.y);
			direction = nextdirection;
			if (position.x < 0) {
				position.setLocation(position.x + Board.WIDTH, position.y);
			} else if (position.x >= Board.WIDTH){
				position.setLocation(position.x - Board.WIDTH, position.y);
			}
			updateNextTile(b);
		}
		moveDirection(direction);
	}
	public void updateNextTile(Board b) {
		nexttile = b.getNextTile(position, direction);
		if (b.isCorner(nexttile)) {
			nextdirection = b.getCornerDir(nexttile, direction);
		}
		if (b.isCrossing(nexttile)) {
			nextdirection = b.getCrossingDir(nexttile, direction, currenttarget);
		}
	}
	
	protected Point tilesAheadOfPacman(int tiles) {
		Point targetPos = Board.pointToGrid(pacman.getPosition());
		if (tiles == 0) {
			return targetPos;
		}
		byte dir = pacman.getDirection();
		switch(dir) {
			case PacmanGame.DIR_UP:    return new Point(targetPos.x - tiles, targetPos.y - tiles);
			case PacmanGame.DIR_RIGHT: return new Point(targetPos.x + tiles, targetPos.y - 0	);
			case PacmanGame.DIR_DOWN:  return new Point(targetPos.x - 0, 	 targetPos.y + tiles);
			case PacmanGame.DIR_LEFT:  return new Point(targetPos.x - tiles, targetPos.y - 0	);
		}
		return currenttarget;
	}
	protected Point chaseTarget(Board b){//Blinky method OVERRIDE IN OTHER GHOST CLASSES PLEASE
		return tilesAheadOfPacman(0);
	}
	
	private void moveDirection(int direction){
		double dx = 0, dy = 0;
		switch (direction) {
			case PacmanGame.DIR_UP:    dy = -GHOST_SPEED; break;
			case PacmanGame.DIR_RIGHT: dx =  GHOST_SPEED; break;
			case PacmanGame.DIR_DOWN:  dy =  GHOST_SPEED; break;
			case PacmanGame.DIR_LEFT:  dx = -GHOST_SPEED; break;
		}
		moveRelative(dx, dy);
	}
	private void moveRelative(double dx, double dy) {
		position.setLocation(position.getX() + dx, position.getY() + dy);
	}

	public Object clone(){
		try{
			Ghost cloned = (Ghost) super.clone();
			cloned.pacman    = (Pacman) pacman.clone();
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

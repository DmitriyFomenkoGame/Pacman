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
		switch(mode){
			case MODE_SCATTER: currenttarget = scattertarget; break;
			case MODE_CHASE: currenttarget = new Point(chaseTarget(b)); break;
			case MODE_FRIGHTENED: currenttarget = scattertarget; break;
		}
		if (nexttile == null) {
			nexttile = b.getNextTile(position, direction);
			/*if (b.isCorner(nexttile)) {
				nextdirection = b.getCornerDir(nexttile, direction);
			}
			if (b.isCrossing(nexttile)) {
				nextdirection = b.getCrossingDir(nexttile, direction, scattertarget);
			}*/
		} else if (Math.abs(position.x - nexttile.x) < 0.01 && Math.abs(position.y - nexttile.y) < 0.01) {
			position.setLocation(nexttile.x, nexttile.y);
			direction = nextdirection;
			nexttile = b.getNextTile(position, direction);
			if (b.isCorner(nexttile)) {
				nextdirection = b.getCornerDir(nexttile, direction);
			}
			if (b.isCrossing(nexttile)) {
				nextdirection = b.getCrossingDir(nexttile, direction, currenttarget);
			}				
		}
		moveDirection(direction);
	}
	
	protected Point chaseTarget(Board b){//Blinky method OVERRIDE IN OTHER GHOST CLASSES PLEASE
		Point2D.Double targetPos = pacman.getPosition();
		return new Point((int)targetPos.getX(),(int)targetPos.getY());
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

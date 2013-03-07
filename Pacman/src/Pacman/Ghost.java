package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.PacmanGame.Dir;

public class Ghost implements Cloneable {
	public static final byte BLINKY = 0,
							 PINKY  = 1,
						     INKY   = 2,
							 CLYDE  = 3;

	public enum Mode {
		CHASE, SCATTER, FRIGHTENED, DEAD
	}

	public static final double SPEED_NORMAL     = 0.20,
							   SPEED_FRIGHTENED = 0.125,
							   SPEED_DEAD       = 0.50;

	protected Point2D.Double position;
	protected Dir direction;
	protected Point scattertarget;

	protected Board board;
	private Point nexttile, deathtarget, currenttarget;
	public Dir nextdirection;

	Mode mode;

	private Mode prevMode;
	protected boolean active;
	
	public Ghost(Board board) {
		this.board 		   = board;
		this.mode  	 	   = Mode.SCATTER;
		this.deathtarget   = new Point(13, 11);
		this.currenttarget = this.deathtarget;
		this.active        = false;
	}
	
	public void setPosition(Point2D.Double pos){
		position = pos;
	}

	public void setMode(Mode mode) {
		if (!active) {return;}
		if (this.mode == mode) {return;}

		roundPosition(); //Round the position to make sure it will stay on the grid with the new speed
		//It can happen that this will shift the instance to the nexttile on the grid, or to the previous tile
		
		if (this.mode != Mode.FRIGHTENED && this.mode != Mode.DEAD) {
			prevMode = this.mode;
		}
		if (this.mode == Mode.DEAD) {
			if (mode != Mode.FRIGHTENED) {
				prevMode = mode;
			}
			return; //If in dead mode, ignore everything but update the prevmode to change to that at revival
		} else {
			this.mode = mode; //If in other mode, change modes
		}
		
		if (prevMode == Mode.CHASE || prevMode == Mode.SCATTER) {
			//When in chase or scatter mode, they also need to reverse their direction when modes change
			if (!inGhosthouse() && !justLeftGhosthouse()) { //Not in ghosthouse, because changing direction there would be fatal TAMTAMTAMMMM
				reverseDirection(); //Reverses direction and nextdirection				
				boolean corner = board.isCorner(position), crossing = board.isCrossing(position);
				if ((corner || crossing) && !atGhosthouse(Board.pointToGrid(position))) {
					if (corner) {
						direction = board.getCornerDir(position, direction);
						nexttile = board.getNextTile(position, direction);
					} else { //Crossing
						direction = board.getCrossingDir(position, direction, calcTarget(), this.mode, inRedArea());
						nexttile  = board.getNextTile(position, direction);
					}
				}
				nextdirection = direction;
				updateNextTile();
			}
		}
	}
	public Mode getMode() {
		return mode;
	}
	
	private void roundPosition() {   
		Point p = Board.pointToGrid(position);
		position = new Point2D.Double(p.x, p.y);
	}
	private void reverseDirection() {
		switch (direction) {
			case UP:    direction = Dir.DOWN;  break;
			case RIGHT: direction = Dir.LEFT;  break;
			case DOWN:  direction = Dir.UP;    break;
			case LEFT:  direction = Dir.RIGHT; break;
		}
	}
	
	public Point2D.Double getPosition() {
		return (Point2D.Double) position.clone();
	}

	private Point calcTarget() {
		Point target = scattertarget;
		if (mode == Mode.DEAD) {
			target = deathtarget;
			if (position.distance(deathtarget) < 0.01) {
				mode = prevMode;
			}
		}
		if (mode == Mode.CHASE) {
			target = new Point(chaseTarget());
		}
		return target;
	}
	
	public void move() {
		if (!active) {return;}	
		currenttarget = calcTarget();
		if (nexttile == null) {
			updateNextTile();
		} else if (position.distance(nexttile) < 0.01) {
			position.setLocation(nexttile.x, nexttile.y);
			direction = nextdirection;
			checkPortals();
			updateNextTile();
		}
		
		moveDirection(direction);
	}
	public void die() {
		if (mode != Mode.FRIGHTENED) {
			throw new Error("Ghost killed when not in fright mode?");
		}
		setMode(Mode.DEAD);
	}
	public void activate() {
		this.active = true;
	}
	public boolean isActive() {
		return active;
	}
	public boolean isEdible() {
		return (mode == Mode.FRIGHTENED);
	}
	public boolean isDead() {
		return (mode == Mode.DEAD);
	}

	private void checkPortals() {
		if (position.x < 0) {
			position.setLocation(position.x + Board.WIDTH, position.y);
		} else if (position.x >= Board.WIDTH){
			position.setLocation(position.x - Board.WIDTH, position.y);
		}
	}
	private boolean inGhosthouse() {
		return (position.x == 13 && position.y > 11 && position.y <= 14);
	}
	private boolean atGhosthouse(Point p) {
		if (direction != Dir.UP && direction != Dir.DOWN) { 
			return p.equals(new Point(13, 11));
		} else {
			return false;
		}
	}
	private boolean justLeftGhosthouse() {
		if (direction == Dir.UP || direction == Dir.DOWN) {
			return position.equals(new Point(13, 11));
		} else {
			return false;
		}
	}
	private boolean inRedArea() {
		if (mode != Mode.FRIGHTENED && mode != Mode.DEAD) {
			Point p = Board.pointToGrid(position);
			return (p.y == 11 || (p.y == 23 && p.x > 9 && p.x < 19));
		}
		return false;
	}
	
	private void updateNextTile() {
		if (direction == null) {
			return;
		}
		nexttile = board.getNextTile(position, direction);
		if (board.isCorner(nexttile)) {
			nextdirection = board.getCornerDir(nexttile, direction);
		}
		if (board.isCrossing(nexttile) && !atGhosthouse(nexttile)) {
			if (mode == Mode.FRIGHTENED) {
				Dir randomdir = board.getRandomDir();
				while (!board.directionFreeExclude(nexttile, randomdir, direction)) {
					randomdir = board.getRandomDir();
				}
				nextdirection = randomdir;				
			} else {
				nextdirection = board.getCrossingDir(nexttile, direction, currenttarget, mode, inRedArea());
			}
		}
	}
	protected Point chaseTarget() {
		return board.tilesAheadOfPacman(0);
	}
	
	private void moveDirection(Dir direction){
		double dx = 0, dy = 0;
		double curSpeed = (mode == Mode.FRIGHTENED) ? SPEED_FRIGHTENED : (mode == Mode.DEAD) ? SPEED_DEAD : SPEED_NORMAL;
		switch (direction) {
			case UP:    dy = -curSpeed; break;
			case RIGHT: dx =  curSpeed; break;
			case DOWN:  dy =  curSpeed; break;
			case LEFT:  dx = -curSpeed; break;
		}
		moveRelative(dx, dy);
	}
	private void moveRelative(double dx, double dy) {
		position.setLocation(position.getX() + dx, position.getY() + dy);
	}
	
	public Object clone(){
		try{
			Ghost cloned = (Ghost) super.clone();
			cloned.position      = (Point2D.Double) position.clone();
			cloned.direction     = direction;
			cloned.scattertarget = (Point) scattertarget.clone();
			cloned.board         = board;
			cloned.nexttile      = nexttile;
			cloned.nextdirection = nextdirection;
			cloned.mode          = mode;
			cloned.prevMode      = prevMode;
			cloned.active 		 = active;
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}

}

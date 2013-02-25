package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Ghost implements Cloneable {
	
	public static final byte GHOST_BLINKY = 0,
							 GHOST_PINKY  = 1,
						     GHOST_INKY   = 2,
							 GHOST_CLYDE  = 3;

	public static final byte MODE_CHASE      = 0,
							 MODE_SCATTER    = 1,
							 MODE_FRIGHTENED = 2,
							 MODE_DEAD       = 3;

	public static final double SPEED_NORMAL     = 0.20,
							   SPEED_FRIGHTENED = 0.10,
							   SPEED_DEAD       = 0.50;

	protected Point2D.Double position;
	protected byte direction;
	protected Point scattertarget;

	protected Board board;
	private Point nexttile, deathtarget, currenttarget;
	private byte nextdirection;

	private byte mode, prevMode;
	protected boolean active;
	
	public Ghost(Board board) {
		this.board 		   = board;
		this.mode  	 	   = MODE_SCATTER;
		this.deathtarget   = new Point(13, 11);
		this.currenttarget = this.deathtarget;
		this.active        = false;
	}

	public void setMode(byte mode) {
		if (mode < MODE_CHASE || mode > MODE_DEAD) {
			throw new Error("Unknown mode for ghost. (" + String.valueOf(mode) + ")");
		}
		
		if (!active) {return;}
		if (this.mode == mode) {return;}

		roundPosition(); //Round the position to make sure it will stay on the grid with the new speed
		//It can happen that this will shift the instance to the nexttile on the grid, or to the previous tile
		
		prevMode = this.mode;
		if (this.mode == MODE_DEAD) {
			prevMode = mode;
			return; //If in dead mode, ignore everything but update the prevmode to change to that at revival
		} else {
			this.mode = mode; //If in other mode, change modes
		}
		
		if (prevMode == MODE_CHASE || prevMode == MODE_SCATTER) {
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
	public byte getMode() {
		return mode;
	}
	
	private void roundPosition() {   
		Point p = Board.pointToGrid(position);
		position = new Point2D.Double(p.x, p.y);
	}
	private void reverseDirection() {
		switch (direction) {
			case PacmanGame.DIR_UP:    direction = PacmanGame.DIR_DOWN;  break;
			case PacmanGame.DIR_RIGHT: direction = PacmanGame.DIR_LEFT;  break;
			case PacmanGame.DIR_DOWN:  direction = PacmanGame.DIR_UP;    break;
			case PacmanGame.DIR_LEFT:  direction = PacmanGame.DIR_RIGHT; break;
		}
	}
	
	public Point2D.Double getPosition() {
		return (Point2D.Double) position.clone();
	}

	private Point calcTarget() {
		Point target = scattertarget;
		if (mode == MODE_DEAD) {
			target = deathtarget;
			if (position.distance(deathtarget) < 0.01) {
				mode = prevMode;
			}
		}
		if (mode == MODE_CHASE) {
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
		if (mode != MODE_FRIGHTENED) {
			throw new Error("Ghost killed when not in fright mode?");
		}
		setMode(MODE_DEAD);
	}
	public void activate() {
		this.active = true;
	}
	public boolean isActive() {
		return active;
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
		if (direction != PacmanGame.DIR_UP && direction != PacmanGame.DIR_DOWN) { 
			return p.equals(new Point(13, 11));
		} else {
			return false;
		}
	}
	private boolean justLeftGhosthouse() {
		if (direction == PacmanGame.DIR_UP || direction == PacmanGame.DIR_DOWN) {
			return position.equals(new Point(13, 11));
		} else {
			return false;
		}
	}
	private boolean inRedArea() {
		if (mode != Ghost.MODE_FRIGHTENED && mode != Ghost.MODE_DEAD) {
			Point p = Board.pointToGrid(position);
			return (p.y == 11 || (p.y == 23 && p.x > 9 && p.x < 19));
		}
		return false;
	}
	
	private void updateNextTile() {
		nexttile = board.getNextTile(position, direction);
		if (board.isCorner(nexttile)) {
			nextdirection = board.getCornerDir(nexttile, direction);
		}
		if (board.isCrossing(nexttile) && !atGhosthouse(nexttile)) {
			if (mode == MODE_FRIGHTENED) {
				byte randomdir = board.getRandomDir();
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
	
	private void moveDirection(int direction){
		double dx = 0, dy = 0;
		double curSpeed = (mode == MODE_FRIGHTENED) ? SPEED_FRIGHTENED : (mode == MODE_DEAD) ? SPEED_DEAD : SPEED_NORMAL;
		switch (direction) {
			case PacmanGame.DIR_UP:    dy = -curSpeed; break;
			case PacmanGame.DIR_RIGHT: dx =  curSpeed; break;
			case PacmanGame.DIR_DOWN:  dy =  curSpeed; break;
			case PacmanGame.DIR_LEFT:  dx = -curSpeed; break;
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

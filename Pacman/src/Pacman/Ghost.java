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
	
	public Ghost(Board board) {
		this.board 		   = board;
		this.mode  	 	   = MODE_SCATTER;
		this.deathtarget   = new Point(13, 11);
		this.currenttarget = this.deathtarget;
	}

	public void setMode(byte mode) {
		if (mode < MODE_CHASE || mode > MODE_DEAD) {
			throw new Error("Unknown mode for ghost. (" + String.valueOf(mode) + ")");
		}
		if (this.mode == mode) {return;}

		//TODO: GLITCHES FIXEN
		if (this.mode == MODE_DEAD) {
			prevMode = mode;
			roundPosition();
//			updateNextTile();
			return;
		}
		
		if (mode == MODE_FRIGHTENED) {
			prevMode = this.mode;
		}
		this.mode = mode;
		
		roundPosition();
		if (mode != MODE_DEAD) {
			reverseDirection();
		} else {
			currenttarget = deathtarget;
			updateNextTile();
		}
	}
	public byte getMode() {
		return mode;
	}
	public void die() {
		if (mode != MODE_FRIGHTENED) {
			throw new Error("Ghost killed when not in fright mode?");
		}
		setMode(MODE_DEAD);
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
		nextdirection = direction;
		updateNextTile();
	}
	
	public Point2D.Double getPosition() {
		return (Point2D.Double) position.clone();
	}

	public void move() {
		currenttarget = scattertarget;
		if (mode == MODE_DEAD) {
			currenttarget = deathtarget;
			if (position.distance(deathtarget) < 0.01) {
				mode = prevMode;
			}
		}
		if (mode == MODE_CHASE) {
			currenttarget = new Point(chaseTarget());
		}
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
	private void checkPortals() {
		if (position.x < 0) {
			position.setLocation(position.x + Board.WIDTH, position.y);
		} else if (position.x >= Board.WIDTH){
			position.setLocation(position.x - Board.WIDTH, position.y);
		}
	}

	private void updateNextTile() {
		nexttile = board.getNextTile(position, direction);
		if (board.isCorner(nexttile)) {
			nextdirection = board.getCornerDir(nexttile, direction);
		}
		if (board.isCrossing(nexttile)) {
			if (mode == MODE_FRIGHTENED) {
				byte randomdir = board.getRandomDir();
				while (!board.directionFreeExclude(nexttile, randomdir, direction)) {
					randomdir = board.getRandomDir();
				}
				nextdirection = randomdir;				
			} else {
				nextdirection = board.getCrossingDir(nexttile, direction, currenttarget);
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
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
}

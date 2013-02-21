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

	private Pacman pacman;
	private int type;
	private int direction;
	private Point2D position;
	private int mode;
	
	public Ghost(Pacman pacman, int type) {
		this.pacman    = pacman;
		this.type      = type;
		this.direction = PacmanGame.DIR_UP;
		this.position  = new Point();
		switch (type) {
			case GHOST_BLINKY: position.setLocation(13, 11); break;
			case GHOST_PINKY:  position.setLocation(14, 14); break;
			case GHOST_INKY:   position.setLocation(12, 14); break;
			case GHOST_CLYDE:  position.setLocation(16, 14); break;
		}
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
	
	public Point2D getPosition() {
		return (Point2D) position.clone();
	}
	
	public Object clone(){
		try{
			Ghost cloned = (Ghost) super.clone();
			cloned.pacman    = (Pacman) pacman.clone();
			cloned.type      = type;
			cloned.direction = direction;
			cloned.position  = (Point2D) position.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
	
}

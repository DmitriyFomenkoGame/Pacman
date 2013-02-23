package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Pinky extends Ghost {

	public Pinky(Pacman pacman) {
		super(pacman);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(2, -4);
		this.direction = PacmanGame.DIR_UP;
		this.mode = MODE_CHASE;
	}
	
	protected Point chaseTarget(Board b){
		Point2D.Double targetPos = pacman.getPosition();
		byte dir = pacman.getDirection();
		switch(dir){
			case PacmanGame.DIR_UP:    return new Point((int)(targetPos.getX() - 4), (int)(targetPos.getY() - 4));
			case PacmanGame.DIR_RIGHT: return new Point((int)(targetPos.getX() + 4), (int)(targetPos.getY() - 0));
			case PacmanGame.DIR_DOWN:  return new Point((int)(targetPos.getX() - 0), (int)(targetPos.getY() + 4));
			case PacmanGame.DIR_LEFT:  return new Point((int)(targetPos.getX() - 4), (int)(targetPos.getY() - 0));
		}
		return scattertarget;
	}

}

package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Blinky extends Ghost {

	public Blinky(Pacman pacman) {
		super(pacman);
		position      = new Point2D.Double(13, 11);
		scattertarget = new Point(25, -4);
		this.direction = PacmanGame.DIR_LEFT;
		this.mode = Ghost.MODE_CHASE;
	}
	
	protected Point chaseTarget(Board b){//Blinky method OVERRIDE IN OTHER GHOST CLASSES PLEASE
		Point2D.Double targetPos = pacman.getPosition();
		return new Point((int)targetPos.getX(),(int)targetPos.getY());
	}

}

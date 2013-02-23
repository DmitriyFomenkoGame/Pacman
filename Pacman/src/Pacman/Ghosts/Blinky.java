package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.Pacman;
import Pacman.PacmanGame;

public class Blinky extends Ghost {

	public Blinky(Pacman pacman) {
		super(pacman);
		position      = new Point2D.Double(13, 11);
		scattertarget = new Point(25, -4);
		this.direction = PacmanGame.DIR_LEFT;
		this.mode = Ghost.MODE_CHASE;
	}
	
	protected Point chaseTarget(Board b){
		Point2D.Double targetPos = pacman.getPosition();
		return new Point((int)targetPos.getX(),(int)targetPos.getY());
	}

}

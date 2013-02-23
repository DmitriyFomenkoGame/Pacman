package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.Pacman;
import Pacman.PacmanGame;

public class Pinky extends Ghost {

	public Pinky(Pacman pacman) {
		super(pacman);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(2, -4);
		this.direction = PacmanGame.DIR_UP;
		this.active = true;
	}
	
	protected Point chaseTarget(Board b) {
		return tilesAheadOfPacman(4);
	}

}

package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Ghost;
import Pacman.Pacman;
import Pacman.PacmanGame;

public class Clyde extends Ghost {

	public Clyde(Pacman pacman) {
		super(pacman);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(0, 31);
		this.direction = PacmanGame.DIR_UP;
	}

}

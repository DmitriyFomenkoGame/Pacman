package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Ghost;
import Pacman.Pacman;
import Pacman.PacmanGame;

public class Inky extends Ghost {

	public Inky(Pacman pacman) {
		super(pacman);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(27, 31);
		this.direction = PacmanGame.DIR_UP;
	}

}

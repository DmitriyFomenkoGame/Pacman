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
	}
	
	protected Point chaseTarget(Board b){
		return tilesAheadOfPacman(0);
	}

}

package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.Pacman;
import Pacman.PacmanGame;

public class Clyde extends Ghost {

	public Clyde(Pacman pacman) {
		super(pacman);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(0, 31);
		this.direction = PacmanGame.DIR_UP;
		this.active = false;
	}
	
	protected Point chaseTarget(Board b){
		if (position.distance(pacman.getPosition()) <= 8) {
			return scattertarget;
		} else {
			return tilesAheadOfPacman(0);
		}		
	}


}

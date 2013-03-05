package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame;

public class Pinky extends Ghost {

	public Pinky(Board board, Point2D.Double pos) {
		super(board);
		position 	  = pos;
		scattertarget = new Point(2, -4);
		direction 	  = PacmanGame.DIR_UP;
		active 		  = true;
	}
	
	protected Point chaseTarget() {
		return board.tilesAheadOfPacman(4);
	}

}

package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame.Dir;

public class Blinky extends Ghost {

	public Blinky(Board board) {
		super(board);
		position      = new Point2D.Double(13, 11);
		scattertarget = new Point(25, -4);
		direction	  = nextdirection = Dir.LEFT;
		active 		  = true;
	}
	
	protected Point chaseTarget(){
		return board.tilesAheadOfPacman(0);
	}

}

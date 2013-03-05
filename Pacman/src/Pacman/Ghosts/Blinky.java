package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame;

public class Blinky extends Ghost {

	public Blinky(Board board, Point2D.Double pos) {
		super(board);
		position      = pos;
		scattertarget = new Point(25, -4);
		direction	  = PacmanGame.DIR_LEFT;
		active 		  = true;
	}
	
	protected Point chaseTarget(){
		return board.tilesAheadOfPacman(0);
	}

}

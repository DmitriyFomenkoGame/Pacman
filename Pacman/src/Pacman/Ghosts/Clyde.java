package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame;

public class Clyde extends Ghost {

	public Clyde(Board board, Point2D.Double pos) {
		super(board);
		position 	  = pos;
		scattertarget = new Point(0, 31);
		this.direction = PacmanGame.DIR_UP;
	}
	
	protected Point chaseTarget(){
		if (position.distance(board.getPacmanPosition()) <= 8) {
			return scattertarget;
		} else {
			return board.tilesAheadOfPacman(0);
		}		
	}


}

package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame;
import Pacman.TestGhost;

public class Clyde extends TestGhost {

	public Clyde(Board board) {
		super(board);
		position 	  = new Point2D.Double(13, 14);
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

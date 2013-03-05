package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame;
import Pacman.TestGhost;

public class Pinky extends TestGhost {

	public Pinky(Board board) {
		super(board);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(2, -4);
		direction 	  = PacmanGame.DIR_UP;
		active 		  = true;
	}
	
	protected Point chaseTarget() {
		return board.tilesAheadOfPacman(4);
	}

}

package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame;

public class Inky extends Ghost {

	public Inky(Board board, Point2D.Double pos) {
		super(board);
		position 	  = pos;
		scattertarget = new Point(27, 31);
		this.direction = PacmanGame.DIR_UP;
	}
	
	protected Point chaseTarget() {
		Point blinky = Board.pointToGrid(board.getBlinkyPosition());
		Point subtarget = board.tilesAheadOfPacman(2);
		int dx = subtarget.x - blinky.x,
			dy = subtarget.y - blinky.y;
		return new Point(blinky.x + dx * 2, blinky.y + dy * 2);
	}

}

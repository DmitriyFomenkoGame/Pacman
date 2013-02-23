package Pacman.Ghosts;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.Pacman;
import Pacman.PacmanGame;

public class Inky extends Ghost {

	public Inky(Pacman pacman) {
		super(pacman);
		position 	  = new Point2D.Double(13, 14);
		scattertarget = new Point(27, 31);
		this.direction = PacmanGame.DIR_UP;
		this.active = false;
	}
	
	protected Point chaseTarget(Board b) {
		Point blinky = Board.pointToGrid(b.getBlinkyPosition());
		Point subtarget = tilesAheadOfPacman(2);
		int dx = subtarget.x - blinky.x,
			dy = subtarget.y - blinky.y;
		return new Point(blinky.x + dx * 2, blinky.y + dy * 2);
	}

}

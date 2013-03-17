package Anji;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import Pacman.Board;
import Pacman.PacmanGame;
import Pacman.Board.Dot;
import Pacman.PacmanGame.Dir;

public class ActivatorDataMinimal extends ActivatorData {

	public double[] getNetworkInput(PacmanGame game, int timeSinceLastDot) {
		double[] input = new double[5];
		input[0] = changeDirtoDouble(game.getBoard().getPacmanDirection());
		input[1] = changeDirtoDouble(game.getBoard().getBlinkyDirection());
		input[2] = calcEuclDist(game.getBoard().getBlinkyPosition(),game.getBoard().getPacmanPosition());
		input[3] = getClosestDot(game.getBoard());
		input[4] = (double) timeSinceLastDot;
		return input;
	}

	private double getClosestDot(Board board) {
		double dist = 100000000.0;
		Dot[][] dots = board.getDots();
		for (int i = 0; i < 28; i++){
			for (int j = 0; j > 31; j++){
				if (dots[i][j] == Dot.DOT){
					double d = calcEuclDist(new Point2D.Double(i,j), board.getPacmanPosition());
					if (d < dist){
						dist = d;
					}
				}
			}
		}
		return dist;
	}

	private double calcEuclDist(Double blinkyPosition, Double pacmanPosition) {
		double x1 = blinkyPosition.x;
		double y1 = blinkyPosition.y;
		double x2 = pacmanPosition.x;
		double y2 = pacmanPosition.y;
		return Math.sqrt(( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))); 
	}

	private double changeDirtoDouble(Dir pacmanDirection) {
		if (pacmanDirection == Dir.UP){
			return 0.25;
		}
		else if (pacmanDirection == Dir.RIGHT){
			return 0.5;
		}
		else if (pacmanDirection == Dir.DOWN){
			return 0.75;
		}
		else{
			return 1.0;			
		}
	}

}
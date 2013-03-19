package Anji;

import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.LinkedList;
import java.util.Queue;

import Pacman.Board;
import Pacman.PacmanGame;
import Pacman.Board.Dot;
import Pacman.PacmanGame.Dir;

public class ActivatorDataMinimal extends ActivatorData {

	public double[] getNetworkInput(PacmanGame game, int timeSinceLastDot) {
		double[] input = new double[11];
		input[0] = changeDirtoDouble(game.getBoard().getPacmanDirection());
		input[1] = changeDirtoDouble(game.getBoard().getBlinkyDirection());
		input[2] = calcFloodDist(game.getBoard().getPacmanPosition(),game.getBoard().getBlinkyPosition(),game.getBoard());
		input[3] = getClosestDot(game.getBoard());
		input[4] = (double) timeSinceLastDot;
		input[5] = changeDirtoDouble(game.getBoard().getInkyDirection());
		input[6] = calcFloodDist(game.getBoard().getPacmanPosition(), game.getBoard().getInkyPosition(), game.getBoard());
		input[7] = changeDirtoDouble(game.getBoard().getPinkyDirection());
		input[8] = calcFloodDist(game.getBoard().getPacmanPosition(), game.getBoard().getPinkyPosition(), game.getBoard());
		input[9] = changeDirtoDouble(game.getBoard().getClydeDirection());
		input[10] = calcFloodDist(game.getBoard().getPacmanPosition(), game.getBoard().getClydePosition(), game.getBoard());
		return input;
	}

	private double getClosestDot(Board board) {
		double dist = 100000000.0;
		Dot[][] dots = board.getDots();
		for (int i = 0; i < 28; i++){
			for (int j = 0; j > 31; j++){
				if (dots[i][j] == Dot.DOT){
					double d = calcFloodDist(new Point2D.Double(i,j), board.getPacmanPosition(),board);
					if (d < dist){
						dist = d;
					}
				}
			}
		}
		return dist;
	}

	private double calcFloodDist(Point2D.Double from, Point2D.Double to, Board board) {
		Queue<Point> points = new LinkedList<Point>();
		Queue<Point> checked = new LinkedList<Point>();
		Point f = Board.pointToGrid(from);
		points.add(f);
		for (int i = 0; i < 60; i++) {
			Queue<Point> points2 = new LinkedList<Point>();
			while (!points.isEmpty()) {
				Point p = points.remove();
				if (p.equals(Board.pointToGrid(to))) {
					return i;
				}
				checked.add(p);
				Point2D.Double newpoint = new Point2D.Double(p.x, p.y);
				for (Dir d : Dir.values()) {
					if (newpoint.x < 0 || newpoint.x > 27){
						break;
					}
					if (board.directionFree(newpoint, d)) {
						Point q = board.getNextTile(p, d);
						if (!checked.contains(q)) {
							points2.add(q);
						}
					}
				}				
			}
			points.clear();
			points.addAll(points2);
		}
		return 60;
	}

	private double calcEuclDist(Double pos1, Double pos2) {
		double x1 = pos1.x;
		double y1 = pos1.y;
		double x2 = pos2.x;
		double y2 = pos2.y;
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
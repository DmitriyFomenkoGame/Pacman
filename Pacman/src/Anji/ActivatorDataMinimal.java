package Anji;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame.Dir;
import Pacman.PacmanScore;

public class ActivatorDataMinimal extends ActivatorData {
	
	public ActivatorDataMinimal() {
		super();
	}
	
	public double[] getData(Board board, PacmanScore score, Ghost.Mode mode, int maxgameticks) {
		ArrayList<Double> result = new ArrayList<Double>();
		
		//Pacman data
		addPosition(result, board.getPacmanPosition());
		addDirection(result, board.getPacmanDirection());
		//Blinky data
		
		addPosition(result, board.getBlinkyPosition());
		addDirection(result, board.getBlinkyDirection());
		
		//Pacman/blinky relation
		result.add((double) distanceToBlinkyFF(board, board.getPacmanPosition()));
		
		//Nearest dot data
		Point2D.Double dot = board.getPacmanPosition();
		int distToDot = distanceNearestDotFF(board, board.getPacmanPosition(), dot);
		addPosition(result, dot);

		//Pacman/dot relation
		result.add((double) distToDot);
		
		return ActivatorData.toArray(result);
	}	
	
	public static int distanceToBlinkyFF(Board b, Point2D.Double from) {
		Queue<Point> points = new LinkedList<Point>();
		Queue<Point> checked = new LinkedList<Point>();
		Point f = Board.pointToGrid(from);
		points.add(f);
		for (int i = 0; i < 60; i++) {
			Queue<Point> points2 = new LinkedList<Point>();
			while (!points.isEmpty()) {
				Point p = points.remove();
				if (p.equals(Board.pointToGrid(b.getBlinkyPosition()))) {
					return i;
				}
				checked.add(p);
				Point2D.Double newpoint = new Point2D.Double(p.x, p.y);
				for (Dir d : Dir.values()) {
					if (b.directionFree(newpoint, d)) {
						Point q = b.getNextTile(p, d);
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
	public static int distanceNearestDotFF(Board b, Point2D.Double from, Point2D.Double result) {
		Queue<Point> points = new LinkedList<Point>();
		Queue<Point> checked = new LinkedList<Point>();
		Point f = Board.pointToGrid(from);
		points.add(f);
		for (int i = 0; i < 60; i++) {
			Queue<Point> points2 = new LinkedList<Point>();
			while (!points.isEmpty()) {
				Point p = points.remove();
				if (b.isDot(p)) {
					result.setLocation(p.x, p.y);
					return i;
				}
				checked.add(p);
				Point2D.Double newpoint = new Point2D.Double(p.x, p.y);
				for (Dir d : Dir.values()) {
					if (b.directionFree(newpoint, d)) {
						Point q = b.getNextTile(p, d);
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
	
	private static double manhattanDist(Point p1, Point p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
	private static double manhattanDist(Point2D.Double p1, Point2D.Double p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
	
	private void addDistance(ArrayList<Double> list, Point2D.Double p1, Point2D.Double p2) {
		list.add(p1.distance(p2));
	}
	
	private void addPosition(ArrayList<Double> list, Point2D.Double p) {
		list.add(p.x);
		list.add(p.y);
	}
	private void addDirection(ArrayList<Double> list, Dir d) {
		list.add(directionToDouble(d));
	}
	private double directionToDouble(Dir d) {
		switch (d) {
			case UP:    return 0.0;
			case RIGHT: return 1.0;
			case DOWN:  return 2.0;
			default:    return 3.0;
		}
	}
}

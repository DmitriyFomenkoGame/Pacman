package Anji;

import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Pacman.Board;
import Pacman.PacmanGame;
import Pacman.Board.Dot;
import Pacman.PacmanGame.Dir;

public class ActivatorDataMinimal extends ActivatorData {

	public double[] getNetworkInput(PacmanGame game) {
		double[] input = new double[4];
		input[0] = changeDirtoDouble(game.getBoard().getPacmanDirection());
		input[1] = changeDirtoDouble(game.getBoard().getBlinkyDirection());
		input[2] = dijkstra(game.getBoard().getPacmanPosition(),game.getBoard().getBlinkyPosition(),game.getBoard());
		input[3] = getClosestDot(game.getBoard());
		return input;
	}

	private double getClosestDot(Board board) {
		double dist = 100000000.0;
		Dot[][] dots = board.getDots();
		for (int i = 0; i < 28; i++){
			for (int j = 0; j > 31; j++){
				if (dots[i][j] == Dot.DOT){
					double d = dijkstra(new Point2D.Double(i,j), board.getPacmanPosition(),board);
					if (d < dist){
						dist = d;
					}
				}
			}
		}
		return dist;
	}

	private double dijkstra(Point2D.Double from, Point2D.Double to, Board board){
		ArrayList<Node> nodes = new ArrayList<Node>();
		Dot[][] dots = board.getDots();
		for (int i =0; i < 28; i++){
			for (int j = 0; j < 31; j++){
				if (dots[i][j] == Dot.DOT){
					nodes.add(new Node(Board.pointToGrid(new Point2D.Double(i,j))));
				}
			}
		}
		Node current = new Node(Board.pointToGrid(from));
		for (int i = 0; i < nodes.size(); i++){
			if (nodes.get(i).equals(current)){
				nodes.get(i).distance = 0;
				break;
			}
		}
		while (!nodes.isEmpty()){
			current = getMinDistance(nodes);
			if (current.equals(new Node(Board.pointToGrid(from)))){
				break;
			}
			int index = nodes.indexOf(current);
			nodes.remove(index);
			ArrayList<Node> neighbours = getNeighbours(current, board);
			for (int i =0; i < neighbours.size(); i++){
				int distance = current.distance + 1;
				if (neighbours.get(i).distance > distance){
					neighbours.get(i).distance = distance;
					neighbours.get(i).previous = current;
				}
			}
		}
		ArrayList<Point> route = new ArrayList<Point>();
		while(current.previous != null){                                   
			route.add(0, current.p);                              
			current = current.previous;
		}
		return route.size();
	}
	
	private Node getMinDistance(ArrayList<Node> unvisited) {
		int distance = -1;
		int minI = -1;
		for (int i = 0; i < unvisited.size(); i++){
			if (unvisited.get(i).distance < distance || distance < 0){
				distance = unvisited.get(i).distance;
				minI = i;
			}
		}
		return unvisited.get(minI);
	}

	private ArrayList<Node> getNeighbours(Node current, Board b) {
		ArrayList<Node> neighbours = new ArrayList<Node>();
		Point2D.Double newpoint = new Point2D.Double(current.p.x, current.p.y);
		for (Dir d : Dir.values()) {
			if (b.directionFree(newpoint, d)) {
				neighbours.add(new Node((Board.pointToGrid(newpoint))));
			}
		}				
		return neighbours;
	}

	private class Node{
		int distance;
		Point p;
		Node previous;
		boolean visited;
		
		Node(Point point){
			p = point;
			distance = 10000000;
			previous = null;
			visited = false;
		}
		
		public boolean equals(Node n){
			if (p.equals(n.p)){
				return true;
			}
			return false;
		}
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
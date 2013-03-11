package Anji;

import java.awt.Point;
import java.util.ArrayList;

import Pacman.Board;
import Pacman.Board.Dot;
import Pacman.Ghost;
import Pacman.PacmanScore;

import com.anji.util.Properties;

public class ActivatorData {
	private int viewSize;
	
	public static final Double DATA_NONE       = 0.0,
							   DATA_WALL       = 1.0, 
							   DATA_PACMAN     = 2.0, 
							   DATA_DOT    	   = 3.0,
							   DATA_ENERGIZER  = 4.0,
							   DATA_GHOST  	   = 5.0;
	
	public void init(Properties properties) {
		viewSize = properties.getIntProperty("activator.data.representation.viewsize", -1); //Of er een viewsize nodig is, bij kleiner dan 0 zal het hele bord meegegeven worden aan het NN
	}

	public double[] getData(Board board, PacmanScore score, Ghost.Mode mode, int maxgameticks) {
		ArrayList<Double> result = new ArrayList<Double>();
		//LETOP er wordt geen rekening gehouden met de properties :3
		//TODO rekening houden met de properties :')
		
		Dot[][] dots 	  = board.getDots();
		boolean[][] walls = board.getWalls();
		Point pacmanPos   = Board.pointToGrid(board.getPacmanPosition());
		Point blinkyPos = Board.pointToGrid(board.getBlinkyPosition());
		
		if (viewSize < 0) {
			//Helebord
			for(int y = 5; y < 25; y++) {
				for(int x = 5; x < 23; x++) {
					if (dots[x][y] != Dot.NONE) {
						result.add(2.0);
					} else if (walls[x][y] == true) {
						result.add(1.0);
					} else if (pacmanPos.equals(new Point(x, y))) {
						result.add(3.0);
					} else {
						result.add(0.0);
					}
				}			
			}
		} else {
			//Deelbord
			for(int y = pacmanPos.y - viewSize; y <= pacmanPos.y + viewSize; y++) {
				for(int x = pacmanPos.x - viewSize; x <= pacmanPos.x + viewSize; x++) {
					if (x < 0 || x >= Board.WIDTH || y < 0 || y >= Board.HEIGHT){
						result.add(0.0);
					} else if (blinkyPos.equals(new Point(x, y))) {
						result.add(-1.0);
					} else if (dots[x][y] != Dot.NONE) {
						result.add(2.0);
					} else if (walls[x][y] == true) {
						result.add(1.0);
					} else if (pacmanPos.equals(new Point(x, y))) {
						result.add(0.5);
					} else {
						result.add(0.0);
					}
				}			
			}
		}
		
		return toArray(result);
	}
	
	public static double[] toArray(ArrayList<Double> l) { //Aangezien de native ArrayList.toArray nogal rot werkt met lowercase double eruit toveren..
		double[] result = new double[l.size()];
		for (int i = 0; i < l.size(); i++) {
			result[i] = l.get(i);
		}
		return result;
	}
	
	
}

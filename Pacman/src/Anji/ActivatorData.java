package Anji;

import java.awt.Point;
import java.util.ArrayList;

import Pacman.Board;
import Pacman.Board.Dot;
import Pacman.Ghost;
import Pacman.PacmanGame.Dir;
import Pacman.PacmanScore;

import com.anji.util.Properties;

public class ActivatorData {

	private boolean showPacman, showBlinky, showPinky, showInky, showClyde, showWalls, showDots, showEnergizers;
	private boolean showGameticks, showScore, showMode;
	private boolean showPacmandir, showGhostsdir;
	
	private boolean isSingleGrid;
	private int viewSize;
	
	public static final Double DATA_NONE       = 0.0,
							   DATA_WALL       = 1.0, 
							   DATA_PACMAN     = 2.0, 
							   DATA_DOT    	   = 3.0,
							   DATA_ENERGIZER  = 4.0,
							   DATA_GHOST  	   = 5.0;
	public static final Double DATA_CHASE      = 0.0,
							   DATA_SCATTER    = 1.0,
							   DATA_FRIGHTENED = 2.0;
	
	public void init(Properties properties) {
		showPacman = properties.getBooleanProperty("activator.data.pacman.show", true); //Of pacman weergegeven moeten worden aan het NN
		
		showBlinky = properties.getBooleanProperty("activator.data.blinky.show", true); //Of de ghosts weergegeven moeten worden aan het NN
		showPinky  = properties.getBooleanProperty("activator.data.pinky.show",  true);
		showInky   = properties.getBooleanProperty("activator.data.inky.show",   true);
		showClyde  = properties.getBooleanProperty("activator.data.clyde.show",  true);
		
		showWalls  	   = properties.getBooleanProperty("activator.data.walls.show", 	 true); //Of de muren weergegeven moeten worden aan het NN
		showDots  	   = properties.getBooleanProperty("activator.data.dots.show", 	     true); //Of dots weergegeven moeten worden aan het NN
		showEnergizers = properties.getBooleanProperty("activator.data.energizers.show", true); //Of de energizers weergegeven moeten worden aan het NN
 		//*Misschien een optie maken om energizers weer te geven als dots, wanneer je niet caret
		
		showGameticks = properties.getBooleanProperty("activator.data.gameticks.show", false); //Of de gameticks die nog over zijn weergegeven moeten worden aan het NN
		showScore     = properties.getBooleanProperty("activator.data.score.show",     false); //Of de score die behaald is weergegeven moet worden aan het NN
		showMode      = properties.getBooleanProperty("activator.data.mode.show",      false); //Of de huidige modus van de spoken weergegeven moet worden aan het NN, bij chase/scatter is het makkelijk doordat dat voor iedereen geldt. Bij dead mode kun je gewoon totaal negeren, maar fright is per ghost uniek, dus dat aangeven is nog best kut :)
		
		//TODO dirs implementeren
		showPacmandir = properties.getBooleanProperty("activator.data.pacman.dir.show", false); //Of er iets toegevoegd moet worden aan de data zodat het duidelijk is wat de huidige richting is van pacman, dus of hij überhaupt wel van richting wil veranderen, aangezien dat evolutionair slecht zou moeten uitvallen doordat je dan loopt waar je al gelopen hebt. Tenzij je anders natuurlijk opgegeten wordt enzo..
		showGhostsdir = properties.getBooleanProperty("activator.data.ghosts.dir.show", false); //Of de direction van de ghosts weergegeven moet worden aan het NN, want dan weet je of je moet ontwijken of niet. Ik zat te denken aan voor of achter de ghost een ander type identifier te plaatsen (0.5 als je een ghost grid hebt en iets anders leuks wanneer je een massive grid hebt)
		
		isSingleGrid  = properties.getBooleanProperty("activator.data.representation.singlegrid", true); //Of alles in één grid als het ware gegooid moet worden, dus niet enkel binaire input, maar meer*
		viewSize      = properties.getIntProperty("activator.data.representation.viewsize", -1); //Of er een viewsize nodig is, bij kleiner dan 0 zal het hele bord meegegeven worden aan het NN
		// * TODO uitzoeken of de double array genormaliseerd moet worden of niet...
	}

	public double[] getData(Board board, PacmanScore score, Ghost.Mode mode, int maxgameticks) {
		ArrayList<Double> result = new ArrayList<Double>();
		
		//LETOP er wordt geen rekening gehouden met de properties :3
		//TODO rekening houden met de properties :')
		
		Dot[][] dots 	  = board.getDots();
		boolean[][] walls = board.getWalls();
		Point pacmanPos   = Board.pointToGrid(board.getPacmanPosition());
		Point blinkyPos = Board.pointToGrid(board.getBlinkyPosition());
		
		/*
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
		}*/
		
		
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
		
		
		
		/*if (showGameticks) {
			result.add((double) (maxgameticks - score.getGameticks()));
		}
		if (showScore) {
			//Hmm, lastig om gelijk te implementeren met wat de fitnessfunction terug gaat geven, dus nu alleen even de dots
			//board.getDotsRemaining(); gebruiken om te normaliseren
			result.add((double) (score.getDots()));
		}
		if (showMode) {
			switch (mode) {
				case SCATTER: result.add(DATA_SCATTER);
				case CHASE:   result.add(DATA_CHASE);
				//case FRIGHTENED: result.add(DATA_FRIGHT);
			}
		}
		
		//TODO dirs toevoegen
		Dot[][] dots 	  = board.getDots();
		boolean[][] walls = board.getWalls();
		Point blinkyPos = Board.pointToGrid(board.getBlinkyPosition());
	  //Dir   blinkyDir = 
		Point pinkyPos  = Board.pointToGrid(board.getPinkyPosition());
	  //Dir   pinkyDir  = 
		Point inkyPos   = Board.pointToGrid(board.getInkyPosition());
	  //Dir   inkyDir   = 
		Point clydePos  = Board.pointToGrid(board.getClydePosition());
	  //Dir   clydeDir  = 
		Point pacmanPos = Board.pointToGrid(board.getPacmanPosition());
	  //Dir   pacmanDir = board.getPacmanDirection();
		
		//Hoop dat dit correct is :3
		int ymin = (viewSize >= 0) ? Math.max(0, pacmanPos.y - viewSize) : 0,
		    ymax = (viewSize >= 0) ? Math.min(pacmanPos.y + viewSize, dots[0].length - 1) : dots[0].length - 1;
		int xmin = (viewSize >= 0) ? Math.max(0, pacmanPos.x - viewSize) : 0,
		    xmax = (viewSize >= 0) ? Math.min(pacmanPos.x + viewSize, dots.length    - 1) : dots.length    - 1;
		
		if (isSingleGrid) {
			for (int y = ymin; y <= ymax; y++) {
				for (int x = xmin; x <= xmax; x++) {
					Point p = new Point(x, y);
					if (showPacman && pacmanPos.equals(p)) {
						result.add(DATA_PACMAN);
						continue;
					}
					if (showBlinky && blinkyPos.equals(p)) {
						if (!board.ghostIsDead(Ghost.BLINKY)) {
							result.add(DATA_GHOST);
							continue;
						}
					}
					if (showPinky && pinkyPos.equals(p)) {
						if (!board.ghostIsDead(Ghost.PINKY)) {
							result.add(DATA_GHOST);
							continue;
						}
					}
					if (showInky && inkyPos.equals(p)) {
						if (!board.ghostIsDead(Ghost.INKY)) {
							result.add(DATA_GHOST);
							continue;
						}
					}
					if (showClyde && clydePos.equals(p)) {
						if (!board.ghostIsDead(Ghost.CLYDE)) {
							result.add(DATA_GHOST);
							continue;
						}
					}
					if (showDots) {
						if (dots[x][y] == Dot.DOT) {
							result.add(DATA_DOT);
							continue;
						} else if (dots[x][y] == Dot.ENERGIZER) {
							result.add(DATA_ENERGIZER);
							continue;
						}
					}
					if (showWalls && walls[x][y]) {
						result.add(DATA_WALL);
						continue;
					}
				}				
			}
		} else {
			for (int y = ymin; y <= ymax; y++) {
				for (int x = xmin; x <= xmax; x++) {
					if (showWalls) {
						result.add((walls[x][y] ? 1.0 : 0.0));
					}
					if (showDots) {
						result.add((dots[x][y] == Dot.DOT ? 1.0 : 0.0));
					}
					if (showEnergizers) {
						result.add((dots[x][y] == Dot.ENERGIZER ? 1.0 : 0.0));
					}
					if (showPacman || showBlinky || showPinky || showInky || showClyde) {
						Point p = new Point(x, y);
						if (p.equals(pacmanPos)) { //Muhmuhmuh, anders krijg je helemaal bagger veel inputs
							result.add(0.5);
						} else if (p.equals(blinkyPos) || p.equals(pinkyPos) || p.equals(inkyPos) || p.equals(clydePos)) {
							result.add(1.0);
						} else {
							result.add(0.0);
						}
					}
				}
			}
		}*/
		
		return toArray(result);
	}
	
	private double[] toArray(ArrayList<Double> l) { //Aangezien de native ArrayList.toArray nogal rot werkt met lowercase double eruit toveren..
		double[] result = new double[l.size()];
		for (int i = 0; i < l.size(); i++) {
			result[i] = l.get(i);
		}
		return result;
	}
	
	
}

package Anji;

import com.anji.util.Properties;

public class ActivatorData {

	private boolean showPacman, showBlinky, showPinky, showInky, showClyde, showWalls, showDots, showEnergizers;
	private boolean showGameticks, showScore, showMode;
	private boolean showPacmandir, showGhostsdir;
	
	private boolean isSingleGrid;
	private int viewSize;
	
	public void init(Properties properties) {
		showPacman = properties.getBooleanProperty("activator.data.pacman.show", true);
		
		showBlinky = properties.getBooleanProperty("activator.data.blinky.show", true);
		showPinky  = properties.getBooleanProperty("activator.data.pinky.show",  true);
		showInky   = properties.getBooleanProperty("activator.data.inky.show",   true);
		showClyde  = properties.getBooleanProperty("activator.data.clyde.show",  true);
		
		showWalls  	   = properties.getBooleanProperty("activator.data.walls.show", 	 true);
		showDots  	   = properties.getBooleanProperty("activator.data.dots.show", 	     true);
		showEnergizers = properties.getBooleanProperty("activator.data.energizers.show", true);
 		
		showGameticks = properties.getBooleanProperty("activator.data.gameticks.show", false);
		showScore     = properties.getBooleanProperty("activator.data.score.show",     false);
		showMode      = properties.getBooleanProperty("activator.data.mode.show",      false);
		
		showPacmandir = properties.getBooleanProperty("activator.data.pacman.dir.show", false);
		showGhostsdir = properties.getBooleanProperty("activator.data.ghosts.dir.show", false);
		
		isSingleGrid  = properties.getBooleanProperty("activator.data.representation.singlegrid", true);
		viewSize      = properties.getIntProperty("activator.data.representation.viewsize", -1);
	}

}

package Anji;

import com.anji.util.Properties;

public class ActivatorData {

	private boolean showPacman, showBlinky, showPinky, showInky, showClyde, showWalls, showDots, showEnergizers;
	private boolean showGameticks, showScore, showMode;
	private boolean showPacmandir, showGhostsdir;
	
	private boolean isSingleGrid;
	private int viewSize;
	
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
		
		showPacmandir = properties.getBooleanProperty("activator.data.pacman.dir.show", false); //Of er iets toegevoegd moet worden aan de data zodat het duidelijk is wat de huidige richting is van pacman, dus of hij überhaupt wel van richting wil veranderen, aangezien dat evolutionair slecht zou moeten uitvallen doordat je dan loopt waar je al gelopen hebt. Tenzij je anders natuurlijk opgegeten wordt enzo..
		showGhostsdir = properties.getBooleanProperty("activator.data.ghosts.dir.show", false); //Of de direction van de ghosts weergegeven moet worden aan het NN, want dan weet je of je moet ontwijken of niet. Ik zat te denken aan voor of achter de ghost een ander type identifier te plaatsen (0.5 als je een ghost grid hebt en iets anders leuks wanneer je een massive grid hebt)
		
		isSingleGrid  = properties.getBooleanProperty("activator.data.representation.singlegrid", true); //Of alles in één grid als het ware gegooid moet worden, dus niet enkel binaire input, maar meer*
		viewSize      = properties.getIntProperty("activator.data.representation.viewsize", -1); //Of er een viewsize nodig is, bij kleiner dan 0 zal het hele bord meegegeven worden aan het NN
		// * TODO uitzoeken of de double array genormaliseerd moet worden of niet...
	}

}

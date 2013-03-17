package Anji;

import java.awt.geom.Point2D.Double;

import org.jgap.Chromosome;

import com.anji.integration.Activator;
import com.anji.integration.AnjiActivator;
import com.anji.integration.AnjiNetTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.nn.AnjiNet;
import com.anji.util.Properties;

import GameUI.PacmanGUI;
import Pacman.Board;
import Pacman.PacmanGame;
import Pacman.PacmanGame.Type;
import Pacman.PacmanScore;
import Pacman.PacmanGame.Dir;
import Pacman.PacmanGame.Status;

public class PacmanWorkerThread extends Thread {

	private Chromosome chromosome;
	private int maxFitness;
	private int maxGameTicks;
	private int dotScore, energizerScore, ghostScore, timePenalty;
	//private ActivatorTranscriber factory;
	private ActivatorDataMinimal activatorData;
	private Type gameType;
	private int expectedStimuli;
	private boolean showGUI;
	private int guiTimeout;
	private int recurrentCycles;
	private int currentDots = 0;
	private int timeSinceLastDot;
	private PacmanFitnessScore fitness;

	public PacmanWorkerThread(Chromosome c, boolean showGUI) {
		super();
		chromosome = c;
		this.showGUI = showGUI;
	}

	public void init(Properties properties) {
		//factory = (ActivatorTranscriber) properties.singletonObjectProperty(ActivatorTranscriber.class);
		recurrentCycles = properties.getIntProperty("recurrent.cycles", 1);
		maxFitness 	   = properties.getIntProperty("fitness.max");
		maxGameTicks   = properties.getIntProperty("game.gameticks", -1);
		dotScore 	   = properties.getIntProperty("game.score.dot", 10);
		energizerScore = properties.getIntProperty("game.score.energizer", 50);
		ghostScore 	   = properties.getIntProperty("game.score.ghost", 100);
		timePenalty    = properties.getIntProperty("game.score.time.penalty", 1);
		expectedStimuli= properties.getIntProperty("stimulus.size");
		showGUI    = properties.getBooleanProperty("game.gui.show", false) || showGUI;
		guiTimeout = properties.getIntProperty("game.gui.timeout", 50);
		String gameTypeString = properties.getProperty("game.type", "default").toLowerCase();
		if (gameTypeString.equals("square")) {
			gameType = Type.SQUARE;
		} else if (gameTypeString.equals("simple")) {
			gameType = Type.SIMPLE;
		} else {
			gameType = Type.DEFAULT;
		}
		activatorData  = new ActivatorDataMinimal();
		fitness = new PacmanFitnessScore();
		fitness.init(properties);
		timeSinceLastDot = 0;
	}

	public void run() {
		PacmanGUI gui = showGUI ? (new PacmanGUI()) : null;
		try {
			AnjiNetTranscriber transcriber = new AnjiNetTranscriber();
			AnjiNet net = transcriber.newAnjiNet(chromosome);
			Activator activator = new AnjiActivator(net, recurrentCycles);
			PacmanGame game = new PacmanGame(maxGameTicks, gameType);
			int fitness = playGame(game, activator, gui);
			if (fitness > maxFitness) {
				chromosome.setFitnessValue(maxFitness);
			} else {
				chromosome.setFitnessValue((int) fitness);
			}
		} catch (TranscriberException e) {
			e.printStackTrace();
		} finally {
			if (gui != null) {
				gui.close();
			}
		}
	}

	private int playGame(PacmanGame game, Activator activator, PacmanGUI gui) {
		if (gui != null) {
			gui.setBoard(game.getBoard());
			gui.show();
		}
		int fitness = 0;
		while (game.getStatus() == Status.BUSY) {
			double[] networkInput = activatorData.getNetworkInput(game,timeSinceLastDot);
			if (networkInput.length != expectedStimuli) {
				System.out.printf("Andere lengte van de array :/ %d (expected: %d)\n", networkInput.length, expectedStimuli);
				System.exit(1);
			}
			double[] networkOutput = activator.next(networkInput);
			Dir direction = getDirection(networkOutput);
			game.doMove(direction);
			if (gui != null) {
				gui.setBoard(game.getBoard());
				gui.setTitle(String.valueOf(game.getScore().getGameticks()) + "/" + String.valueOf(game.getMaxGameticks()));
				gui.redraw();
			}
			try {
				if (gui != null) {
					Thread.sleep(guiTimeout);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		fitness = updateFitness(game.getScore(),game, fitness);
		}
		if (game.getStatus() == Status.GAME_OVER){
			fitness -= 10*game.getScore().getGameticks();
		}
		if (fitness < 0){
			fitness = 0;
		}
		return fitness;
	}
	
	private int updateFitness(PacmanScore score, PacmanGame game, int fitness){
		if (currentDots == score.getDots()){
			timeSinceLastDot += 1;
			if (timeSinceLastDot > 15){
				fitness -= 1;
			}
		}
		else{
			timeSinceLastDot = 0;
			currentDots += 1;
			fitness += dotScore;
		}
		return fitness;
	}

	private Dir getDirection(double[] networkOutput) {
		double d = networkOutput[0];
		if (d <= 0.25){
			return PacmanGame.Dir.UP;
		}
		else if (d <= 0.5){
			return PacmanGame.Dir.RIGHT;
		}
		else if (d <= 0.75){
			return PacmanGame.Dir.DOWN;
		}
		else{
			return PacmanGame.Dir.LEFT;
		}
	}

}
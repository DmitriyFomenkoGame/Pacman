package Anji;

import org.jgap.Chromosome;

import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.util.Properties;

import GameUI.PacmanGUI;
import GameUI.PacmanGUIArrows;
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
	private ActivatorTranscriber factory;
	private ActivatorData activatorData;
	private Type gameType;
	private int expectedStimuli;

	public PacmanWorkerThread(Chromosome c) {
		super();
		chromosome = c;
		//System.out.println("[THREAD] Thread created");
	}

	public void init(Properties properties) {
		factory = (ActivatorTranscriber) properties.singletonObjectProperty(ActivatorTranscriber.class);
		maxFitness 	   = properties.getIntProperty("fitness.max");
		maxGameTicks   = properties.getIntProperty("game.gameticks", -1);
		
		dotScore 	   = properties.getIntProperty("game.score.dot", 10);
		energizerScore = properties.getIntProperty("game.score.energizer", 50);
		ghostScore 	   = properties.getIntProperty("game.score.ghost", 100);
		timePenalty    = properties.getIntProperty("game.score.time.penalty", 1);
		
		expectedStimuli= properties.getIntProperty("stimulus.size");
		
		String gameTypeString = properties.getProperty("game.type", "default").toLowerCase();
		if (gameTypeString.equals("square")) {
			gameType = Type.SQUARE;
		} else if (gameTypeString.equals("simple")) {
			gameType = Type.SIMPLE;
		} else {
			gameType = Type.DEFAULT;
		}
		
		activatorData  = new ActivatorData();
		activatorData.init(properties);
		//System.out.println("[THREAD] Thread init");
	}

	public void giveWork(Chromosome c) {
		chromosome = c;
	}

	public void run() {
		//System.out.println("[THREAD] Thread started");
		//PacmanGUI gui = new PacmanGUI();
		PacmanGUI gui = null;
		try {
			Activator activator = factory.newActivator(chromosome);
			PacmanGame game = new PacmanGame(maxGameTicks, gameType);
			double fitness = playGame(game, activator, gui);
			System.out.printf("[THREAD] Fitness %.2f\n", fitness);
			if (fitness > maxFitness) {
				chromosome.setFitnessValue(maxFitness);
			} else {
				chromosome.setFitnessValue((int) fitness);
			}
		} catch (TranscriberException e) {
			e.printStackTrace();
		} finally {
			//gui.close();
		}
		//System.out.println("[THREAD] Thread done");
	}

	private double playGame(PacmanGame game, Activator activator, PacmanGUI gui) {
		//gui.setBoard(game.getBoard());
		//gui.show();
		while (game.getStatus() == Status.BUSY) {
			double[] networkInput = activatorData.getData(game.getBoard(), game.getScore(), game.getMode(), game.getMaxGameticks());
			if (networkInput.length != expectedStimuli) {
				System.out.printf("Andere lengte van de array :/ %d (expected: %d)\n", networkInput.length, expectedStimuli);
				System.exit(1);
			}
			double[] networkOutput = activator.next(networkInput);
			Dir direction = getDirection(networkOutput, game.getBoard());
			game.doMove(direction);
			//gui.setBoard(game.getBoard());
			//gui.setTitle(String.valueOf(game.getScore().getGameticks()) + "/" + String.valueOf(game.getMaxGameticks()));
			//gui.redraw();
			try {
				//Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return generateFitness(game.getScore());
	}

	private double generateFitness(PacmanScore score) {
		double fitness = 0;
		fitness += dotScore * score.getDots();
		fitness += energizerScore * score.getEnergizers();
		fitness += ghostScore * score.getGhosts();
		fitness -= timePenalty * score.getGameticks();
		return fitness;
	}

	private Dir getDirection(double[] networkOutput, Board b) {
		//System.out.printf("%.2f %.2f %.2f %.2f\n", networkOutput[0], networkOutput[1], networkOutput[2], networkOutput[3]);
		Dir d = b.getPacmanDirection();
		double dirval = 0.0;
		if (b.directionFree(b.getPacmanPosition(), Dir.UP) && networkOutput[0] > dirval) {
			d = Dir.UP;
			dirval = networkOutput[0];
		}
		if (b.directionFree(b.getPacmanPosition(), Dir.RIGHT) && networkOutput[1] > dirval) {
			d = Dir.RIGHT;
			dirval = networkOutput[1];
		}
		if (b.directionFree(b.getPacmanPosition(), Dir.DOWN) && networkOutput[2] > dirval) {
			d = Dir.DOWN;
			dirval = networkOutput[2];
		}
		if (b.directionFree(b.getPacmanPosition(), Dir.LEFT) && networkOutput[3] > dirval) {
			d = Dir.LEFT;
			dirval = networkOutput[3];
		}
		return d;
	}

}
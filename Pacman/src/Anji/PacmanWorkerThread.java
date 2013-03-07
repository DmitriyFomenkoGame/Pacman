package Anji;

import org.jgap.Chromosome;

import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.util.Properties;

import Pacman.PacmanGame;
import Pacman.PacmanScore;
import Pacman.PacmanGame.Dir;
import Pacman.PacmanGame.Status;

public class PacmanWorkerThread extends Thread {

	private Chromosome chromosome;
	private int maxFitness;
	private int maxGameTicks;
	private int dotScore, energizerScore, ghostScore, timePenalty;
	private ActivatorTranscriber factory;

	public PacmanWorkerThread(Chromosome c) {
		super();
		chromosome = c;
	}

	public void init(Properties properties) {
		factory = (ActivatorTranscriber) properties.singletonObjectProperty(ActivatorTranscriber.class);
		maxFitness 	   = properties.getIntProperty("fitness.max");
		maxGameTicks   = properties.getIntProperty("game.gameticks", -1);
		
		dotScore 	   = properties.getIntProperty("game.score.dot", 10);
		energizerScore = properties.getIntProperty("game.score.energizer", 50);
		ghostScore 	   = properties.getIntProperty("game.score.ghost", 100);
		timePenalty    = properties.getIntProperty("game.score.time.penalty", 1);
	}

	public void giveWork(Chromosome c) {
		chromosome = c;
	}

	public void run() {
		try {
			Activator activator = factory.newActivator(chromosome);
			PacmanGame game = new PacmanGame(maxGameTicks);
			double fitness = playGame(game, activator);
			if (fitness > maxFitness) {
				chromosome.setFitnessValue(maxFitness);
			} else {
				chromosome.setFitnessValue((int) fitness);
			}
		} catch (TranscriberException e) {
			e.printStackTrace();
		}
	}

	private double playGame(PacmanGame game, Activator activator) {
		while (game.getStatus() == Status.BUSY) {
			double[] networkInput = new double[100]; // dit is een random
														// tijdelijk nummer
			getNetworkInput(networkInput);
			double[] networkOutput = activator.next(networkInput);
			Dir direction = getDirection(networkOutput);
			game.doMove(direction);
		}
		return generateFitness(game.getScore());
	}

	private void getNetworkInput(double[] networkInput) {
		// TODO Auto-generated method stub

	}

	private double generateFitness(PacmanScore score) {
		double fitness = 0;
		fitness += dotScore * score.getDots();
		fitness += energizerScore * score.getEnergizers();
		fitness += ghostScore * score.getGhosts();
		fitness -= timePenalty * score.getGameticks();
		return fitness;
	}

	private Dir getDirection(double[] networkOutput) {
		return Dir.UP;
	}

}
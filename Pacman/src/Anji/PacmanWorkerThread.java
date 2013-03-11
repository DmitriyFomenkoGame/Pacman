package Anji;

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
	private int gameOverScore, gameEndScore, gameTimeoutScore;
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
		
		gameOverScore    = properties.getIntProperty("game.score.game.over", -100);
		gameEndScore     = properties.getIntProperty("game.score.game.end", 1000);
		gameTimeoutScore = properties.getIntProperty("game.score.game.timeout", 500);
		
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
		activatorData.init(properties);
		
		fitness = new PacmanFitnessScore();
		fitness.init(properties);
	}

	public void giveWork(Chromosome c) {
		chromosome = c;
	}

	public void run() {
		PacmanGUI gui = showGUI ? (new PacmanGUI()) : null;

		try {
			//long start = System.currentTimeMillis();
			AnjiNetTranscriber transcriber = new AnjiNetTranscriber();
			AnjiNet net = transcriber.newAnjiNet(chromosome);
			Activator activator = new AnjiActivator(net, recurrentCycles);
			//long end = System.currentTimeMillis();
			//System.out.printf("Created activator in %dms\n", end - start);
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
		while (game.getStatus() == Status.BUSY) {
			double[] networkInput = activatorData.getData(game.getBoard(), game.getScore(), game.getMode(), game.getMaxGameticks());
			if (networkInput.length != expectedStimuli) {
				System.out.printf("Andere lengte van de array :/ %d (expected: %d)\n", networkInput.length, expectedStimuli);
				System.exit(1);
			}
			double[] networkOutput = activator.next(networkInput);
			Dir direction = getDirection(networkOutput, game.getBoard());
			game.doMove(direction);
			fitness.addGameState(game.getScore(), game.getBoard().getPacmanDirection());
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
		}
//		return generateFitness(game.getScore(), game.getStatus());
		return fitness.getFitness();
	}

	private int generateFitness(PacmanScore score, Status gamestatus) {
		int fitness = 0;
		fitness += dotScore * score.getDots();
		fitness += energizerScore * score.getEnergizers();
		fitness += ghostScore * score.getGhosts();
		fitness -= timePenalty * score.getGameticks();
		switch (gamestatus) {
			case GAME_OVER: fitness += gameOverScore;    break;
			case GAME_END:  fitness += gameEndScore;     break;
			case TIMEOUT:   fitness += gameTimeoutScore; break;
		}
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
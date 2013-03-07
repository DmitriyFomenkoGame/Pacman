package Anji;


import org.jgap.Chromosome;

import com.anji.integration.Activator;
import com.anji.util.Configurable;
import com.anji.util.Properties;

import Pacman.PacmanGame;
import Pacman.PacmanScore;

public class PacmanWorkerThread extends Thread implements Configurable {

	private Chromosome chromosome;
	private int maxFitness;
	private int maxGameTicks;
	private int DotScore, EnergizerScore, GhostScore, TimePenalty;
	
	public PacmanWorkerThread(Chromosome c){
		super();
		chromosome = c;
	}
	
	public void init(Properties properties) throws Exception{
		maxFitness = properties.getIntProperty("MaxFitness");
		maxGameTicks = properties.getIntProperty("MaxGameTicks", -1);
		DotScore = properties.getIntProperty("DotScore", 10);
		EnergizerScore = properties.getIntProperty("EnergizerScore", 50);
		GhostScore = properties.getIntProperty("GhostScore", 100);
		TimePenalty = properties.getIntProperty("TimePenalty", 1);
	}

	public void giveWork(Chromosome c) {
		chromosome = c;	
	}
	
	public void run(){
		Activator activator = getActivator();
		PacmanGame game = new PacmanGame(maxGameTicks);
		double fitness = playGame(game,activator);
		if (fitness > maxFitness){
			chromosome.setFitnessValue(maxFitness);
		}
		else{
			chromosome.setFitnessValue(fitness);
		}
	}

	private double playGame(PacmanGame game, Activator activator) {
		while (game.getStatus() == PacmanGame.GAME_BUSY){
			double[] networkInput = new double[100]; //dit is een random tijdelijk nummer 
			getNetworkInput(networkInput);
			double[] networkOutput = activator.next(networkInput);
			byte direction = getDirection(networkOutput);
			game.doMove(direction);
		}
		return generateFitness(game.getScore());
	}

	private void getNetworkInput(double[] networkInput) {
		// TODO Auto-generated method stub
		
	}

	private double generateFitness(PacmanScore score) {
		double fitness = 0;
		fitness += DotScore * score.getDots();
		fitness += EnergizerScore * score.getEnergizers();
		fitness += GhostScore * score.getGhosts();
		fitness -= TimePenalty * score.getGameticks();
		return fitness;
	}

	private byte getDirection(double[] networkOutput) {
		return 0;	
	}

	private Activator getActivator() {
		// hier moet factory stuff komen en dat komt weer uit de properties.
		return null;
	}
	
}
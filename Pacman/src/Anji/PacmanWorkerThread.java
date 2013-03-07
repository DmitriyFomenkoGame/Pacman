package Anji;


import org.jgap.Chromosome;

import com.anji.integration.Activator;

import Pacman.PacmanGame;

public class PacmanWorkerThread extends Thread {

	private Chromosome chromosome;
	private int maxFitness;
	
	public PacmanWorkerThread(Chromosome c, int maxFitnessValue){
		super();
		chromosome = c;
		maxFitness = maxFitnessValue;
	}

	public void giveWork(Chromosome c) {
		chromosome = c;	
	}
	
	public void run(){
		Activator activator = getActivator();
		PacmanGame game = new PacmanGame(-1); //de max gametick horen NIET -1 te zijn, maar die komen via de properties binnen
		double fitness = playGame(game,activator);
		if (fitness > maxFitness){
			chromosome.setFitnessValue(maxFitness);
		}
		else{
			chromosome.setFitnessValue(fitness);
		}
	}

	private double playGame(PacmanGame game, Activator activator) {
		double[] networkInput = new double[100]; //dit is een random tijdelijk nummer 
		double[] networkOutput = activator.next(networkInput);
		return 0;
	}

	private Activator getActivator() {
		// hier moet factory stuff komen en dat komt weer uit de properties.
		return null;
	}
	
}
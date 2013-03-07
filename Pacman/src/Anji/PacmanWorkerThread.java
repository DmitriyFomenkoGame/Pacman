package Anji;

import java.rmi.activation.Activator;

import org.jgap.Chromosome;

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
		Activator a = getActivator();
		PacmanGame game = new PacmanGame(-1); //de max gametick horen NIET -1 te zijn, maar die komen via de properties binnen
		double fitness = playGame(game,a);
		if (fitness > maxFitness){
			chromosome.setFitnessValue(maxFitness);
		}
		else{
			chromosome.setFitnessValue(fitness);
		}
	}

	private double playGame(PacmanGame game, Activator a) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Activator getActivator() {
		return null;
	}
	
}
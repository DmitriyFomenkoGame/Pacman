package Anji;

import org.jgap.Chromosome;

public class PacmanWorkerThread extends Thread {

	Chromosome chromosome;
	
	public PacmanWorkerThread(Chromosome c){
		chromosome = c;
	}

	public void giveWork(Chromosome c) {
		chromosome = c;	
	}
	
	public void run(){
		
	}
	
}
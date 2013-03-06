package Anji;

import org.jgap.Chromosome;

public class PacmanWorkerThread extends Thread {

	Chromosome[] chromosomesToTest;
	
	public PacmanWorkerThread(Chromosome[] items){
		chromosomesToTest = items;
		System.out.println("Thread: " + chromosomesToTest.length);
	}
	
}
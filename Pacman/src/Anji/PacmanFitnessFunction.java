package Anji;

import java.util.List;
import org.jgap.Chromosome;

import com.anji.util.Configurable;
import com.anji.util.Properties;

public class PacmanFitnessFunction implements BulkFitnessFunction, Configurable {

	private int numberOfThreads;
	private int maxFitness;
	private Properties properties;

	public PacmanFitnessFunction() {

	}

	@Override
	public void init(Properties properties) throws Exception {
		try{
			numberOfThreads = properties.getIntProperty("NumberOfThreads", 4);
			maxFitness = properties.getIntProperty("MaxFitness");
			this.properties = properties;
		}catch(Exception e){
			throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString() + ": "
					+ e.getMessage() );
		}
	}

	@Override
	public void evaluate(List<Chromosome> subjects) {
		int i = 0;
		if (subjects.size() < numberOfThreads) {
			numberOfThreads = subjects.size();
		}
		PacmanWorkerThread[] threads = new PacmanWorkerThread[numberOfThreads];
		for (int j = 0; j < numberOfThreads; j++) {
			threads[j] = new PacmanWorkerThread(subjects.get(j));
			// Hier moet nog de init van the PacmanWorkerThread. 
			// Maar krijg het er niet mooi in zonder een try/catch. 
			// Kan iemand even kijken?
			threads[j].start();
		}
		i = numberOfThreads;
		while (i < subjects.size()) {
			for (int j = 0; j < numberOfThreads; j++) {
				if (!(threads[i].isAlive())) {
					threads[i].giveWork(subjects.get(i));
					threads[i].start();
					i++;
				}
			}
		}
		for (int j = 0; j < numberOfThreads; j++) {
			try {
				threads[j].join();
			} catch (InterruptedException e) {
				throw new Error("A thread got interrupted");
			}
		}
	}

	/*
	 * oude versie public void evaluate(List subjects) { Chromosome[]
	 * chromosomes = (Chromosome[]) subjects.toArray(); int alreadyDivided = 0;
	 * Thread[] threads = new Thread[numberOfThreads]; for (int i = 0; i <
	 * threads.length; i++){ ArrayList<Chromosome> division = new
	 * ArrayList<Chromosome>(); for (int k = alreadyDivided; k <
	 * ((chromosomes.length/numberOfThreads) + alreadyDivided) ; k++){
	 * division.add(chromosomes[k]); } alreadyDivided += division.size(); if (i
	 * == (numberOfThreads -1) && alreadyDivided < chromosomes.length){ for (int
	 * j = alreadyDivided; j < chromosomes.length; j++){
	 * division.add(chromosomes[j]); } } threads[i] = new
	 * PacmanWorkerThread((Chromosome[]) division.toArray()); } //todo:
	 * Thread.join for loop }
	 */

	public int getMaxFitnessValue() {
		return maxFitness;
	}

}

package Anji;

import java.util.List;
import org.jgap.Chromosome;

public class PacmanFitnessFunction implements BulkFitnessFunction {

	private int numberOfThreads;
	private int maxFitness;
	
	public PacmanFitnessFunction(int maxFitnessValue){
		setNumberOfThreads(4);
		maxFitness = maxFitnessValue;
	}
	
	public void setNumberOfThreads(int i){
		if (i < 1){
			throw new Error("The minimum number of threads is 1");
		}
		numberOfThreads = i;
	}

	public void evaluate(List subjects) {
		int i = 0;
		PacmanWorkerThread[] threads = new PacmanWorkerThread[numberOfThreads];
		if (subjects.size() < numberOfThreads){
			numberOfThreads = subjects.size();
		}
		for (int j = 0; j < numberOfThreads; j++){
			threads[j] = new PacmanWorkerThread((Chromosome)subjects.get(j),maxFitness); // weet niet of de cast nodig is
			threads[j].start();
		}
		i = numberOfThreads;
		while (i < subjects.size()){
			for (int j = 0; j < numberOfThreads; j++){
				if (!(threads[i].isAlive())){
					threads[i].giveWork((Chromosome)subjects.get(i));
					threads[i].start();
					i++;
				}
			}
		}
		for (int j = 0; j < numberOfThreads; j++){
			try {
				threads[j].join();
			} catch (InterruptedException e) {
				throw new Error("A thread got interrupted");
			}
		}
	}
	
	/* oude versie
	 public void evaluate(List subjects) {
		Chromosome[] chromosomes = (Chromosome[]) subjects.toArray();
		int alreadyDivided = 0;
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < threads.length; i++){
			ArrayList<Chromosome> division = new ArrayList<Chromosome>();
			for (int k = alreadyDivided; k < ((chromosomes.length/numberOfThreads) + alreadyDivided) ; k++){
				division.add(chromosomes[k]);
			}
			alreadyDivided += division.size();
			if (i == (numberOfThreads -1) && alreadyDivided < chromosomes.length){
				for (int j = alreadyDivided; j < chromosomes.length; j++){
					division.add(chromosomes[j]);
				}
			}
			threads[i] = new PacmanWorkerThread((Chromosome[]) division.toArray());
		}
		//todo: Thread.join for loop
	}*/

	
	public int getMaxFitnessValue() {
		return maxFitness;
	}


	
}

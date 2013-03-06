package Anji;

import java.util.ArrayList;
import java.util.List;

import org.jgap.Chromosome;

public class PacmanFitnessFunction implements BulkFitnessFunction {

	private int numberOfThreads;
	
	public PacmanFitnessFunction(){
		setNumberOfThreads(4);
	}
	
	public void setNumberOfThreads(int i){
		if (i < 1){
			throw new Error("The minimum number of threads is 1");
		}
		numberOfThreads = i;
	}
	
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
	}

	
	public int getMaxFitnessValue() {
		return 0;
	}
	
}

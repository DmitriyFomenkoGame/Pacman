package Anji;

import java.util.ArrayList;
import java.util.List;

import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;

import com.anji.util.Configurable;
import com.anji.util.Properties;

@SuppressWarnings("serial")
public class PacmanFitnessFunction implements BulkFitnessFunction, Configurable {

	private int numberOfThreads;
	private int maxFitness;
	private Properties properties;
	private boolean showGUI;

	public PacmanFitnessFunction() {
		showGUI = false;
	}

	public void enableDisplay() {
		showGUI = true;
	}
	
	@Override
	public void init(Properties properties) throws Exception {
		try{
			numberOfThreads = properties.getIntProperty("threads.count", 4);
			maxFitness = properties.getIntProperty("fitness.max");
			this.properties = properties;
		}catch(Exception e){
			throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString() + ": "
					+ e.getMessage() );
		}
	}

	public void evaluate(Chromosome c) {
		List<Chromosome> l = new ArrayList<Chromosome>();
		l.add(c);
		evaluate(l); //Lekker lelijk, maarja..
	}
	
	public void evaluate(@SuppressWarnings("rawtypes") List subjects) {
		long start = System.currentTimeMillis();
		int current = 0;
		PacmanWorkerThread[] threads = new PacmanWorkerThread[numberOfThreads];
		while (current < subjects.size()) {
			for (int i = 0; i < numberOfThreads; i++) {
				if (threads[i] == null || !threads[i].isAlive()) {
					threads[i] = new PacmanWorkerThread((Chromosome) subjects.get(current), showGUI);
					threads[i].init(properties);
					threads[i].start();
					current++;
					break;
				}
			}
		}
		for (int j = 0; j < numberOfThreads; j++) {
			try {
				if (threads[j] != null) {
					threads[j].join();
				}
			} catch (InterruptedException e) {
				throw new Error("A thread got interrupted");
			}
		}
		long end = System.currentTimeMillis();
		System.out.printf("PacmanFitnessFunction.evaluate on %d chromosomes took %dms\n", subjects.size(), end - start);
	}

	public int getMaxFitnessValue() {
		return maxFitness;
	}

}

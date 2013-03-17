package Anji;

import org.apache.log4j.Logger;
import org.jgap.Chromosome;
import org.jgap.Configuration;

import com.anji.persistence.Persistence;
import com.anji.util.DummyConfiguration;
import com.anji.util.Properties;

public class PacmanEvaluator {
	private static final Logger logger = Logger.getLogger( PacmanEvaluator.class );

	public static void main( String[] args ) throws Exception {
		PacmanFitnessFunction ff = new PacmanFitnessFunction();
		Properties props = new Properties();
		props.loadFromResource(args[0]);
		ff.init(props);
		Persistence db = (Persistence) props.newObjectProperty(Persistence.PERSISTENCE_CLASS_KEY);
		Configuration config = new DummyConfiguration();
		Chromosome chrom = db.loadChromosome(args[1], config);
		if (chrom == null)
			throw new IllegalArgumentException("No chromosome found: " + args[1]);
		ff.enableDisplay();
		ff.evaluate(chrom);
		logger.info("Fitness = " + chrom.getFitnessValue());
	}
}
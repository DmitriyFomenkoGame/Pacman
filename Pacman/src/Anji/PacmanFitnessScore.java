package Anji;

import com.anji.util.Properties;

import Pacman.PacmanGame.Dir;
import Pacman.PacmanScore;

public class PacmanFitnessScore {
	private int fitness;
	private PacmanScore previousScore;
	private Dir prevPacmanDir;
	private int dotScore, energizerScore, ghostScore, ticksWithoutDots, dirChangePenalty, dirReversePenalty;
	private double deathModifier;
	private int dotlessTickCounter;
	
	public PacmanFitnessScore() {
		fitness = 0;
		previousScore = null;
		prevPacmanDir = Dir.UP;
		dotlessTickCounter = 0;
	}
	
	public void init(Properties properties) {
		dotScore 	   = properties.getIntProperty("game.score.dot", 10);
		energizerScore = properties.getIntProperty("game.score.energizer", 50);
		ghostScore 	   = properties.getIntProperty("game.score.ghost", 100);
		
		deathModifier     = properties.getDoubleProperty("game.score.death.modifier", 0.2);
		dirChangePenalty  = properties.getIntProperty("game.score.dir.change.penalty", 0);
		dirReversePenalty = properties.getIntProperty("game.score.dir.reverse.penalty", 200);
		
		ticksWithoutDots = properties.getIntProperty("game.score.steps.without.dots", 50) * 4;
	}
	
	public void addGameState(PacmanScore s, Dir pacmandir) {
		PacmanScore tickscore;
		if (previousScore == null) {
			tickscore = (PacmanScore) s.clone();
		} else {
			tickscore = s.scoreDifference(previousScore);
			if (pacmandir != prevPacmanDir) {
				int penalty = 0;
				switch(prevPacmanDir) {
					case UP:    penalty = (pacmandir == Dir.DOWN  ? 1 : 0) * dirReversePenalty; break;
					case RIGHT: penalty = (pacmandir == Dir.LEFT  ? 1 : 0) * dirReversePenalty; break;
					case DOWN:  penalty = (pacmandir == Dir.UP    ? 1 : 0) * dirReversePenalty; break;
					case LEFT:  penalty = (pacmandir == Dir.RIGHT ? 1 : 0) * dirReversePenalty; break;
				}
				if (penalty != 0) {
					fitness -= penalty;
				} else {
					fitness -= dirChangePenalty;
				}
			}
		}
		previousScore = (PacmanScore) s.clone();
		prevPacmanDir = pacmandir;
		
		//Dots
		if (tickscore.getDots() == 0) {
			dotlessTickCounter += tickscore.getGameticks();
			if (dotlessTickCounter > ticksWithoutDots) {
				fitness -= 1;
			}
		} else {
			dotlessTickCounter = 0;
			fitness += dotScore * tickscore.getDots();
		}
		
		//Energizers
		fitness += energizerScore * tickscore.getEnergizers();
		
		//Ghosts fright
		fitness += ghostScore * tickscore.getGhosts();
		
		//Ghosts others
		if (s.getDeaths() > 0) {
			fitness -= s.getGameticks() * deathModifier;
		}
	}
	
	public int getFitness() {
		return fitness;
	}
}

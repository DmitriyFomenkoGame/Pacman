package Pacman;

//Wrapper for the possible ways to get a higher score in pacman
public class PacmanScore implements Cloneable {
	private int dots, energizers, ghosts, gameticks, deaths;

	public PacmanScore() { //Automatically initializes with one gametick, can be incremented of more are executed at once.
		dots       = 0;
		energizers = 0;
		ghosts     = 0;
		deaths     = 0;
		gameticks  = 1;
	}

	public void addDot() {
		dots++;
	}
	public void addEnergizer() {
		energizers++;
	}
	public void addGhost() {
		ghosts++;
	}
	public void addGametick() {
		gameticks++;
	}
	public void addDeath() {
		deaths++;
	}

	public void addScore(PacmanScore p) {
		this.dots 		+= p.dots;
		this.energizers += p.energizers;
		this.ghosts 	+= p.ghosts;
		this.gameticks 	+= p.gameticks;
		this.deaths     += p.deaths;
	}
	
	public int getDots() {
		return dots;
	}
	public int getEnergizers() {
		return energizers;
	}
	public int getGhosts() {
		return ghosts;
	}
	public int getGameticks() {
		return gameticks;
	}
	public int getDeaths() {
		return deaths;
	}

	public Object clone(){
		try{
			PacmanScore cloned = (PacmanScore) super.clone();
			cloned.dots       = dots;
			cloned.energizers = energizers;
			cloned.ghosts     = ghosts;
			cloned.gameticks  = gameticks;
			cloned.deaths     = deaths;
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
}

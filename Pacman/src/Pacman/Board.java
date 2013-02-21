package Pacman;

public class Board implements Cloneable {

	private Pacman pacman;
	private Ghost blinky, pinky, inky, clyde;
	
	public Board() {
		pacman = new Pacman();
		blinky = new Ghost(pacman, Ghost.GHOST_BLINKY);
		pinky  = new Ghost(pacman, Ghost.GHOST_PINKY);
		inky   = new Ghost(pacman, Ghost.GHOST_INKY);
		clyde  = new Ghost(pacman, Ghost.GHOST_CLYDE);
	}

	public PacmanScore doMove(int direction) {
		
		return null;
	}
	
	
	public Object clone(){
		try{
			Board cloned = (Board) super.clone();
			cloned.pacman = (Pacman) pacman.clone();
			cloned.blinky = (Ghost) blinky.clone();
			cloned.pinky  = (Ghost) pinky.clone();
			cloned.inky   = (Ghost) inky.clone();
			cloned.clyde  = (Ghost) clyde.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
	
	
}

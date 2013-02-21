package Pacman;

public class Board implements Cloneable {

	private Pacman pacman;
	private Ghost blinky, pinky, inky, clyde;
	private boolean locked;
	
	public Board() {
		pacman = new Pacman();
		blinky = new Ghost(pacman, Ghost.GHOST_BLINKY);
		pinky  = new Ghost(pacman, Ghost.GHOST_PINKY);
		inky   = new Ghost(pacman, Ghost.GHOST_INKY);
		clyde  = new Ghost(pacman, Ghost.GHOST_CLYDE);
		locked = false;
	}

	public PacmanScore doMove(int direction) {
		
		return null;
	}
	
	
	public Object clone(){ //Clones are readonly
		try{
			Board cloned = (Board) super.clone();
			cloned.pacman = (Pacman) pacman.clone();
			cloned.blinky = (Ghost) blinky.clone();
			cloned.pinky  = (Ghost) pinky.clone();
			cloned.inky   = (Ghost) inky.clone();
			cloned.clyde  = (Ghost) clyde.clone();
			cloned.locked = true;
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
	
	
}

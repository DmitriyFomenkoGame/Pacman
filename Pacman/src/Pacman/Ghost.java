package Pacman;

public class Ghost implements Cloneable {

	public static final int GHOST_BLINKY = 0,
							GHOST_PINKY  = 1,
							GHOST_INKY   = 2,
							GHOST_CLYDE  = 3;
	
	public Ghost(Pacman pacman, int type) {
		
	}
	
	public Object clone(){
		try{
			Ghost cloned = (Ghost) super.clone();

			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
	
}

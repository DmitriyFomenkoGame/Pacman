package game;

/**
 * In this class the movement of the ghosts will be calculated.
 * Also their current state will be saved.
 * 
 * @author Luka Stout
 * @version 0.1
 */

public interface Ghost {

	
	/**
	 * @return
	 * 		returns the next move of the Ghost.
	 */
	public void doMove(/*Ook hier weer welk type moet het returnen?*/);
	
	/**
	 * @param
	 * 		The desired state of the Ghost.
	 */
	public void setState(int state); // type van de state van de Ghost?

}

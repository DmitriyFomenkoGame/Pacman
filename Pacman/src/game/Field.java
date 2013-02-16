package game;

import java.awt.image.BufferedImage;

/**
 * 
 * 
 * @author Luka Stout
 * @version 0.1
 */

public interface Field {

	/**
	 * @constructor
	 * 		The constructor should prepare the board for playing,
	 * 		putting all orbs in place and initialize all objects
	 */
	
	/**
	 * @return
	 * 		A bufferedImage of the board.
	 */
	
	public BufferedImage getBoard();
	
	/**
	 * @param direction
	 * 		Will move the Pacman in the specified direction
	 */
	public void doMove(/*WELK TYPE?*/);
	
	
}

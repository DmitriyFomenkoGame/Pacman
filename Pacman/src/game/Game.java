package game;

import java.awt.image.BufferedImage;

/**
 * In the Game class all information about the game, points, 
 * state of ghosts and the state of Pacman, is stored. 
 * All Pacman-moves that should be done on the board should be given to this class
 * 
 * @author Luka Stout
 * @version 0.1
 */

public interface Game {
	
	/**
	 * This is the function you should use when you want
	 * Pacman to make a move on the board.
	 * All the ghosts will also move at this time
	 * 
	 * 
	 * @param direction
	 * 		The direction that Pacman should move in.
	 */
	public void doMove(/*Welk type moet de direction zijn?*/);
	
	/**
	 * @return
	 * 		The current score
	 */
	public int getScore();
	
	/**
	 * @return
	 * 		A bufferedImage of the board.
	 */
	
	public BufferedImage getBoard();
	
	/**
	 * This will show the state of the board
	 */
	
	public void showBoard();
}

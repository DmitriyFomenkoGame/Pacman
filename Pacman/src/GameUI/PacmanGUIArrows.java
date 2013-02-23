package GameUI;

import java.awt.event.*;

public class PacmanGUIArrows extends PacmanGUI {
	
	private byte direction = Pacman.PacmanGame.DIR_LEFT;
	
	public PacmanGUIArrows(){
		super();
		this.frame.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			if(e.getKeyCode() == (KeyEvent.VK_LEFT)){
    				setDirection(Pacman.PacmanGame.DIR_LEFT);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_RIGHT)){
    				setDirection(Pacman.PacmanGame.DIR_RIGHT);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_UP)){
    				setDirection(Pacman.PacmanGame.DIR_UP);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_DOWN)){
    				setDirection(Pacman.PacmanGame.DIR_DOWN);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_ESCAPE)){
    				close();
    			}
    		}
		});
	}
	
	public void setDirection(byte direction){
		this.direction = direction;
	}
	
	public byte getDirection(){
		return this.direction;
	}
	
}

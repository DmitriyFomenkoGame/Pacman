package GameUI;

import java.awt.event.*;
import Pacman.PacmanGame.Dir;

public class PacmanGUIArrows extends PacmanGUI {
	
	private Dir direction = Dir.LEFT;
	
	public PacmanGUIArrows(){
		super();
		this.frame.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			if(e.getKeyCode() == (KeyEvent.VK_LEFT)){
    				setDirection(Dir.LEFT);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_RIGHT)){
    				setDirection(Dir.RIGHT);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_UP)){
    				setDirection(Dir.UP);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_DOWN)){
    				setDirection(Dir.DOWN);
    			}
    			if(e.getKeyCode() == (KeyEvent.VK_ESCAPE)){
    				close();
    			}
    		}
		});
	}
	
	public void setDirection(Dir direction){
		this.direction = direction;
	}
	
	public Dir getDirection(){
		return this.direction;
	}
}


package Pacman;

import GameUI.PacmanGUI;
import GameUI.PacmanGUIArrows;

public class PacmanControllerManual {

	public void start() {
//		PacmanGUI gui = new PacmanGUI();
		PacmanGUI gui = new PacmanGUIArrows();
		PacmanGame game = new PacmanGame(-1);
		gui.setBoard(game.getBoard());
		gui.show();
		while(true) {
			try {
				Thread.sleep(100);
				game.bypassMove();
				gui.setBoard(game.getBoard());
				gui.redraw();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new PacmanControllerManual().start();
	}

}

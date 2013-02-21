package Pacman;

import GameUI.PacmanGUI;

public class PacmanControllerManual {

	public void start() {
		PacmanGUI gui = new PacmanGUI();
		PacmanGame game = new PacmanGame(-1);
		gui.setBoard(game.getBoard());
		gui.show();
		while(true) {
			try {
				Thread.sleep(1000);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new PacmanControllerManual().start();
	}

}

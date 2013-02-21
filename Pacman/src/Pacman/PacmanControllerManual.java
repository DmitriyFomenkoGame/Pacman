package Pacman;

import GameUI.PacmanGUI;

public class PacmanControllerManual {

	public void start() {
		PacmanGUI gui = new PacmanGUI();
		gui.show();
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new PacmanControllerManual().start();
	}

}

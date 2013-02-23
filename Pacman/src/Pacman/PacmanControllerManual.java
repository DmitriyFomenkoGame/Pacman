package Pacman;

import GameUI.PacmanGUIArrows;

public class PacmanControllerManual {

	public void start() {
		PacmanGUIArrows gui = new PacmanGUIArrows();
		PacmanGame game = new PacmanGame(-1);
		gui.setBoard(game.getBoard());
		gui.show();
		while(game.getGameStatus() == PacmanGame.GAME_BUSY) {
			try {
				Thread.sleep(100);
				//game.bypassMove();
				game.doMove(gui.getDirection());
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

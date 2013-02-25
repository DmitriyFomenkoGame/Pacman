package Pacman;

import GameUI.PacmanGUIArrows;

public class PacmanControllerManual {

	public void start() {
		PacmanGUIArrows gui = new PacmanGUIArrows();
		PacmanGame game = new PacmanGame(-1);
		gui.setBoard(game.getBoard());
		gui.show();
		while(game.getStatus() == PacmanGame.GAME_BUSY && gui.isVisible()) {
			try {
				Thread.sleep(100);
				PacmanScore s = game.getScore();
				String title = "Dots: " + String.valueOf(s.getDots()) + " | " + game.getModeString();
				gui.setTitle(title);
				game.doMove(gui.getDirection());
				if (gui.getSpecial()) {
					game.special();
				}
				gui.setBoard(game.getBoard());
				gui.redraw();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		switch (game.getStatus()) {
			case PacmanGame.GAME_OVER:    System.out.println("GAME_OVER");    break;
			case PacmanGame.GAME_END:     System.out.println("GAME_END");     break;
			case PacmanGame.GAME_TIMEOUT: System.out.println("GAME_TIMEOUT"); break;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui.close();
	}
	
	public static void main(String[] args) {
		new PacmanControllerManual().start();
	}

}

package Pacman;

import GameUI.PacmanGUIArrows;

public class PacmanControllerManual {

	public void start(String mazeNumber) {
		PacmanGUIArrows gui = new PacmanGUIArrows();
		PacmanGame game = new PacmanGame(-1,Integer.parseInt(mazeNumber));
		gui.setBoard(game.getBoard());
		gui.show();
		while(game.getStatus() == PacmanGame.GAME_BUSY && gui.isVisible()) {
			try {
				Thread.sleep(35);
				PacmanScore s = game.getScore();
				String title = "Dots: " + String.valueOf(s.getDots()) + " | " + game.getModeString();
				gui.setTitle(title);
				game.doMove(gui.getDirection());
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
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui.close();
	}
	
	public static void main(String[] args) {
		if (args.length > 0){
			new PacmanControllerManual().start(args[0]);
		}
		else{
			new PacmanControllerManual().start("0");
		}
	}

}

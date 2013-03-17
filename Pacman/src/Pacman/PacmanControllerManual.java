package Pacman;

import Anji.PacmanFitnessScore;
import GameUI.PacmanGUIArrows;
import Pacman.PacmanGame.Status;
import Pacman.PacmanGame.Type;

public class PacmanControllerManual {

	public void start() {
		PacmanGUIArrows gui = new PacmanGUIArrows();
		PacmanGame game = new PacmanGame(-1, Type.SIMPLE);
		gui.setBoard(game.getBoard());
		gui.show();
		PacmanFitnessScore fitness = new PacmanFitnessScore();
		while(game.getStatus() == Status.BUSY && gui.isVisible()) {
			try {
				Thread.sleep(35);
				PacmanScore s = game.getScore();
				game.doMove(gui.getDirection());
				fitness.addGameState(game.getScore(), game.getBoard().getPacmanDirection());
				String title = "Dots: " + String.valueOf(s.getDots()) + " | " + game.getModeString() + " | "  + String.valueOf(fitness.getFitness());
				gui.setTitle(title);
				gui.setBoard(game.getBoard());
				gui.redraw();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		switch (game.getStatus()) {
			case GAME_OVER: System.out.println("GAME_OVER");    break;
			case GAME_END:  System.out.println("GAME_END");     break;
			case TIMEOUT: 	System.out.println("GAME_TIMEOUT"); break;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui.close();
		System.out.println(game.getScore().getGameticks());
	}
	
	public static void main(String[] args) {
		new PacmanControllerManual().start();
	}

}

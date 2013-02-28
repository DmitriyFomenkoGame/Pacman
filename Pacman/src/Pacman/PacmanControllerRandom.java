package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

import GameUI.PacmanGUIArrows;

public class PacmanControllerRandom {

	public void start() {
		Random rand = new Random();
		PacmanGUIArrows gui = new PacmanGUIArrows();
		for(int i = 0; i < 100; i++) {
			PacmanGame game = new PacmanGame(-1);
			gui.setBoard(game.getBoard());
			gui.show();
			byte dir = PacmanGame.DIR_LEFT;
			while(game.getStatus() == PacmanGame.GAME_BUSY/* && gui.isVisible()*/) {
				try {
					//Thread.sleep(1);
					PacmanScore s = game.getScore();
					String title = "Dots: " + String.valueOf(s.getDots()) + " | " + game.getModeString();
					gui.setTitle(title);
					game.doMove(dir);
					Board b = game.getBoard();
					Point2D.Double p = b.getPacmanPosition();
					//if (b.isCrossing(p) || b.isCorner(p)) {
					while (!b.directionFree(p, dir)) {dir = randomDir(rand);}
					//}
					//gui.setBoard(b);
					//gui.redraw();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PacmanScore s = game.getScore();
			System.out.printf("%d, %d, %d, %d, ", s.getDots(), s.getEnergizers(), s.getGameticks(), s.getGhosts());
			switch (game.getStatus()) {
				case PacmanGame.GAME_OVER:    System.out.println("GAME_OVER");    break;
				case PacmanGame.GAME_END:     System.out.println("GAME_END");     break;
				case PacmanGame.GAME_TIMEOUT: System.out.println("GAME_TIMEOUT"); break;
			}
		}
		/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		gui.close();
	}
	
	public byte randomDir(Random r) {
		return (byte) r.nextInt(PacmanGame.DIR_LEFT + 1);
	}
	
	public static void main(String[] args) {
		new PacmanControllerRandom().start();
	}

}

package GameUI;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Pacman.Board;

public class PacmanGUI {
	private JFrame frame;
	private ImagePanel panel;
	
	public static final int BOARD_WIDTH  = Board.WIDTH,
							BOARD_HEIGHT = Board.HEIGHT;
	
	public PacmanGUI() {
		frame = new JFrame("Pacman GUI");
		panel = new ImagePanel(null);
		frame.setContentPane(panel);
		frame.setSize(280, 310);
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void setBoard(Board b) {
		BufferedImage image = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		boolean[][] walls = b.getWalls();
		for(int j = 0; j < BOARD_HEIGHT; j++) {
			for(int i = 0; i < BOARD_WIDTH; i++) {
				if (walls[i][j]) {
					image.setRGB(i, j, Color.red.getRGB());
				}
			}
		}
		panel.setImage(image);
		//TODO: Add redraw...
	}
	
}

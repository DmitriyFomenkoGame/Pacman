package GameUI;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

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
		frame.setSize(560, 620);
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
					image.setRGB(i, j, Color.blue.getRGB());
				}
			}
		}
		Point2D blinky = b.getBlinkyPosition(),
				pinky  = b.getPinkyPosition(),
				inky   = b.getInkyPosition(),
				clyde  = b.getClydePosition();
		image.setRGB((int) Math.round(blinky.getX()), (int) Math.round(blinky.getY()), Color.red.getRGB());
		image.setRGB((int) Math.round(pinky.getX()),  (int) Math.round(pinky.getY()),  Color.pink.getRGB());
		image.setRGB((int) Math.round(inky.getX()),   (int) Math.round(inky.getY()),   Color.cyan.getRGB());
		image.setRGB((int) Math.round(clyde.getX()),  (int) Math.round(clyde.getY()),  Color.orange.getRGB());
		
		panel.setImage(image);
		//TODO: Add redraw...
	}
	
}

package GameUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Pacman.Board;

public class PacmanGUI {
	protected JFrame frame;
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
					imageTrySet(image, i, j, Color.blue);
				}
			}
		}
		Point2D blinky = b.getBlinkyPosition(),
				pinky  = b.getPinkyPosition(),
				inky   = b.getInkyPosition(),
				clyde  = b.getClydePosition(),
				pacman = b.getPacmanPosition();
		imageTrySet(image, blinky.getX(), blinky.getY(), Color.red);
		imageTrySet(image, pinky.getX(),  pinky.getY(),  Color.pink);
		imageTrySet(image, inky.getX(),   inky.getY(),   Color.cyan);
		imageTrySet(image, clyde.getX(),  clyde.getY(),  Color.orange);
		imageTrySet(image, pacman.getX(), pacman.getY(), Color.yellow);
		
		panel.setImage(image);
	}
	
	//TODO: Check redraw...
	public void redraw() {
		panel.invalidate();
		frame.getContentPane().invalidate();
		frame.getContentPane().validate();
		panel.validate();
		panel.repaint();
		frame.repaint();
	}
	
	private void imageTrySet(BufferedImage image, double x, double y, Color c) {
		int xx = (int) Math.round(x),
			yy = (int) Math.round(y);
		if (xx >= 0 && xx < BOARD_WIDTH && yy >= 0 && yy < BOARD_HEIGHT) {
			image.setRGB(xx, yy, c.getRGB());			
		}
	}
	private void imageTrySet(BufferedImage image, int x, int y, Color c) {
		imageTrySet(image, (double) x, (double) y, c);
	}
	
}

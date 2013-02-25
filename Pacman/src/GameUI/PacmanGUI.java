package GameUI;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Pacman.Board;
import Pacman.Ghost;

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
		byte[][] dots = b.getDots();
		for(int j = 0; j < BOARD_HEIGHT; j++) {
			for(int i = 0; i < BOARD_WIDTH; i++) {
				if (walls[i][j]) {
					imageTrySet(image, i, j, Color.blue);
				}
				if (dots[i][j] == Board.DOT_DOT) {
					imageTrySet(image, i, j, Color.darkGray);					
				} else if (dots[i][j] == Board.DOT_ENERGIZER) {
					imageTrySet(image, i, j, Color.gray);					
				}
			}
		}
		Point2D blinky = b.getBlinkyPosition(),
				pinky  = b.getPinkyPosition(),
				inky   = b.getInkyPosition(),
				clyde  = b.getClydePosition(),
				pacman = b.getPacmanPosition();

		imageTrySet(image, blinky.getX(), blinky.getY(), (!b.ghostIsEdible(Ghost.GHOST_BLINKY)) ? Color.red    : Color.magenta);
		imageTrySet(image, pinky.getX(),  pinky.getY(),  (!b.ghostIsEdible(Ghost.GHOST_PINKY))  ? Color.pink   : Color.magenta);
		imageTrySet(image, inky.getX(),   inky.getY(),   (!b.ghostIsEdible(Ghost.GHOST_INKY))   ? Color.cyan   : Color.magenta);
		imageTrySet(image, clyde.getX(),  clyde.getY(),  (!b.ghostIsEdible(Ghost.GHOST_CLYDE))  ? Color.orange : Color.magenta);
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
	
	public void close() {
		frame.dispose();
	}
	
	public boolean isVisible() {
		return frame.isVisible();
	}
	
	public void setTitle(String str) {
		frame.setTitle(str);
	}
}

package GameUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Pacman.Board;
import Pacman.Ghost;
import Pacman.PacmanGame.Dir;

public class BoardRenderer {
	
	public static final int BOARD_WIDTH  = Board.WIDTH,
							BOARD_HEIGHT = Board.HEIGHT;
	
	public static final int SPRITE_WIDTH  = 20,
						 	SPRITE_HEIGHT = 20;
	
	private BufferedImage imageBack,   imageFright,   imageDead, imagePacman, 
				  		  imageBlinky, imagePinky,    imageInky, imageClyde,
				  		  imageDot,    imageEnergizer;
	
	public BoardRenderer() {
		imageBack 	   = loadResource("background");
		imageFright    = loadResource("fright");
		imageDead      = loadResource("dead");
		imagePacman    = loadResource("pacman");
		imageBlinky    = loadResource("blinky");
		imagePinky     = loadResource("pinky");
		imageInky      = loadResource("inky");
		imageClyde     = loadResource("clyde");
		imageDot   	   = loadResource("dot");
		imageEnergizer = loadResource("energizer");
	}
	
	private BufferedImage loadResource(String filename) {
		try {
			return ImageIO.read(getClass().getResource("resources/" + filename + (filename.equals("background") ? ".png" : ".gif")));	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BufferedImage renderSimple(Board b) {
		BufferedImage image = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		boolean[][] walls = b.getWalls();
		Board.Dot[][] dots = b.getDots();
		for(int j = 0; j < BOARD_HEIGHT; j++) {
			for(int i = 0; i < BOARD_WIDTH; i++) {
				if (walls[i][j]) {
					imageTrySet(image, i, j, Color.blue);
				}
				if (dots[i][j] == Board.Dot.DOT) {
					imageTrySet(image, i, j, Color.darkGray);					
				} else if (dots[i][j] == Board.Dot.ENERGIZER) {
					imageTrySet(image, i, j, Color.gray);					
				}
			}
		}
		Point2D blinky = b.getBlinkyPosition(),
				pinky  = b.getPinkyPosition(),
				inky   = b.getInkyPosition(),
				clyde  = b.getClydePosition(),
				pacman = b.getPacmanPosition();

		imageTrySet(image, blinky.getX(), blinky.getY(), (!b.ghostIsEdible(Ghost.BLINKY)) ? Color.red    : Color.magenta);
		imageTrySet(image, pinky.getX(),  pinky.getY(),  (!b.ghostIsEdible(Ghost.PINKY))  ? Color.pink   : Color.magenta);
		imageTrySet(image, inky.getX(),   inky.getY(),   (!b.ghostIsEdible(Ghost.INKY))   ? Color.cyan   : Color.magenta);
		imageTrySet(image, clyde.getX(),  clyde.getY(),  (!b.ghostIsEdible(Ghost.CLYDE))  ? Color.orange : Color.magenta);
		imageTrySet(image, pacman.getX(), pacman.getY(), Color.yellow);
		
		return image;
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

	
	public BufferedImage renderFull(Board b) {
		BufferedImage image = new BufferedImage(BOARD_WIDTH * SPRITE_WIDTH, BOARD_HEIGHT * SPRITE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = image.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    graphicsTrySet(g, imageBack, 0, 0);
	    
		Board.Dot[][] dots = b.getDots();
		for(int j = 0; j < BOARD_HEIGHT; j++) {
			for(int i = 0; i < BOARD_WIDTH; i++) {
				if (dots[i][j] == Board.Dot.DOT) {
					graphicsTrySet(g, imageDot, i, j);		
				} else if (dots[i][j] == Board.Dot.ENERGIZER) {
					graphicsTrySet(g, imageEnergizer, i, j);		
				}
			}
		}
		Point2D blinky = b.getBlinkyPosition(),
				pinky  = b.getPinkyPosition(),
				inky   = b.getInkyPosition(),
				clyde  = b.getClydePosition(),
				pacman = b.getPacmanPosition();
		Dir pacmandir = b.getPacmanDirection();

		graphicsTrySet(g, (b.ghostIsEdible(Ghost.BLINKY)) ? imageFright : (b.ghostIsDead(Ghost.BLINKY) ? imageDead : imageBlinky), blinky.getX(), blinky.getY());
		graphicsTrySet(g, (b.ghostIsEdible(Ghost.PINKY))  ? imageFright : (b.ghostIsDead(Ghost.PINKY)  ? imageDead : imagePinky),  pinky.getX(),  pinky.getY());
		graphicsTrySet(g, (b.ghostIsEdible(Ghost.INKY))   ? imageFright : (b.ghostIsDead(Ghost.INKY)   ? imageDead : imageInky),   inky.getX(),   inky.getY());
		graphicsTrySet(g, (b.ghostIsEdible(Ghost.CLYDE))  ? imageFright : (b.ghostIsDead(Ghost.CLYDE)  ? imageDead : imageClyde),  clyde.getX(),  clyde.getY());
		graphicsTrySetAngle(g, imagePacman, pacman.getX(), pacman.getY(), dirToAngle(pacmandir));
		
		return image;
	}
	
	private int dirToAngle(Dir d) {
		switch (d) {
			case UP: 	return -90;
			case RIGHT: return 0;
			case DOWN:  return 90;
			default:    return 180;
		}
	}

	private void graphicsTrySet(Graphics2D g, Image img, double x, double y) {
		int xx = (int) Math.round(x * SPRITE_WIDTH),
			yy = (int) Math.round(y * SPRITE_HEIGHT);
		g.drawImage(img, xx, yy, null);
	}
	
	private void graphicsTrySetAngle(Graphics2D g, BufferedImage img, double x, double y, int angle) {
		int xx = (int) Math.round(x * SPRITE_WIDTH),
			yy = (int) Math.round(y * SPRITE_HEIGHT);
		AffineTransform at = new AffineTransform();
        at.translate(xx + img.getWidth() / 2, yy + img.getHeight() / 2);
        at.rotate(Math.PI / 180 * angle);
        at.translate(-img.getWidth() / 2, -img.getHeight() / 2);
        g.drawImage(img, at, null);
	}
}

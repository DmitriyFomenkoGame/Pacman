package Pacman;

import java.awt.geom.Point2D;

public class Pacman implements Cloneable {
	
	private Point2D.Double position;
	private byte direction;
	public static final double PACMAN_SPEED = 0.25;
	private boolean canmove;
	
	public Pacman() {
		position = new Point2D.Double(13, 23);
		direction = PacmanGame.DIR_LEFT;
		canmove = true;
	}

	public Point2D.Double doMove(Board b, byte direction) {
//		System.out.printf("%.2f %.2f\n", position.x, position.y);
		boolean stop = false;
		if (position.x == Math.floor(position.x) && position.y == Math.floor(position.y)) {
			if (b.directionFree(position, direction)) {
				this.direction = direction;
			}
			if (b.isWall(b.getNextTile(position, this.direction))) {
				stop = true;
			}
		}
		if (!stop) {
			switch (this.direction) {
				case PacmanGame.DIR_UP:    moveRelative(0, -PACMAN_SPEED); break;
				case PacmanGame.DIR_RIGHT: moveRelative( PACMAN_SPEED, 0); break;
				case PacmanGame.DIR_DOWN:  moveRelative(0,  PACMAN_SPEED); break;
				case PacmanGame.DIR_LEFT:  moveRelative(-PACMAN_SPEED, 0); break;
			}
		}
		return position;
	}

	public Point2D.Double getPosition() {
		return (Point2D.Double) position.clone();
	}
	
	private void moveRelative(double dx, double dy) {
		position.setLocation(position.getX() + dx, position.getY() + dy);
	}
	
	public Object clone(){
		try{
			Pacman cloned = (Pacman) super.clone();
			cloned.position = (Point2D.Double) position.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}
	
}

package Pacman;

import java.awt.Point;
import java.awt.geom.Point2D;

import Pacman.PacmanGame.Dir;

public class Pacman implements Cloneable {
	
	private Point2D.Double position;
	private Dir direction;
	public static final double PACMAN_SPEED = 0.25;
	
	public Pacman() {
		position = new Point2D.Double(13, 23);
		direction = Dir.LEFT;
	}

	public Point2D.Double doMove(Board b, Dir direction) {
		boolean stop = false;
		if (position.x == Math.floor(position.x) && position.y == Math.floor(position.y)) {
			if (!position.equals(new Point(13,11)) || direction != Dir.DOWN) {
				if (b.directionFree(position, direction)) {
					this.direction = direction;
				}
			}
			if (b.isWall(b.getNextTile(position, this.direction))) {
				stop = true;
			}
		}
		if (!stop) {
			switch (this.direction) {
				case UP:    moveRelative(0, -PACMAN_SPEED); break;
				case RIGHT: moveRelative( PACMAN_SPEED, 0); break;
				case DOWN:  moveRelative(0,  PACMAN_SPEED); break;
				case LEFT:  moveRelative(-PACMAN_SPEED, 0); break;
			}
			if (position.x < 0) {
				position.x += Board.WIDTH;
			} else if (position.x >= Board.WIDTH) {
				position.x -= Board.WIDTH;
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
	
	public Dir getDirection(){
		return this.direction;
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

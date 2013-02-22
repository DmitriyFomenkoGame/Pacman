package Pacman;

import java.awt.geom.Point2D;

public class Pacman implements Cloneable {
	
	private Point2D.Double position;
	
	public Pacman() {
		position = new Point2D.Double(13, 23);
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

	public Point2D.Double doMove(byte direction) {
		
		return position;
	}
	
	public Point2D.Double getPosition() {
		return (Point2D.Double) position.clone();
	}
	
}

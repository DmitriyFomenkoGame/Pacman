package Pacman;

import java.awt.geom.Point2D;

public class TestGhost extends Ghost{

	public TestGhost(Board board) {
		super(board);
	}
	
	public void setPosition(Point2D.Double pos){
		super.position = pos;
	}

}
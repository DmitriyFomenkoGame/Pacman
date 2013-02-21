package GameUI;

import java.awt.Color;

import javax.swing.JFrame;

import Pacman.Board;

public class PacmanGUI {
	private JFrame frame;
	private ImagePanel panel;
	
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

	}
	
}

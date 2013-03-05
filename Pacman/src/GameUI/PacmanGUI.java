package GameUI;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;
import Pacman.Board;

public class PacmanGUI {
	protected JFrame frame;
	private ImagePanel panel;
	private BoardRenderer renderer;
	
	public PacmanGUI() {
		frame = new JFrame("Pacman GUI");
		panel = new ImagePanel(null);
		frame.setContentPane(panel);
		Insets insets = frame.getInsets();
		int insetwidth = insets.left + insets.right, insetheight = insets.top + insets.bottom;
		frame.setSize(560 + insetwidth, 620 + insetheight + 10);
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		renderer = new BoardRenderer();
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void setBoard(Board b) {
		panel.setImage(renderer.renderFull(b));
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

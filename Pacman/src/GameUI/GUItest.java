package GameUI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class GUItest {

	public void start() throws IOException, InterruptedException {
		BufferedImage myImage = new BufferedImage(28, 31, BufferedImage.TYPE_INT_ARGB);
		String field = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWDDDDDDDDDDDDWWDDDDDDDDDDDDWWDWWWWDWWWWWDWWDWWWWWDWWWWDWWEWWWWDWWWWWDWWDWWWWWDWWWWEWWDWWWWDWWWWWDWWDWWWWWDWWWWDWWDDDDDDDDDDDDDDDDDDDDDDDDDDWWDWWWWDWWDWWWWWWWWDWWDWWWWDWWDWWWWDWWDWWWWWWWWDWWDWWWWDWWDDDDDDWWDDDDWWDDDDWWDDDDDDWWWWWWWDWWWWW WW WWWWWDWWWWWWWWWWWWDWWWWW WW WWWWWDWWWWWWWWWWWWDWW    G     WWDWWWWWWWWWWWWDWW WWWWWWWW WWDWWWWWWWWWWWWDWW W      W WWDWWWWWW      D   W G G GW   D      WWWWWWDWW W      W WWDWWWWWWWWWWWWDWW WWWWWWWW WWDWWWWWWWWWWWWDWW          WWDWWWWWWWWWWWWDWW WWWWWWWW WWDWWWWWWWWWWWWDWW WWWWWWWW WWDWWWWWWWDDDDDDDDDDDDWWDDDDDDDDDDDDWWDWWWWDWWWWWDWWDWWWWWDWWWWDWWDWWWWDWWWWWDWWDWWWWWDWWWWDWWEDDWWDDDDDDDPDDDDDDDDWWDDEWWWWDWWDWWDWWWWWWWWDWWDWWDWWWWWWDWWDWWDWWWWWWWWDWWDWWDWWWWDDDDDDWWDDDDWWDDDDWWDDDDDDWWDWWWWWWWWWWDWWDWWWWWWWWWWDWWDWWWWWWWWWWDWWDWWWWWWWWWWDWWDDDDDDDDDDDDDDDDDDDDDDDDDDWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
		for(int j = 0; j < 31; j++) {
			for(int i = 0; i < 28; i++) {
				char c = field.charAt(j * 28 + i);
				if (c == 'W') {
					myImage.setRGB(i, j, Color.blue.getRGB());
				} else if (c == ' ') {
					myImage.setRGB(i, j, Color.black.getRGB());
				} else if (c == 'D') {
					myImage.setRGB(i, j, Color.gray.getRGB());
				} else if (c == 'E') {
					myImage.setRGB(i, j, Color.lightGray.getRGB());
				} else if (c == 'P') {
					myImage.setRGB(i, j, Color.yellow.getRGB());
				} else {
					myImage.setRGB(i, j, Color.red.getRGB());
				}
			}
		}
		JFrame myJFrame = new JFrame("Pacman test JONGUH");
		ImagePanel imagePanel = new ImagePanel(myImage);
//		imagePanel.setSize(280, 310);
		myJFrame.setContentPane(imagePanel);
		myJFrame.setSize(280, 310);
		myJFrame.setBackground(Color.black);
		myJFrame.setVisible(true);
		myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while (true) {
			Thread.sleep(5000);
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new GUItest().start();
	}

}

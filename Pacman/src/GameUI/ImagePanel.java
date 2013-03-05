package GameUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ImagePanel extends JComponent implements ComponentListener {
	private Image image;
    
    private Dimension size = null;
    private int width   = 0,
    	        height  = 0,
    	        xoffset = 0,
    	        yoffset = 0;
    
    public ImagePanel() {
    	this(null);
    }
    
    public ImagePanel(Image image) {
        this.image = image;
        this.addComponentListener(this);
    }
    
    public void setImage(Image image) {
    	this.image = image;
    }
    
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}
	public void componentResized(ComponentEvent e) {
		if (image == null){return;}
    	size = this.getSize();
    	float wfrac = size.width / 28, hfrac = size.height / 31;
    	if (wfrac > hfrac) {
    		height  = size.height;
    		width   = (int) (28 * hfrac);
    	} else {
    		width   = size.width;
    		height  = (int) (32 * wfrac);
    	}
		xoffset = (int) ((size.width - width)   * 0.5);
		yoffset = (int) ((size.height - height) * 0.5);
	}
	
    protected void paintComponent(Graphics g) {
    	if (image == null) {return;}
        g.drawImage(image, xoffset, yoffset, width, height, null);
    }
}

package renderer.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	private int mouseX = -1;
	private int mouseY = -1;
	private int mouseB = -1;
	private int scroll = 0;
	
	private double moveSensitivity = 1.0;
	private double scrollSensitivity = 1.0;
	
	public int getX() {return this.mouseX;}
	
	public int getY() {return this.mouseY;}
	
	public MouseMap getButton() {
		return MouseMap.getAction(mouseB);
	}
	
	public int getScroll() {return this.scroll;}
	public void resetScroll() {this.scroll = 0;}
	
	public double getMoveSensitivity() {return this.moveSensitivity;}
	public void setMoveSensitivity(double value) {this.moveSensitivity = value;}

	public double getScrollSensitivity() {return this.scrollSensitivity;}
	public void setScrollSensitivity(double value) {this.scrollSensitivity = value;}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.scroll = e.getWheelRotation();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseB = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

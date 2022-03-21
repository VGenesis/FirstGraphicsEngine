package renderer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[65536];
	private boolean left, right, forward, backward, up, down;
			
	public void update() {
		this.left = this.keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_A];
		this.right = this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D];
		this.forward = this.keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_W];
		this.backward = this.keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_S];
		this.up = this.keys[KeyEvent.VK_SPACE];
		this.down = this.keys[KeyEvent.VK_SHIFT];
	}
	
	public boolean getLeft() {return left;}
	public boolean getRight() {return right;}
	public boolean getForward() {return forward;}
	public boolean getBackward() {return backward;}
	public boolean getUp() {return up;}
	public boolean getDown() {return down;}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}

package renderer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[65536];
	private boolean left, right, up, down, forward, backward;
			
	public void update() {
		this.left = this.keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_A];
		this.right = this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D];
		this.forward = this.keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_W];
		this.backward = this.keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_S];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

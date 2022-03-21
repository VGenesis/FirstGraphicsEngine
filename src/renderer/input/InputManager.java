package renderer.input;

public class InputManager {
	public Mouse mouse;
	public Keyboard keyboard;
	
	public InputManager() {
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
	}
}

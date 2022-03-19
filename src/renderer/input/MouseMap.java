package renderer.input;

public enum MouseMap{
	LCLICK(1),
	MCLICK(2),
	RCLICK(3),
	PAGEDOWN(4),
	PAGEUP(5);
	
	private int value;
	
	private MouseMap(int value) {
		this.value = value;
	}
	
	public int getValue() {return value;}
	
	public static MouseMap getAction(int value) {
		for(MouseMap m : values()) {
			if(m.value == value) return m;
		}
		return null;
	}
}

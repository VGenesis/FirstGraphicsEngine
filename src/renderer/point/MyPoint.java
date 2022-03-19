package renderer.point;

public class MyPoint {
	public double x, y, z;
	
	public MyPoint(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void move(MyPoint vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	
}

package renderer.world;

public class Camera {
	private double x, y, z;
	private double roll, pitch, yaw;

	public Camera(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {return x;}
	public double getY() {return y;}
	public double getZ() {return z;}
	
	public void translate(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
}

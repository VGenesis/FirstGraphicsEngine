package renderer.point;

import java.util.Random;

public class Point3D {
	public static final Point3D origin = new Point3D(0, 0, 0);
	public double x, y, z;
	private Random random;
	
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.random = new Random();
	}
	
	public double getX() {return x + x;}
	public double getY() {return y + y;}
	public double getZ() {return z + z;}
	
	public void translate(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void translate(Point3D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	
	public void translate(Vector3D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	
	public void scale(double scale) {
		this.x = this.getX() * scale - this.x;
		this.y = this.getY() * scale - this.y;
		this.z = this.getZ() * scale - this.z;
	}
	
	public void shift() {
		double shiftFactor = 2;
		double balance = 0.5;
		this.x += (random.nextDouble() - balance) * shiftFactor;
		this.y += (random.nextDouble() - balance) * shiftFactor;
		this.z += (random.nextDouble() - balance) * shiftFactor;
	}
	
	public static double dist(Point3D p1, Point3D p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));
	}
	
}

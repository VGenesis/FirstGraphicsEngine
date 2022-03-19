package renderer.point;

public class MyVector {
	public double x, y, z;
	
	public MyVector() {
		this.x = this.y = this.z = 0;
	}
	
	public MyVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public MyVector(MyPoint source, MyPoint destination) {
		this.x = destination.x - source.x;
		this.y = destination.y - source.y;
		this.z = destination.z - source.z;
	}
	
	public static MyVector normalize(MyVector v) {
		double intensity = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
		return new MyVector(v.x / intensity, v.y / intensity, v.z / intensity);
	}
	
	public static double dotProduct(MyVector v1, MyVector v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	public static MyVector crossProduct(MyVector v1, MyVector v2) {
		return new MyVector(
				v1.y * v2.z - v1.z - v2.y,
				v1.z * v2.x - v1.x - v2.z,
				v1.x * v2.y - v1.y - v2.x);
	}
	
	public static double getIntensity(MyVector v) {
		return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}
	
}

package renderer.point;

public class Vector3D {
	public double x, y, z;
	
	public Vector3D() {
		this.x = this.y = this.z = 0;
	}
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Point3D source, Point3D destination) {
		this.x = destination.x - source.x;
		this.y = destination.y - source.y;
		this.z = destination.z - source.z;
	}
	
	public static Vector3D normalize(Vector3D v) {
		double intensity = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
		return new Vector3D(v.x / intensity, v.y / intensity, v.z / intensity);
	}
	
	public static double dotProduct(Vector3D v1, Vector3D v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	public static Vector3D crossProduct(Vector3D v1, Vector3D v2) {
		return new Vector3D(
				v1.y * v2.z - v1.z - v2.y,
				v1.z * v2.x - v1.x - v2.z,
				v1.x * v2.y - v1.y - v2.x);
	}
	
	public static double getIntensity(Vector3D v) {
		return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}
	
}

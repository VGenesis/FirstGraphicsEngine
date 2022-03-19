package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;

public class MyPolygon {
	private static final double AMBIENT_LIGHT = 0.03;
	
	private Color baseColor, shadedColor;
	private MyPoint[] points;
	
	public MyPoint center;

	public MyVector normalVector;
	
	
	public MyPolygon(MyPoint... points) {
		this.baseColor = this.shadedColor = null;
		this.points = new MyPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x, p.y, p.z);
		}
		findCenter();
	}
	
	public MyPolygon(Color color, MyPoint... points) {
		this.baseColor = this.shadedColor = color;
		this.points = new MyPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x, p.y, p.z);
		}
		findCenter();
	}
	
	public void findCenter() {
		center = new MyPoint(0, 0, 0);
		for(MyPoint p : this.points) {
			center.x += p.x;
			center.y += p.y;
			center.z += p.z;
		}
		center.x /= this.points.length;
		center.y /= this.points.length;
		center.z /= this.points.length;
	}
	
	public void findNormalVector() {
		MyVector v1 = new MyVector(this.points[0], this.points[1]);
		MyVector v2 = new MyVector(this.points[1], this.points[2]);
		this.normalVector = MyVector.normalize(MyVector.crossProduct(v2, v1));
	}
	
	public double getAverageX() {
		int depth = 0;
		for(MyPoint p : this.points) {
			depth += p.x;
		}
		return depth / points.length;
	}
	
	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for(int i = 0; i < points.length; i++) {
			Point p = PointConverter.convertPoint(points[i]);
			poly.addPoint(p.x, p.y);
		}
		g.setColor(this.shadedColor);
		g.fillPolygon(poly);
	}
	
	public void setColor(Color color) {this.baseColor = color;}
	
	public void move(MyPoint vector) {
		for(MyPoint p : this.points) {
			p.move(vector);
		}
	}
	
	public void rotate(MyPoint pivot, boolean CW, double degreesX, double degreesY, double degreesZ) {
		this.findCenter();
		this.move(this.center);
		this.rotateAxis(CW, degreesX, degreesY, degreesZ);
		this.move(this.center);
	}
	
	public void rotateAxis(boolean CW, double degreesX, double degreesY, double degreesZ) {
		for(MyPoint p : this.points) {
			PointConverter.rotateAxisX(p, CW, degreesX);
			PointConverter.rotateAxisY(p, CW, degreesY);
			PointConverter.rotateAxisZ(p, CW, degreesZ);
		}
	}
	
	public static MyPolygon[] sortPolygons(MyPolygon[] polygons) {
		ArrayList<MyPolygon> polygonList = new ArrayList<MyPolygon>();
		for(MyPolygon poly : polygons) {
			polygonList.add(poly);
		}
		Collections.sort(polygonList, new Comparator<MyPolygon>() {
			@Override
			public int compare(MyPolygon p1, MyPolygon p2) {
				double diff = p1.getAverageX() - p2.getAverageX();
				if(diff == 0) return 0;
				else return (int) Math.signum(diff);
			}
		});
		for(int i = 0; i < polygons.length; i++) {
			polygons[i] = polygonList.get(i);
		}
		
		return polygons;
	}
	
	public void updateLightingRatio(MyVector lightVector) {
		double dot = MyVector.dotProduct(normalVector, lightVector);
		dot = (dot + 1) / 2 * (1 - AMBIENT_LIGHT);
		double lightRatio = (AMBIENT_LIGHT + dot > 1)? 1 : AMBIENT_LIGHT + dot;
		this.updateLightingColor(lightRatio);
	}
	
	private void updateLightingColor(double lightRatio) {
		int r = (int) (this.baseColor.getRed() * lightRatio);
		int g = (int) (this.baseColor.getGreen() * lightRatio);
		int b = (int) (this.baseColor.getBlue() * lightRatio);
		this.shadedColor = new Color(r, g, b);
	}
	
	public void orientNormalVector(MyPoint basis) {
		this.findCenter();
		MyVector basisVector = new MyVector(this.center.x - basis.x,
											this.center.y - basis.y,
											this.center.z - basis.z);
		normalVector = MyVector.normalize(basisVector);
	}

	public MyPoint[] getPoints() {
		return points;
	}
	
}

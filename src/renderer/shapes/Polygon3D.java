package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import renderer.Display;
import renderer.point.Point3D;
import renderer.point.Vector3D;
import renderer.point.PointConverter;

public class Polygon3D {
	private static final double AMBIENT_LIGHT = 0.03;
	
	private Color baseColor, shadedColor;
	private Point3D[] points;
	private boolean visible;
	
	public Point3D center;
	public Vector3D normalVector;
	
	public double scale;
	
	public Polygon3D(Point3D... points) {
		this.baseColor = this.shadedColor = null;
		this.points = new Point3D[points.length];
		this.scale = 1;
		for(int i = 0; i < points.length; i++) {
			Point3D p = points[i];
			this.points[i] = new Point3D(p.x, p.y, p.z);
		}
		this.updateVisibility();
	}
	
	public Polygon3D(Color color, Point3D... points) {
		this.baseColor = this.shadedColor = color;
		this.points = new Point3D[points.length];
		this.scale = 1;
		for(int i = 0; i < points.length; i++) {
			Point3D p = points[i];
			this.points[i] = new Point3D(p.x, p.y, p.z);
		}
		this.updateVisibility();
	}
	
	public boolean isVisible() {return this.visible;}
	
	public void updateVisibility() {
		this.findCenter();
		this.visible = (this.center.x < Display.render_distance);
	}
	
	public void findCenter() {
		this.center = new Point3D(0, 0, 0);
		for(Point3D p : this.points) {
			this.center.translate(p);
		}
		this.center.x /= this.points.length;
		this.center.y /= this.points.length;
		this.center.z /= this.points.length;
	}
	
	public void findNormalVector() {
		Vector3D v1 = new Vector3D(this.points[0], this.points[1]);
		Vector3D v2 = new Vector3D(this.points[1], this.points[2]);
		this.normalVector = Vector3D.normalize(Vector3D.crossProduct(v2, v1));
	}
	
	public void render(Graphics g) {
		if(!this.visible) return;
		Polygon3D scaledPolygon = this.getScaledPolygon();
		Polygon poly = new Polygon();
		for(int i = 0; i < points.length; i++) {
			Point p = PointConverter.convertPoint(scaledPolygon.points[i]);
			poly.addPoint(p.x, p.y);
		}
		g.setColor(this.shadedColor);
		g.fillPolygon(poly);
	}
	
	public void setColor(Color color) {this.baseColor = color;}
	
	public void translate(Point3D vector) {
		for(Point3D p : this.points) {
			p.translate(vector);
		}
		this.updateVisibility();
	}
	
	public void rotateAxis(boolean CW, double degreesX, double degreesY, double degreesZ) {
		for(Point3D p : this.points) {
			PointConverter.rotateAxisX(p, CW, degreesX);
			PointConverter.rotateAxisY(p, CW, degreesY);
			PointConverter.rotateAxisZ(p, CW, degreesZ);
		}
		this.updateVisibility();
	}
	
	public static Polygon3D[] sortPolygons(Polygon3D[] polygons) {
		ArrayList<Polygon3D> polygonList = new ArrayList<Polygon3D>();
		for(Polygon3D poly : polygons) {
			polygonList.add(poly);
		}
		Collections.sort(polygonList, new Comparator<Polygon3D>() {
			@Override
			public int compare(Polygon3D p1, Polygon3D p2) {
				p1.findCenter();
				p2.findCenter();
				double diff = p2.center.x - p1.center.x;
				if(diff == 0) return 0;
				else return diff < 0? 1 : -1;
			}
		});
		for(int i = 0; i < polygons.length; i++) {
			polygons[i] = polygonList.get(i);
		}
		
		return polygons;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	private Polygon3D getScaledPolygon() {
		Polygon3D polygon = new Polygon3D(this.points);
		polygon.findCenter();
		for(Point3D p : polygon.points) {
			double xOffset = p.x - polygon.center.x;
			double yOffset = p.y - polygon.center.y;
			double zOffset = p.z - polygon.center.z;
			
			p.x = polygon.center.x + xOffset * this.scale;
			p.y = polygon.center.y + yOffset * this.scale;
			p.z = polygon.center.z + zOffset * this.scale;
		}
		return polygon;
	}
	
	public void shift() {
		for(Point3D p : this.points) {
			p.shift();
		}
	}
	
	public void updateLightingRatio(Vector3D lightVector) {
		double dot = Vector3D.dotProduct(normalVector, lightVector);
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
	
	public void orientNormalVector(Point3D basis) {
		this.findCenter();
		Vector3D basisVector = new Vector3D(this.center.x - basis.x,
											this.center.y - basis.y,
											this.center.z - basis.z);
		this.normalVector = Vector3D.normalize(basisVector);
	}
	
}

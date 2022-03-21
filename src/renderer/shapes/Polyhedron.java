package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;

import renderer.point.Point3D;
import renderer.point.Vector3D;

public class Polyhedron {
	private Polygon3D[] polygons;
	private Color color;
	
	public Point3D center;
	
	public Polyhedron(Color color, Polygon3D... polygons) {
		this.color = color;
		this.polygons = polygons;
		if(color != null) this.setPolygonColor();
		this.sortPolygons();
		this.findCenter();
	}
	
	private void findCenter() {
		this.center = new Point3D(0, 0, 0);
		for(Polygon3D poly : this.polygons) {
			poly.findCenter();
			this.center.x += poly.center.x;
			this.center.y += poly.center.y;
			this.center.z += poly.center.z;
		}
		this.center.x /= this.polygons.length;
		this.center.y /= this.polygons.length;
		this.center.z /= this.polygons.length;
	}
	
	public void render(Graphics g) {
		for(Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}
	
	public Polygon3D[] getPolygons() {return polygons;}
	
	private void sortPolygons() {
		Polygon3D.sortPolygons(polygons);
	}
	
	private void setPolygonColor() {
		for(Polygon3D poly : this.polygons) {
			poly.setColor(color);
		}
		this.sortPolygons();
	}
	
	public void translate(double moveX, double moveY, double moveZ) {
		for(Polygon3D poly : this.polygons) {
			poly.translate(new Point3D(moveX, moveY, moveZ));
		}
		this.findCenter();
		this.sortPolygons();
	}
	
	public void rotateAxis(boolean CW, double degreesX, double degreesY, double degreesZ) {
		for(Polygon3D poly : this.polygons) {
			poly.rotateAxis(CW, degreesX, degreesY, degreesZ);
		}
		this.sortPolygons();
	}
	
	public void rotate(boolean CW, double degreesX, double degreesY, double degreesZ, Point3D pivot) {
		findCenter();
		if(pivot == null) pivot = this.center;
		this.translate(-pivot.x, -pivot.y, -pivot.z);
		this.rotateAxis(CW, degreesX, degreesY, degreesZ);
		this.translate(pivot.x, pivot.y, pivot.z);
		this.sortPolygons();
	}
	
	public void scale(double scale) {
		for(Polygon3D p : this.polygons) {
			p.setScale(scale);
		}
		this.sortPolygons();
	}

	public void shift() {
		for(Polygon3D p : this.polygons) {
			p.shift();
		}
		this.sortPolygons();
	}
	
	public void setLighting(Vector3D lightVector) {
		this.findCenter();
		for(Polygon3D p : this.polygons) {
			p.orientNormalVector(this.center);
			p.updateLightingRatio(lightVector);
		}
	}
}

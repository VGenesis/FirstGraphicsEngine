package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import renderer.entity.IEntity;
import renderer.point.MyPoint;
import renderer.point.MyVector;

public class Tetrahedron {
	private MyPolygon[] polygons;
	private Color color;
	
	public MyPoint center;
	
	public Tetrahedron(Color color, MyPolygon... polygons) {
		this.color = color;
		this.polygons = polygons;
		if(color != null) this.setPolygonColor();
		this.sortPolygons();
		this.findCenter();
	}
	
	private void findCenter() {
		center = new MyPoint(0, 0, 0);
		for(MyPolygon poly : this.polygons) {
			poly.findCenter();
			center.x += poly.center.x;
			center.y += poly.center.y;
			center.z += poly.center.z;
		}
		center.x /= this.polygons.length;
		center.y /= this.polygons.length;
		center.z /= this.polygons.length;
	}
	
	public void render(Graphics g) {
		for(MyPolygon poly : this.polygons) {
			poly.render(g);
		}
	}
	
	public MyPolygon[] getPolygons() {return polygons;}
	
	private void sortPolygons() {
		MyPolygon.sortPolygons(polygons);
	}
	
	private void setPolygonColor() {
		for(MyPolygon poly : this.polygons) {
			poly.setColor(color);
		}
	}
	
	private void setRandomPolygonColor() {
		Random rng = new Random();
		for(MyPolygon poly : this.polygons) {
			poly.setColor(new Color(rng.nextFloat(), rng.nextFloat(), rng.nextFloat()));
		}
	}
	
	public void move(double moveX, double moveY, double moveZ) {
		for(MyPolygon poly : this.polygons) {
			poly.move(new MyPoint(moveX, moveY, moveZ));
		}
		this.sortPolygons();
	}
	
	public void rotateAxis(boolean CW, double degreesX, double degreesY, double degreesZ) {
		for(MyPolygon poly : this.polygons) {
			poly.rotateAxis(CW, degreesX, degreesY, degreesZ);
		}
		this.sortPolygons();
	}
	
	public void rotate(boolean CW, double degreesX, double degreesY, double degreesZ) {
		findCenter();
		this.move(-center.x, -center.y, -center.z);
		this.rotateAxis(CW, degreesX, degreesY, degreesZ);
		this.move(center.x, center.y, center.z);
		this.sortPolygons();
	}
	
	public void rotateAround(boolean CW, MyPoint pivot, double degreesX, double degreesY, double degreesZ) {
		this.move(-pivot.x, -pivot.y, -pivot.z);
		this.rotateAxis(CW, degreesX, degreesY, degreesZ);
		this.move(pivot.x, pivot.y, pivot.z);
		this.sortPolygons();
	}
	
	public void setLighting(MyVector lightVector) {
		this.findCenter();
		for(MyPolygon p : this.polygons) {
			p.orientNormalVector(center);
			p.updateLightingRatio(lightVector);
		}
	}
}

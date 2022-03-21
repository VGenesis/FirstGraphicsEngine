package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import renderer.point.Point3D;
import renderer.point.Vector3D;
import renderer.shapes.Polygon3D;
import renderer.shapes.Polyhedron;

public class Entity implements IEntity {
	
	private ArrayList<Polyhedron> mesh;
	private Polygon3D[] polygons;
	public Point3D center;
	
	public Entity(ArrayList<Polyhedron> mesh) {
		this.mesh = mesh;
		this.center = new Point3D(0, 0, 0);
		int polygonCount = 0;
		ArrayList<Polygon3D> tempList = new ArrayList<Polygon3D>();
		for(Polyhedron t : mesh) {
			polygonCount += t.getPolygons().length;
			for(Polygon3D poly : t.getPolygons()) {
				tempList.add(poly);
			}
		}
		
		polygons = new Polygon3D[polygonCount];
		int i = 0;
		for(Polygon3D poly : tempList) {
			polygons[i++] = poly;
		}
		this.sortPolygons();
		this.findCenter();
	}
	
	private void findCenter() {
		this.center = new Point3D(0, 0, 0);
		for(Polyhedron t : this.mesh) {
			this.center.translate(t.center);
		}
		this.center.x /= this.mesh.size();
		this.center.y /= this.mesh.size();
		this.center.z /= this.mesh.size();
	}
	
	@Override
	public ArrayList<Polyhedron> getMesh(){return mesh;}
	
	@Override
	public void render(Graphics g) {
		this.sortPolygons();
		for(Polygon3D p : this.polygons) {
			p.render(g);
		}
	}
	
	@Override
	public void rotate(boolean CW, double degreesX, double degreesY, double degreesZ) {
		this.findCenter();
		for(Polyhedron t : this.mesh) {
			t.rotate(CW, degreesX, degreesY, degreesZ, this.center);
		}
	}

	@Override
	public void translate(double moveX, double moveY, double moveZ) {
		for(Polyhedron t : this.mesh) {
			t.translate(moveX, moveY, moveZ);
		}
		this.findCenter();
	}
	
	public void scale(double scale) {
		for(Polyhedron t : this.mesh) {
			t.scale(scale);
		}
	}
	
	public void setLighting(Vector3D lightVector) {
		for(Polyhedron t : this.mesh) {
			t.setLighting(lightVector);
		}
	}
	
	@Override
	public void shift() {
		for(Polyhedron p : this.mesh) {
			p.shift();
		}
		this.sortPolygons();
	}
	
	public void sortPolygons() {
		Polygon3D.sortPolygons(this.polygons);
	}

}

package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class Entity implements IEntity {
	
	private ArrayList<Tetrahedron> tetrahedrons;
	
	private MyPolygon[] polygons;
	
	public MyPoint center;
	
	public Entity(ArrayList<Tetrahedron> tetras) {
		this.tetrahedrons = tetras;
		this.center = new MyPoint(0, 0, 0);
		int polygonCount = 0;
		ArrayList<MyPolygon> tempList = new ArrayList<MyPolygon>();
		for(Tetrahedron t : tetras) {
			center.x += t.center.x;
			center.y += t.center.y;
			center.z += t.center.z;
			polygonCount += t.getPolygons().length;
			for(MyPolygon poly : t.getPolygons()) {
				tempList.add(poly);
			}
		}
		center.x /= tetras.size();
		center.y /= tetras.size();
		center.z /= tetras.size();
		
		polygons = new MyPolygon[polygonCount];
		int i = 0;
		for(MyPolygon poly : tempList) {
			polygons[i++] = poly;
		}
		this.sortPolygons();
	}

	@Override
	public void render(Graphics g) {
		this.sortPolygons();
		for(MyPolygon p : this.polygons) {
			p.render(g);
		}
	}

	@Override
	public void rotate(boolean CW, double degreesX, double degreesY, double degreesZ, MyVector lightVector) {
		for(Tetrahedron t : this.tetrahedrons) {
			t.rotateAround(CW, this.center, degreesX, degreesY, degreesZ);
			t.setLighting(lightVector);
		}
	}

	@Override
	public void move(double moveX, double moveY, double moveZ) {
		for(Tetrahedron t : this.tetrahedrons) {
			t.move(moveX, moveY, moveZ);
		}
	}
	
	public void sortPolygons() {
		MyPolygon.sortPolygons(this.polygons);
	}
}

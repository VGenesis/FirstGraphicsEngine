package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;

import renderer.entity.Entity;
import renderer.entity.IEntity;
import renderer.point.MyPoint;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class ComplexEntityBuilder {
	public static IEntity createRubiksCube(double size, double centerX, double centerY, double centerZ) {
		ArrayList<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
		
		double cubeSpacing = 5;
		
		for(int i = -1; i <= 1 ; i++) {
			double posX = centerX + i * (size + cubeSpacing);
			for(int j = -1; j <= 1; j++) {
				double posY = centerY + j * (size + cubeSpacing);
				for(int k = -1; k <= 1; k++) {
					double posZ = centerZ + k * (size + cubeSpacing);

					MyPoint p1 = new MyPoint(posX - size / 2, posY - size / 2, posZ - size / 2);
					MyPoint p2 = new MyPoint(posX - size / 2, posY - size / 2, posZ + size / 2);
					MyPoint p3 = new MyPoint(posX - size / 2, posY + size / 2, posZ - size / 2);
					MyPoint p4 = new MyPoint(posX - size / 2, posY + size / 2, posZ + size / 2);
					MyPoint p5 = new MyPoint(posX + size / 2, posY - size / 2, posZ - size / 2);
					MyPoint p6 = new MyPoint(posX + size / 2, posY - size / 2, posZ + size / 2);
					MyPoint p7 = new MyPoint(posX + size / 2, posY + size / 2, posZ - size / 2);
					MyPoint p8 = new MyPoint(posX + size / 2, posY + size / 2, posZ + size / 2);

					MyPolygon[] polygons = new MyPolygon[6];
					polygons[0] = new MyPolygon(Color.orange, p1, p3, p4, p2);
					polygons[1] = new MyPolygon(Color.red, p5, p6, p8, p7);
					polygons[2] = new MyPolygon(Color.blue, p1, p2, p6, p5);
					polygons[3] = new MyPolygon(Color.green, p3, p7, p8, p4);
					polygons[4] = new MyPolygon(Color.white, p1, p5, p7, p3);
					polygons[5] = new MyPolygon(Color.yellow, p2, p4, p8, p6);
					
					Tetrahedron tetra = new Tetrahedron(null, polygons);
					tetras.add(tetra);
				}
			}
		}
		return new Entity(tetras);
	}
}

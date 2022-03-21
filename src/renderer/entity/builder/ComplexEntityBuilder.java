package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;

import renderer.entity.Entity;
import renderer.entity.IEntity;
import renderer.point.Point3D;
import renderer.shapes.Polygon3D;
import renderer.shapes.Polyhedron;

public class ComplexEntityBuilder {
	public static IEntity createRubiksCube(double size, double centerX, double centerY, double centerZ) {
		ArrayList<Polyhedron> tetras = new ArrayList<Polyhedron>();
		
		double cubeSpacing = 5;
		
		for(int i = -1; i <= 1 ; i++) {
			double posX = centerX + i * (size + cubeSpacing);
			for(int j = -1; j <= 1; j++) {
				double posY = centerY + j * (size + cubeSpacing);
				for(int k = -1; k <= 1; k++) {
					double posZ = centerZ + k * (size + cubeSpacing);

					Point3D p1 = new Point3D(posX - size / 2, posY - size / 2, posZ - size / 2);
					Point3D p2 = new Point3D(posX - size / 2, posY - size / 2, posZ + size / 2);
					Point3D p3 = new Point3D(posX - size / 2, posY + size / 2, posZ - size / 2);
					Point3D p4 = new Point3D(posX - size / 2, posY + size / 2, posZ + size / 2);
					Point3D p5 = new Point3D(posX + size / 2, posY - size / 2, posZ - size / 2);
					Point3D p6 = new Point3D(posX + size / 2, posY - size / 2, posZ + size / 2);
					Point3D p7 = new Point3D(posX + size / 2, posY + size / 2, posZ - size / 2);
					Point3D p8 = new Point3D(posX + size / 2, posY + size / 2, posZ + size / 2);

					Polygon3D[] polygons = new Polygon3D[6];
					polygons[0] = new Polygon3D(Color.orange, p1, p3, p4, p2);
					polygons[1] = new Polygon3D(Color.red, p5, p6, p8, p7);
					polygons[2] = new Polygon3D(Color.blue, p1, p2, p6, p5);
					polygons[3] = new Polygon3D(Color.green, p3, p7, p8, p4);
					polygons[4] = new Polygon3D(Color.white, p1, p5, p7, p3);
					polygons[5] = new Polygon3D(Color.yellow, p2, p4, p8, p6);
					
					Polyhedron tetra = new Polyhedron(null, polygons);
					tetras.add(tetra);
				}
			}
		}
		return new Entity(tetras);
	}
}

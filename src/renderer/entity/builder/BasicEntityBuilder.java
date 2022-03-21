package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;

import renderer.entity.Entity;
import renderer.entity.IEntity;
import renderer.point.Point3D;
import renderer.shapes.Polygon3D;
import renderer.shapes.Polyhedron;

public class BasicEntityBuilder {
	public static IEntity createCube(Color color, double size, double centerX, double centerY, double centerZ) {
		ArrayList<Polyhedron> tetras = new ArrayList<Polyhedron>();
		
		Point3D p1 = new Point3D(centerX - size / 2, centerY - size / 2, centerZ - size / 2);
		Point3D p2 = new Point3D(centerX - size / 2, centerY - size / 2, centerZ + size / 2);
		Point3D p3 = new Point3D(centerX - size / 2, centerY + size / 2, centerZ - size / 2);
		Point3D p4 = new Point3D(centerX - size / 2, centerY + size / 2, centerZ + size / 2);
		Point3D p5 = new Point3D(centerX + size / 2, centerY - size / 2, centerZ - size / 2);
		Point3D p6 = new Point3D(centerX + size / 2, centerY - size / 2, centerZ + size / 2);
		Point3D p7 = new Point3D(centerX + size / 2, centerY + size / 2, centerZ - size / 2);
		Point3D p8 = new Point3D(centerX + size / 2, centerY + size / 2, centerZ + size / 2);
		
		Polygon3D[] polygons = new Polygon3D[6];
		polygons[0] = new Polygon3D(color, p1, p2, p4, p3);
		polygons[1] = new Polygon3D(color, p5, p6, p8, p7);
		polygons[2] = new Polygon3D(color, p1, p2, p6, p5);
		polygons[3] = new Polygon3D(color, p3, p4, p8, p7);
		polygons[4] = new Polygon3D(color, p1, p3, p7, p5);
		polygons[5] = new Polygon3D(color, p2, p4, p8, p6);
		
		Polyhedron tetra = new Polyhedron(color, polygons);
		tetras.add(tetra);
		return new Entity(tetras);
	}
	
	public static IEntity createDiamond(Color color, double radius, double height, int edges, double infactor, double centerX, double centerY, double centerZ) {
		ArrayList<Polyhedron> tetras = new ArrayList<Polyhedron>();
		
		Point3D bottom = new Point3D(centerX, centerY, centerZ -height / 2);
		Point3D[] outPoints = new Point3D[edges];
		Point3D[] inPoints = new Point3D[edges];
		for(int i = 0; i < edges; i++) {
			double theta  = 2 * Math.PI / edges * i;
			double xPos = -radius * Math.sin(theta);
			double yPos = radius * Math.cos(theta);
			double zPos = height / 2;
			outPoints[i] = new Point3D(centerX + xPos, centerY + yPos, centerZ + zPos * infactor);
			inPoints[i] = new Point3D(centerX + xPos * infactor, centerY + yPos * infactor, centerZ + zPos);
		}
		
		Polygon3D[] polygons = new Polygon3D[2 * edges + 1];
		for(int i = 0; i < edges; i++) {
			Point3D[] crownSidePoints = {outPoints[i], inPoints[i], inPoints[(i + 1) % edges], outPoints[(i + 1) % edges]};
			Point3D[] sidePoints = {bottom, outPoints[i], outPoints[(i + 1) % edges]};
			polygons[i] = new Polygon3D(crownSidePoints);
			polygons[i + edges] = new Polygon3D(sidePoints);
		}
		polygons[2 * edges] = new Polygon3D(inPoints);
		
		Polyhedron tetra = new Polyhedron(color, polygons);
		tetras.add(tetra);
		return new Entity(tetras);
	}
	
	public static IEntity createSphere(Color color, double radius, int equatorEdges, int meridianEdges, double centerX, double centerY, double centerZ) {
		ArrayList<Polyhedron> tetras = new ArrayList<Polyhedron>();
		
		Point3D bottom = new Point3D(centerX, centerY, centerZ - radius);
		Point3D top = new Point3D(centerX, centerY, centerZ + radius);
		Point3D[][] points = new Point3D[equatorEdges][meridianEdges];
		for(int i = 0; i < meridianEdges; i++) {
			double thetaZ = 3/2 * Math.PI + Math.PI / meridianEdges * i;
			double zPos = centerZ + radius * Math.cos(thetaZ);
			for(int j = 0; j < equatorEdges; j++) {
				double thetaRad = 2 * Math.PI / equatorEdges * j;
				double xPos = centerX + radius * Math.cos(thetaRad) * Math.sin(thetaZ);
				double yPos = centerY +radius * Math.sin(thetaRad) * Math.sin(thetaZ);
				points[j][i] = new Point3D(xPos, yPos, zPos);
			}
		}
		
		ArrayList<Polygon3D> polys = new ArrayList<Polygon3D>();
		for(int i = 0; i < equatorEdges; i++) {
			polys.add(new Polygon3D(bottom, points[i][0], points[(i + 1) % equatorEdges][0]));
			for(int j = 0; j < meridianEdges - 1; j++) {
				polys.add(new Polygon3D(points[i][j], 
										points[(i + 1) % equatorEdges][j],
										points[(i + 1) % equatorEdges][j + 1],
										points[i][j + 1]));
			}
			polys.add(new Polygon3D(top, points[i][meridianEdges - 1], points[(i + 1) % equatorEdges][meridianEdges - 1]));
		}
		Polygon3D[] polygons = new Polygon3D[polys.size()];
		polys.toArray(polygons);
		Polyhedron tetra = new Polyhedron(color, polygons);
		tetras.add(tetra);
		return new Entity(tetras);
	}
	
}

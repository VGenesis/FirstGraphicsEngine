package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;

import renderer.entity.Entity;
import renderer.entity.IEntity;
import renderer.point.MyPoint;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class BasicEntityBuilder {
	public static IEntity createCube(Color color, double size, double centerX, double centerY, double centerZ) {
		ArrayList<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
		
		MyPoint p1 = new MyPoint(centerX - size / 2, centerY - size / 2, centerZ - size / 2);
		MyPoint p2 = new MyPoint(centerX - size / 2, centerY - size / 2, centerZ + size / 2);
		MyPoint p3 = new MyPoint(centerX - size / 2, centerY + size / 2, centerZ - size / 2);
		MyPoint p4 = new MyPoint(centerX - size / 2, centerY + size / 2, centerZ + size / 2);
		MyPoint p5 = new MyPoint(centerX + size / 2, centerY - size / 2, centerZ - size / 2);
		MyPoint p6 = new MyPoint(centerX + size / 2, centerY - size / 2, centerZ + size / 2);
		MyPoint p7 = new MyPoint(centerX + size / 2, centerY + size / 2, centerZ - size / 2);
		MyPoint p8 = new MyPoint(centerX + size / 2, centerY + size / 2, centerZ + size / 2);
		
		MyPolygon[] polygons = new MyPolygon[6];
		polygons[0] = new MyPolygon(color, p1, p2, p4, p3);
		polygons[1] = new MyPolygon(color, p5, p6, p8, p7);
		polygons[2] = new MyPolygon(color, p1, p2, p6, p5);
		polygons[3] = new MyPolygon(color, p3, p4, p8, p7);
		polygons[4] = new MyPolygon(color, p1, p3, p7, p5);
		polygons[5] = new MyPolygon(color, p2, p4, p8, p6);
		
		Tetrahedron tetra = new Tetrahedron(color, polygons);
		tetras.add(tetra);
		return new Entity(tetras);
	}
	
	public static IEntity createDiamond(Color color, double radius, double height, int edges, double infactor, double centerX, double centerY, double centerZ) {
		ArrayList<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
		
		MyPoint bottom = new MyPoint(centerX, centerY, centerZ -height / 2);
		MyPoint[] outPoints = new MyPoint[edges];
		MyPoint[] inPoints = new MyPoint[edges];
		for(int i = 0; i < edges; i++) {
			double theta  = 2 * Math.PI / edges * i;
			double xPos = -radius * Math.sin(theta);
			double yPos = radius * Math.cos(theta);
			double zPos = height / 2;
			outPoints[i] = new MyPoint(centerX + xPos, centerY + yPos, centerZ + zPos * infactor);
			inPoints[i] = new MyPoint(centerX + xPos * infactor, centerY + yPos * infactor, centerZ + zPos);
		}
		
		MyPolygon[] polygons = new MyPolygon[2 * edges + 1];
		for(int i = 0; i < edges; i++) {
			MyPoint[] crownSidePoints = {outPoints[i], inPoints[i], inPoints[(i + 1) % edges], outPoints[(i + 1) % edges]};
			MyPoint[] sidePoints = {bottom, outPoints[i], outPoints[(i + 1) % edges]};
			polygons[i] = new MyPolygon(crownSidePoints);
			polygons[i + edges] = new MyPolygon(sidePoints);
		}
		polygons[2 * edges] = new MyPolygon(inPoints);
		
		Tetrahedron tetra = new Tetrahedron(color, polygons);
		tetras.add(tetra);
		return new Entity(tetras);
	}
	
	public static IEntity createSphere(Color color, double radius, int equatorEdges, int meridianEdges, double centerX, double centerY, double centerZ) {
		ArrayList<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
		
		MyPoint bottom = new MyPoint(centerX, centerY, centerZ - radius);
		MyPoint top = new MyPoint(centerX, centerY, centerZ + radius);
		MyPoint[][] points = new MyPoint[equatorEdges][meridianEdges];
		for(int i = 0; i < meridianEdges; i++) {
			double thetaZ = 3/2 * Math.PI + Math.PI / meridianEdges * i;
			double zPos = centerZ + radius * Math.cos(thetaZ);
			for(int j = 0; j < equatorEdges; j++) {
				double thetaRad = 2 * Math.PI / equatorEdges * j;
				double xPos = centerX + radius * Math.cos(thetaRad) * Math.sin(thetaZ);
				double yPos = centerY +radius * Math.sin(thetaRad) * Math.sin(thetaZ);
				points[j][i] = new MyPoint(xPos, yPos, zPos);
			}
		}
		
		ArrayList<MyPolygon> polys = new ArrayList<MyPolygon>();
		for(int i = 0; i < equatorEdges; i++) {
			polys.add(new MyPolygon(bottom, points[i][0], points[(i + 1) % equatorEdges][0]));
			for(int j = 0; j < meridianEdges - 1; j++) {
				polys.add(new MyPolygon(points[i][j], 
										points[(i + 1) % equatorEdges][j],
										points[(i + 1) % equatorEdges][j + 1],
										points[i][j + 1]));
			}
			polys.add(new MyPolygon(top, points[i][meridianEdges - 1], points[(i + 1) % equatorEdges][meridianEdges - 1]));
		}
		MyPolygon[] polygons = new MyPolygon[polys.size()];
		polys.toArray(polygons);
		Tetrahedron tetra = new Tetrahedron(color, polygons);
		tetras.add(tetra);
		return new Entity(tetras);
	}
	
}

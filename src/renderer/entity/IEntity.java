package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import renderer.point.Vector3D;
import renderer.shapes.Polyhedron;

public interface IEntity {
	
	ArrayList<Polyhedron> getMesh();
	
	void render(Graphics g);

	void translate(double moveX, double moveY, double moveZ);
	
	void rotate(boolean CW, double degreesX, double degreesY, double degreesZ);
	
	void scale(double scale);
	
	void setLighting(Vector3D lightVector);
	
	void shift();
	
}

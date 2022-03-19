package renderer.entity;

import java.awt.Graphics;

import renderer.point.MyVector;

public interface IEntity {
	void render(Graphics g);
	
	void move(double moveX, double moveY, double moveZ);
	
	void rotate(boolean CW, double degreesX, double degreesY, double degreesZ, MyVector lightVector);
	
}

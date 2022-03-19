package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.entity.builder.ComplexEntityBuilder;
import renderer.point.MyVector;

public class EntityManager {
	private ArrayList<IEntity> entities;
	private MyVector lightVector;
	
	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
		this.lightVector = MyVector.normalize(new MyVector(1, 0.3, 0.5));
	}
	
	public void init() {
//		this.entities.add(ComplexEntityBuilder.createRubiksCube(70, 0, 0, 0));
		this.entities.add(BasicEntityBuilder.createDiamond(Color.CYAN, 100, 125, 16, 0.65, 0, 0, 0));
//		this.entities.add(BasicEntityBuilder.createCube(Color.red, 100, 0, 0, 0));
//		this.entities.add(BasicEntityBuilder.createSphere(new Color(192, 192, 192), 175, 25, 15, 0, 0, 0));
		
		for(IEntity e : this.entities) {
			e.rotate(true, 0, 0, 0, lightVector);
		}
	}
	
	public void render(Graphics g) {
		for(IEntity e : this.entities) {
			e.render(g);
		}
	}
	
	public void move(double moveX, double moveY, double moveZ) {
		for(IEntity e : this.entities) {
			e.move(moveX, moveY, moveZ);
		}
	}
	
	public void rotate(boolean CW, double degreesX, double degreesY, double degreesZ) {
		for(IEntity e : this.entities) {
			e.rotate(CW, degreesX, degreesY, degreesZ, this.lightVector);
		}
	}
	
	public void setLighting() {
		for(IEntity e : this.entities) {
			e.rotate(true, 0, 0, 0, lightVector);
		}
	}
	
}

package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import renderer.Display;
import renderer.entity.builder.*;
import renderer.input.InputManager;
import renderer.input.MouseMap;
import renderer.point.Vector3D;
import renderer.shapes.Polyhedron;
import renderer.point.PointConverter;
import renderer.world.Camera;

public class EntityManager {
	private ArrayList<IEntity> entities;
	private Vector3D lightVector;
	private Camera camera;
	
	private final double moveSpeed = 10;

	private boolean pulsing;
	private double pulseRatio;
	
	private boolean deform;
	
	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
		this.lightVector = Vector3D.normalize(new Vector3D(1, 0, 0));
		this.camera = new Camera(0, 0, 0);
		this.pulsing = true;
		this.pulseRatio = 1;
		this.deform = false;
	}
	
	public void init() {
//		this.cubeTest(125, 50);
		this.sphereTest();
		this.setLighting();
	}
	
	public void render(Graphics g) {
		for(IEntity e : this.entities) {
			e.render(g);
		}
	}
	
	public void translate(double moveX, double moveY, double moveZ) {
		for(IEntity e : this.entities) {
			e.translate(moveX, moveY, moveZ);
			e.setLighting(lightVector);
		}
	}
	
	public void rotate(boolean CW, double degreesX, double degreesY, double degreesZ) {
		for(IEntity e : this.entities) {
			e.rotate(CW, degreesX, degreesY, degreesZ);
			e.setLighting(lightVector);
		}
	}
	
	public void scale(double scale) {
		for(IEntity e : this.entities) {
			e.scale(scale);
		}
	}
	
	public void shift() {
		for(IEntity e : this.entities) {
			e.shift();
		}
	}
	
	public void setLighting() {
		for(IEntity e : this.entities) {
			e.setLighting(lightVector);
		}
	}
	
	private int mouseInitialX, mouseInitialY;
	private double localScale = 0;
	public void update(InputManager inputManager) {
		ArrayList<MouseMap> mouseInput = inputManager.mouse.getButtons();
		
		if(mouseInput.indexOf(MouseMap.LCLICK) != -1) {
			double mouseDeltaX = mouseInitialX - inputManager.mouse.getX();
			double mouseDeltaY = mouseInitialY - inputManager.mouse.getY();
			double sensitivity = inputManager.mouse.getMoveSensitivity();
			this.rotate(true, 0, mouseDeltaY * sensitivity, mouseDeltaX * sensitivity);
		}

		int mouseScroll = inputManager.mouse.getScroll();
		if(mouseScroll != 0) {
			if(mouseScroll < 0) PointConverter.zoomIn();
			else PointConverter.ZoomOut();
		}
		
		inputManager.keyboard.update();
		int cameraMoveX = ((inputManager.keyboard.getForward())? 1 : 0) - ((inputManager.keyboard.getBackward())? 1 : 0);
		int cameraMoveY = ((inputManager.keyboard.getRight())? 1 : 0) - ((inputManager.keyboard.getLeft())? 1 : 0);
		int cameraMoveZ = ((inputManager.keyboard.getUp())? 1 : 0) - ((inputManager.keyboard.getDown())? 1 : 0);
		if(cameraMoveX + cameraMoveY + cameraMoveZ != 0) {
			Vector3D direction = Vector3D.normalize(new Vector3D(cameraMoveX, cameraMoveY, cameraMoveZ));
			camera.translate(moveSpeed * direction.x, moveSpeed * direction.y, moveSpeed * direction.z);
			this.translate(moveSpeed * direction.x, moveSpeed * direction.y, moveSpeed * direction.z);
		}
		
		if(mouseInput.indexOf(MouseMap.RCLICK) != -1) this.pulsing = false;
		else this.pulsing = true;
		
		if(this.pulsing) {
			this.localScale += Math.PI * this.pulseRatio / Display.UPDATES_PER_SECOND;
			this.scale(Math.cos(this.localScale));
			if(deform) this.shift();
		}
		
		this.mouseInitialX = inputManager.mouse.getX();
		this.mouseInitialY = inputManager.mouse.getY();
		inputManager.mouse.resetScroll();
	}
	
	private void cubeTest(int cubeCount, int cubeSize) {
		Random random = new Random();
		ArrayList<Polyhedron> cubes = new ArrayList<Polyhedron>();
		for(int i = 0; i < cubeCount; i++) {
			double x =  -(100 + random.nextInt(cubeCount * cubeSize / 30));
			double y = (random.nextBoolean()? 1 : -1) * random.nextInt(cubeCount * cubeSize / 60);
			double z = (random.nextBoolean()? 1 : -1) * random.nextInt(cubeCount * cubeSize / 60);
			IEntity cube = BasicEntityBuilder.createCube(new Color(0, 144, 255), cubeSize, x, y, z);
			cube.rotate(true, random.nextDouble(), random.nextDouble(), random.nextDouble());
			cubes.addAll(cube.getMesh());
		}
		this.entities.add(new Entity(cubes));
		this.deform = false;
		this.pulseRatio = 0.5;
	}
	
	private void sphereTest() {
		this.entities.add(BasicEntityBuilder.createSphere(new Color(0, 0, 255), 90, 25, 20, -1000, 0, 0));
		this.entities.add(BasicEntityBuilder.createSphere(new Color(64, 0, 192), 105, 25, 20, -1000, 0, 0));
		this.entities.add(BasicEntityBuilder.createSphere(new Color(128, 0, 128), 120, 25, 20, -1000, 0, 0));
		this.entities.add(BasicEntityBuilder.createSphere(new Color(192, 0, 64), 135, 25, 20, -1000, 0, 0));
		this.entities.add(BasicEntityBuilder.createSphere(new Color(255, 0, 0), 150, 25, 20, -1000, 0, 0));
		this.deform = true;
		this.pulseRatio = 0.7;
	}
}

package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.entity.EntityManager;
import renderer.input.Mouse;
import renderer.input.MouseMap;
import renderer.point.MyVector;
import renderer.point.PointConverter;

public class Display extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title = "3D Renderer";
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT= 600;
	private static boolean running = false;
	private static final int UPDATES_PER_SECOND = 60;
	public static double render_distance = 100;

	private EntityManager entityManager;
	
	private Mouse mouse;
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setPreferredSize(size);
		
		this.mouse = new Mouse();
		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addMouseWheelListener(this.mouse);
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setLocationRelativeTo(null);
		display.frame.setResizable(false);
		display.frame.setVisible(true);
		display.start();
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}
	
	public synchronized void stop() {
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		PointConverter.setPerspectiveScale(1280);
		
		this.entityManager = new EntityManager();
		this.entityManager.init();
		this.mouse.setMoveSensitivity(0.5);
		this.mouse.setScrollSensitivity(50);
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1e9 / UPDATES_PER_SECOND;
		double delta = 0;
		int frames = 0;
		
		init();
		
		while(running) {
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime) / ns;
			lastTime = nowTime;
			while(delta >= 1.0) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " | " + frames + "fps");
				frames = 0;
			}
		}
		
		stop();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		this.entityManager.render(g);
		
		g.dispose();
		bs.show();
	}
	
	private int mouseInitialX, mouseInitialY;
	private void update() {
		if(mouse.getButton() == MouseMap.LCLICK) {
			double mouseDeltaX = mouseInitialX - mouse.getX();
			double mouseDeltaY = mouseInitialY - mouse.getY();
			double sensitivity = mouse.getMoveSensitivity();
			this.entityManager.rotate(true, 0, mouseDeltaY * sensitivity, mouseDeltaX * sensitivity);
		}
		
		if(mouse.getButton() == MouseMap.MCLICK) {
			double mouseDeltaX = mouseInitialX - mouse.getX();
			double mouseDeltaY = mouseInitialY - mouse.getY();
			this.entityManager.move(0, -mouseDeltaX, mouseDeltaY);
		}

		int mouseScroll = mouse.getScroll();
		if(mouseScroll != 0) {
			if(mouseScroll < 0) PointConverter.zoomIn();
			else PointConverter.ZoomOut();
		}
		
		mouseInitialX = mouse.getX();
		mouseInitialY = mouse.getY();
		mouse.resetScroll();
	}
	
}

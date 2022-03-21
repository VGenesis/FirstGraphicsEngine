package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.entity.EntityManager;
import renderer.input.InputManager;
import renderer.point.PointConverter;

public class Display extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title = "3D Renderer";
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT= 600;
	private static boolean running = false;
	public static final int UPDATES_PER_SECOND = 60;
	public static double render_distance = 10;

	private EntityManager entityManager;
	private InputManager inputManager;
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setPreferredSize(size);
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
		
		this.inputManager = new InputManager();
		this.entityManager = new EntityManager();
		this.entityManager.init();
		this.addMouseListener(this.inputManager.mouse);
		this.addMouseMotionListener(this.inputManager.mouse);
		this.addMouseWheelListener(this.inputManager.mouse);
		this.addKeyListener(this.inputManager.keyboard);
		this.inputManager.mouse.setMoveSensitivity(0.5);
		this.inputManager.mouse.setScrollSensitivity(50);
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
		
		String text1 = "Left Click to rotate";
		String text2 = "Right click to stop pulse";
		String text3 = "Arrow Keys / WASD to move";
		String text4 = "SHIFT/SPACE to elevate";
		
		
		g.setColor(Color.white);
		g.setFont(new Font("Plain", Font.PLAIN, 13));
		g.drawChars(text1.toCharArray(), 0, text1.length(), 8, 16);
		g.drawChars(text2.toCharArray(), 0, text2.length(), 8, 32);
		g.drawChars(text3.toCharArray(), 0, text3.length(), 8, 48);
		g.drawChars(text4.toCharArray(), 0, text4.length(), 8, 64);
		
		g.dispose();
		bs.show();
	}
	
	private void update() {
		this.entityManager.update(inputManager);
	}
	
}

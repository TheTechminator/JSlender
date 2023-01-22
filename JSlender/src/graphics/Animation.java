package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Animációk kezelésére szolgáló osztály.
 * @author Terminator
 *
 */
public abstract class Animation {
	
	private BufferedImage image = null;
	private long millis = 0;
	private long startTime = 0;
	private long lastTime = 0;
	private long currentTime = 0;
	private int zoom = 0;
	
	private int FPS = 60;

	public Animation(BufferedImage image, long millis) {
		this.image = image;
		this.millis = millis;
	}
	
	public void startAnimation () {
		startTime = System.currentTimeMillis();
		currentTime = startTime;
		zoom = 0;
	}
	
	public abstract void animate (Graphics2D g);
	
	public void animateZoomIn (Graphics2D g) {
		if(isWorking()) {
			lastTime = currentTime;
			currentTime = System.currentTimeMillis();
			
			g.drawImage(image, 275-zoom, 275-zoom, 50+zoom*2, 50+zoom*2, null);
			zoom+=(int)(5*getKeyFrame());
		}
	}
	
	public float getKeyFrame () {
		return (((float)(currentTime-lastTime)) / ((float)(1000f/(float)FPS)));
	}
	
	public boolean isWorking () {
		return (startTime+millis >= System.currentTimeMillis()) ? true : false;
	}

}

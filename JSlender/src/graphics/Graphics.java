package graphics;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import obstacle.*;

/**
 * Kezeli a játék grafikáját. Lehetõvé teszi, hogy szépen megjelenjen mindne ahogy kell.
 * @author Terminator
 *
 */
public class Graphics implements Runnable {
	//Ez az osztály intézi, hogy minden szépen megjelenjen a kijelzõn grafikusan
    private Display Display;
    private Thread t;
    private boolean running;
    
    public int width, height;
    private int screenWidth, screenHeight;
    
    private String title;
    private BufferStrategy bs;
    private Graphics2D g;
    
    private BufferedImage grass;
    private int oneBlockSize;
    
    private ArrayList<Renderable> renderables = new ArrayList<>();
    private Canvas canvas;
    private long FPS = 60;
    
    private JFrameKeyListener listener;
    
    private static int winLose = 0; //0: nincs, 1: lose, 2: win
    
    private static ArrayList<Animation> animQueue;
    
    private BufferedImage imgWin, imgLose;

    public Graphics(String title, int width, int height, int oneBlockSize) {
    	animQueue = new ArrayList<Animation>();
    	this.title = title;
        this.width = width;
        this.height = height;
        this.oneBlockSize = oneBlockSize;
        
        this.screenWidth = width*oneBlockSize;
        this.screenHeight = height*oneBlockSize;
        imgWin = ImageLoader.loadImage("images/nyert.png");
        imgLose = ImageLoader.loadImage("images/vesztett.png");
    }
    
    public void addRenderables (ArrayList<Renderable> renderables, Renderable player, Renderable slenderman) {
    	this.renderables = renderables;
    	this.renderables.add(player);
    	this.renderables.add(slenderman);
    }

    @Override
    public void run() {
        init();
        
        canvas = Display.getCanvas();
        
        canvas.createBufferStrategy(3);
        bs = canvas.getBufferStrategy();
        
        while (running) {
            tick();
            render();
        }
    }

    private void render() {
    	long time = System.currentTimeMillis();
        bs = canvas.getBufferStrategy();
        g = (Graphics2D)bs.getDrawGraphics();
        g.clearRect(0, 0, screenWidth, screenHeight);
        //g.drawImage(grass, 20, 20, null);
        
        for(int i = 0; i<width; i++) {
        	for(int k = 0; k<height; k++) {
        		g.drawImage(grass, k*oneBlockSize, screenHeight-i*oneBlockSize-oneBlockSize, oneBlockSize, oneBlockSize, null);
        	}
        }
        
        for(int i = 0; i<renderables.size(); i++) {
        	renderables.get(i).render(g);
        }
        
        animate(g);
        drawWinLose(g);
        
        bs.show();
        g.dispose();
        
        try {
        	if(System.currentTimeMillis()-time < (1000/FPS)) {
        		Thread.sleep((1000/FPS)-(System.currentTimeMillis()-time));
        	}
        } catch (InterruptedException e) {
        	
        }
    }
    
    private void animate (Graphics2D g) {
    	for(int i = 0; i<animQueue.size(); i++) {
    		if(animQueue.get(i).isWorking()) {
    			animQueue.get(i).animate(g);
    		} else {
    			animQueue.remove(i);
    		}
    	}
    }
    
    public static void addToAnimQueue (Animation anim) {
    	if(animQueue != null) {
    		anim.startAnimation();
    		animQueue.add(anim);
    	}
    }

    private void tick() { }
    
    public void addKeyListener (JFrameKeyListener listener) {
    	this.listener = listener;
    }
    
    private void init() {
        Display = new Display(title, screenWidth, screenHeight);
        grass = ImageLoader.loadImage("images/gyep.png");
        Display.addKeyListener(listener);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        t = new Thread(this);
        t.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            t.join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
    
    public static void playerLose () {
    	winLose = 1;
    }
    
    private void drawWinLose (Graphics2D g) {
    	if(winLose == 1) { //lose
    		g.clearRect(0, 0, screenWidth, screenHeight);
    		g.drawImage(imgLose, 0, 0, screenWidth, screenHeight, null);
    	} else if (winLose == 2) { //win
    		g.clearRect(0, 0, screenWidth, screenHeight);
    		g.drawImage(imgWin, 0, 0, screenWidth, screenHeight, null);
    	}
    }
    
    public static void playerWin () {
    	winLose = 2;
    }
}
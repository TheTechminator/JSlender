package obstacle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import graphics.ImageLoader;

/**
 * Egy speciális osztály amely a garfikus megjelenítés-t szólgálja. Minden entitás és tereptárgy amely megjeleníthetõ grafikusan ebböl az osztálybõl származik.
 * Tulajdonképp ez az osztály kapcsolatot teremt a grafika és a program között.
 * @author Terminator
 *
 */
public class Renderable {
	//Az adott tereptárgyat grafikusan megjeleníthetõ formában írja le
	
	private int posX, posY; //A tárgy bal alsó sarkának a kordinátája
	private int width, height; //A tárgy szélessége, magassága
	private int rotation; //A tárgy forgatása (tetszõleges egész szám * 90-nel) (0: eredeti állapot)
	
	private String[] textures = {
		"jatekos.png",
		"kfa.png",
		"nfa.png",
		"haz.png",
		"auto.png",
		"teher.png",
		"szikla.png",
		"hordo.png",
		"slender.png"
	};
	
	private BufferedImage image;
	private int oneBlockSize = 40;
	
	public Renderable() {
		super();
	}
	
	public void addObstacle (int posX, int posY, int width, int height, int rotation) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.posY = 14-posY-height+1;
	}
	
	public void loadImage (int type) {
		image = ImageLoader.loadImage("images/"+textures[type]);
		image = ImageLoader.rotateImageByDegrees(image, rotation);
	}
	
	public void changePosition (int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.posY = 14-posY-height+1;
	}
	
	public void render (Graphics2D g) {
		g.drawImage(image, posX*oneBlockSize, posY*oneBlockSize, width*oneBlockSize, height*oneBlockSize, null);
	}
}

package obstacle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import graphics.ImageLoader;

/**
 * Egy speci�lis oszt�ly amely a garfikus megjelen�t�s-t sz�lg�lja. Minden entit�s �s terept�rgy amely megjelen�thet� grafikusan ebb�l az oszt�lyb�l sz�rmazik.
 * Tulajdonk�pp ez az oszt�ly kapcsolatot teremt a grafika �s a program k�z�tt.
 * @author Terminator
 *
 */
public class Renderable {
	//Az adott terept�rgyat grafikusan megjelen�thet� form�ban �rja le
	
	private int posX, posY; //A t�rgy bal als� sark�nak a kordin�t�ja
	private int width, height; //A t�rgy sz�less�ge, magass�ga
	private int rotation; //A t�rgy forgat�sa (tetsz�leges eg�sz sz�m * 90-nel) (0: eredeti �llapot)
	
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

package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.Animation;
import graphics.Graphics;
import graphics.ImageLoader;
import map.Map;
import obstacle.Renderable;

/**
 * Egy játékost ír le amely mozgatható a megadott pályán.
 * @author Terminator
 *
 */
public class Player extends Renderable{
	
	private int posX, posY; //x, y koordinátája
	private int paperCount; //a papírok darabszáma
	private boolean alive; //él e még
	private Map map; //a pálya
	private Font font; //betûtipus (így írjuk ki hány papírja van)
	private Slenderman slenderman; //A veszedelmes szörnyeteg
	
	private Animation animation;
	
	/**
	*Új játékos létrehozása.
	*@param startX a játékos kezdõ x koordinátája
	*@param startY a játékos kezdõ y koordinátája
	*@param map a már elkészített pálya térképét várja paraméterben
	*@param slenderman egy slendermant vár paraméterben
	*/
	public Player(int startX, int startY, Map map, Slenderman slenderman) {
		posX = startX;
		posY = startY;
		alive = true;
		paperCount = 0;
		this.map = map;
		this.slenderman = slenderman;
		font = new Font("Times New Roman", Font.PLAIN, 24);
		this.addObstacle(this.posX, this.posY, 1, 1, 0);
		this.loadImage(0);
		
		animation = new Animation(ImageLoader.loadImage("images/papir.png"), 300) {
			@Override
			public void animate(Graphics2D g) {
				this.animateZoomIn(g);
			}
		};
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	/**
	* Ez a metódus a játékost mozgatja fel irányba.<br>
	* A játékos csak akkor fog ebbe az irányba ténylegesen mozdulni, ha ott nincs akadály.
	*/
	public void goNorth () {
		goToIfCan(0, 1);
	}
	
	/**
	* Ez a metódus a játékost mozgatja le irányba.<br>
	* A játékos csak akkor fog ebbe az irányba ténylegesen mozdulni, ha ott nincs akadály.
	*/
	public void goSouth () {
		goToIfCan( 0, -1);
	}
	
	/**
	* Ez a metódus a játékost mozgatja ballra irányba.<br>
	* A játékos csak akkor fog ebbe az irányba ténylegesen mozdulni, ha ott nincs akadály.
	*/
	public void goWest () {
		goToIfCan(-1, 0);
	}
	
	/**
	* Ez a metódus a játékost mozgatja jobbra irányba.<br>
	* A játékos csak akkor fog ebbe az irányba ténylegesen mozdulni, ha ott nincs akadály.
	*/
	public void goEast () {
		goToIfCan(1, 0);
	}
	
	@Override
	public void render(Graphics2D g) {
		//Ki rajzolja a játékost és kiírja hány papírja van
		super.render(g);
		g.setFont(font);
		g.setColor(new Color(255,255,255,64));
        g.fillRect(0, 0, 200, 50);
        g.setColor(new Color(0,0,0,255));
		g.drawString("PAPÍROK: "+paperCount+" db", 20, 30);
	}
	
	/**
	* Ez a metódus a játkost próbálja a megadott helyre léptettni ha tudja. Tehát ha nincs ott akadály.
	* @param dX amennyivel akarja az x koordinátát módosítani
	* @param dY amennyivel az y koordinátát akarja módosítani
	*/
	private void goToIfCan (int dX, int dY) {
		if(alive && map.canPassIt(posX+dX, posY+dY)) {
			posX += dX;
			posY += dY;
			this.changePosition(this.posX, this.posY);
			checkIfCanTakePaper ();
			makeNoise();
		}
		
		if(alive == false) {
			Graphics.playerLose();
		}
		
		if(paperCount == 8) {
			Graphics.playerWin();
		}
	}
	
	/**
	* Ez a metódus megnézi a térképet fel tud e venni egy papírt. Ha fel tud akkor fel is veszi.
	*/
	private void checkIfCanTakePaper () {
		if(map.takePaperIfAvailable(posX, posY)) {
			paperCount++;
			Graphics.addToAnimQueue(animation);
		}
	}
	
	public int getPaperCount() {
		return paperCount;
	}

	public boolean isAlive() {
		return alive;
	}
	
	/**
	* Ez a metódus meghívása esetén zajt csap amelyet a slenderman meghall.<br>
	* Minden lépés után érdemes meghívni, mert a slenderman is akkor lép ha zajt hall.
	*/
	private void makeNoise () {
		slenderman.hearNoise(this);
	}
	
	/**
	*A metúdus meghívása megöli a játékost.
	*/
	public void killed () {
		alive = false;
	}
}

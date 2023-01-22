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
 * Egy j�t�kost �r le amely mozgathat� a megadott p�ly�n.
 * @author Terminator
 *
 */
public class Player extends Renderable{
	
	private int posX, posY; //x, y koordin�t�ja
	private int paperCount; //a pap�rok darabsz�ma
	private boolean alive; //�l e m�g
	private Map map; //a p�lya
	private Font font; //bet�tipus (�gy �rjuk ki h�ny pap�rja van)
	private Slenderman slenderman; //A veszedelmes sz�rnyeteg
	
	private Animation animation;
	
	/**
	*�j j�t�kos l�trehoz�sa.
	*@param startX a j�t�kos kezd� x koordin�t�ja
	*@param startY a j�t�kos kezd� y koordin�t�ja
	*@param map a m�r elk�sz�tett p�lya t�rk�p�t v�rja param�terben
	*@param slenderman egy slendermant v�r param�terben
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
	* Ez a met�dus a j�t�kost mozgatja fel ir�nyba.<br>
	* A j�t�kos csak akkor fog ebbe az ir�nyba t�nylegesen mozdulni, ha ott nincs akad�ly.
	*/
	public void goNorth () {
		goToIfCan(0, 1);
	}
	
	/**
	* Ez a met�dus a j�t�kost mozgatja le ir�nyba.<br>
	* A j�t�kos csak akkor fog ebbe az ir�nyba t�nylegesen mozdulni, ha ott nincs akad�ly.
	*/
	public void goSouth () {
		goToIfCan( 0, -1);
	}
	
	/**
	* Ez a met�dus a j�t�kost mozgatja ballra ir�nyba.<br>
	* A j�t�kos csak akkor fog ebbe az ir�nyba t�nylegesen mozdulni, ha ott nincs akad�ly.
	*/
	public void goWest () {
		goToIfCan(-1, 0);
	}
	
	/**
	* Ez a met�dus a j�t�kost mozgatja jobbra ir�nyba.<br>
	* A j�t�kos csak akkor fog ebbe az ir�nyba t�nylegesen mozdulni, ha ott nincs akad�ly.
	*/
	public void goEast () {
		goToIfCan(1, 0);
	}
	
	@Override
	public void render(Graphics2D g) {
		//Ki rajzolja a j�t�kost �s ki�rja h�ny pap�rja van
		super.render(g);
		g.setFont(font);
		g.setColor(new Color(255,255,255,64));
        g.fillRect(0, 0, 200, 50);
        g.setColor(new Color(0,0,0,255));
		g.drawString("PAP�ROK: "+paperCount+" db", 20, 30);
	}
	
	/**
	* Ez a met�dus a j�tkost pr�b�lja a megadott helyre l�ptettni ha tudja. Teh�t ha nincs ott akad�ly.
	* @param dX amennyivel akarja az x koordin�t�t m�dos�tani
	* @param dY amennyivel az y koordin�t�t akarja m�dos�tani
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
	* Ez a met�dus megn�zi a t�rk�pet fel tud e venni egy pap�rt. Ha fel tud akkor fel is veszi.
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
	* Ez a met�dus megh�v�sa eset�n zajt csap amelyet a slenderman meghall.<br>
	* Minden l�p�s ut�n �rdemes megh�vni, mert a slenderman is akkor l�p ha zajt hall.
	*/
	private void makeNoise () {
		slenderman.hearNoise(this);
	}
	
	/**
	*A met�dus megh�v�sa meg�li a j�t�kost.
	*/
	public void killed () {
		alive = false;
	}
}

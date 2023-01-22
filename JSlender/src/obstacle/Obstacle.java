package obstacle;

import java.awt.Graphics2D;

/**
 * A j�t�kban megjelen� terept�rgyakat �rja le.
 * @author Terminator
 *
 */
public class Obstacle extends Renderable{
	
	private int posX, posY; //A t�rgy bal als� sark�nak a kordin�t�ja
	private int width, height; //A t�rgy sz�less�ge, magass�ga
	private int rotation; //A t�rgy forgat�sa (tetsz�leges eg�sz sz�m * 90-nel) (0: eredeti �llapot)
	private boolean hasPaper; //Van e a t�rgyon pap�r
	private boolean passable; //A j�t�kos �tmehet-e rajta
	private int type; //A t�rgy t�pusa (kis fa, nagy fa, h�z, aut�, furgon, szikla, hord�)
	
	private boolean canHavePaper; //Lehet-e az adott t�rgyon pap�r
	
	public static final int TYPE_SMALL_TREE = 1, TYPE_LARGE_TREE = 2, TYPE_HOUSE = 3, TYPE_CAR = 4, TYPE_VAN = 5, TYPE_ROCK = 6, TYPE_BARREL = 7;
	
	public static final int sizes[] = { //Terept�rgyak m�rete
			1, 1, //kis fa
			2, 2, //nagy fa
			6, 7, //h�z
			2, 3, //aut�
			3, 5, //furgon
			3, 3, //szikla
			2, 4 //hord�
	};

	/**
	 * �j terept�rgy l�trehoz�sa.
	 * @param type egy t�p�s
	 * @param rotation a forgatotts�ga egy�sz sz�m amelyet 90-nel szorzunk
	 * @param posX a terept�rgy bal als� x koordin�t�ja
	 * @param posY a terept�rgy bal als� y koordin�t�ja
	 */
	public Obstacle(int type, int rotation, int posX, int posY) {
		super();
		this.type = type;
		this.rotation = rotation * 90;
		
		this.posX = posX;
		this.posY = posY;
		
		hasPaper = false;
		
		setPassability(type);
		setSizeByType();
		
		this.addObstacle(this.posX, this.posY, this.width, this.height, this.rotation);
		this.loadImage(this.type);
	}
	
	/**
	 * Az el�re meghat�rozott szab�lyok szerint �s a megadott t�pus szerint be�ll�tja az adott terept�rgyat �gy, hogy vagy �t lehet rajta menni vagy nem.
	 * @param type egy megadott t�pus
	 */
	private void setPassability (int type) {
		if(type == TYPE_SMALL_TREE) {
			passable = true;
			canHavePaper = false;
		} else if(type == TYPE_LARGE_TREE){
			passable = true;
			canHavePaper = true;
		} else {
			passable = false;
			canHavePaper = true;
		}
	}
	
	/**
	 * Meghat�rozza a terept�rgy m�ret�t a t�pusa �s a megadott szab�lyok alapj�n.
	 */
	private void setSizeByType () {
		if(rotation % 180 == 0) {
			width = sizes[(type-1)*2];
			height = sizes[(type-1)*2+1];
		} else {
			height = sizes[(type-1)*2];
			width = sizes[(type-1)*2+1];
		}
	}
	
	/**
	 * Erre a terept�rgyra vonatkoz�an megmondja, hogy a param�terben megadott koordin�ta foglalt-e vagy sem.
	 * @param x egy megadott x koordin�ta.
	 * @param y egy megadott y koordin�ta.
	 * @return igaz-hamis �rt�ket ad vissza amely akkor igaz ha az adott koordin�ta foglalt.
	 */
	public boolean isCoordReserved (int x, int y) {
		if(!passable && x>=posX && y>=posY && x<posX+width && y<posY+height) {
			return true;
		}
		return false;
	}
	
	/**
	 * Erre a terept�rgyra vonatkoz�an megmondja hogy el�rhet�-e vagy sem a pap�r, a megadott koordin�t�r�l.
	 * @param x megadott x koordin�ta
	 * @param y megadott y koordin�ta
	 * @return igaz-hamis �rt�k amely akkor igaz ha el�rhet� a pap�r.
	 */
	public boolean isPaperAvailable (int x, int y) {
		if(canHavePaper) {
			if(x>=posX && x<posX+width && (y == posY-1 || y == posY+height)) {
				return true;
			}
			
			if(y>=posY && y<posY+height && (x == posX-1 || x == posX+width)) {
				return true;
			}
		}
		return false;
	}
	
	public int getType () {
		return type;
	}
	
	/**
	 * Ha a megadott koordin�t�r�l el�rhet� a pap�r akkor odaadja a j�t�kosnak, �gy ezen a terept�rgyon m�r nem lesz t�bb pap�r.
	 * @param x j�t�kos x koordin�t�ja
	 * @param y j�t�kos y koordin�t�ja
	 * @return igaz-hamis akkor igaz ha a j�t�kos fel tudja szedni a pap�rt.
	 */
	public boolean takePaperIfAvailable (int x, int y) { 
		boolean paperAvailable = false;
		
		if (hasPaper) {
			paperAvailable = isPaperAvailable(x, y);
			
			if(paperAvailable) {
				hasPaper = false;
			}
		}
		
		return paperAvailable;
	}
	
	public boolean isCanHavePaper () {
		return canHavePaper;
	}
	
	public void itHasPaper () {
		hasPaper = true;
	}
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	
	public int getWidth () {
		return width;
	}
	
	public int getHeight () {
		return height;
	}
	

	public boolean isHasPaper() {
		return hasPaper;
	}
	

	@Override
	public void render(Graphics2D g) {
		//Egy kis debug milyatt volt meg lehet n�zni melyik terept�rgyon vn pap�r
		super.render(g);
		//Debug
		/*g.setStroke(new BasicStroke(10));
		
		if(hasPaper) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		
		int y = 14-posY-height+1;
		g.drawRect(posX*40, y*40, width*40, height*40);*/
		//Debug
	}
}

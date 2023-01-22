package obstacle;

import java.awt.Graphics2D;

/**
 * A játékban megjelenõ tereptárgyakat írja le.
 * @author Terminator
 *
 */
public class Obstacle extends Renderable{
	
	private int posX, posY; //A tárgy bal alsó sarkának a kordinátája
	private int width, height; //A tárgy szélessége, magassága
	private int rotation; //A tárgy forgatása (tetszõleges egész szám * 90-nel) (0: eredeti állapot)
	private boolean hasPaper; //Van e a tárgyon papír
	private boolean passable; //A játékos átmehet-e rajta
	private int type; //A tárgy típusa (kis fa, nagy fa, ház, autó, furgon, szikla, hordó)
	
	private boolean canHavePaper; //Lehet-e az adott tárgyon papír
	
	public static final int TYPE_SMALL_TREE = 1, TYPE_LARGE_TREE = 2, TYPE_HOUSE = 3, TYPE_CAR = 4, TYPE_VAN = 5, TYPE_ROCK = 6, TYPE_BARREL = 7;
	
	public static final int sizes[] = { //Tereptárgyak mérete
			1, 1, //kis fa
			2, 2, //nagy fa
			6, 7, //ház
			2, 3, //autó
			3, 5, //furgon
			3, 3, //szikla
			2, 4 //hordó
	};

	/**
	 * Új tereptárgy létrehozása.
	 * @param type egy típús
	 * @param rotation a forgatottsága egyész szám amelyet 90-nel szorzunk
	 * @param posX a tereptárgy bal alsó x koordinátája
	 * @param posY a tereptárgy bal alsó y koordinátája
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
	 * Az elõre meghatározott szabályok szerint és a megadott típus szerint beállítja az adott tereptárgyat úgy, hogy vagy át lehet rajta menni vagy nem.
	 * @param type egy megadott típus
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
	 * Meghatározza a tereptárgy máretét a típusa és a megadott szabályok alapján.
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
	 * Erre a tereptárgyra vonatkozóan megmondja, hogy a paraméterben megadott koordináta foglalt-e vagy sem.
	 * @param x egy megadott x koordináta.
	 * @param y egy megadott y koordináta.
	 * @return igaz-hamis értéket ad vissza amely akkor igaz ha az adott koordináta foglalt.
	 */
	public boolean isCoordReserved (int x, int y) {
		if(!passable && x>=posX && y>=posY && x<posX+width && y<posY+height) {
			return true;
		}
		return false;
	}
	
	/**
	 * Erre a tereptárgyra vonatkozóan megmondja hogy elérhetõ-e vagy sem a papír, a megadott koordinátáról.
	 * @param x megadott x koordináta
	 * @param y megadott y koordináta
	 * @return igaz-hamis érték amely akkor igaz ha elérhetõ a papír.
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
	 * Ha a megadott koordinátáról elérhetõ a papír akkor odaadja a játékosnak, így ezen a tereptárgyon már nem lesz több papír.
	 * @param x játékos x koordinátája
	 * @param y játékos y koordinátája
	 * @return igaz-hamis akkor igaz ha a játékos fel tudja szedni a papírt.
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
		//Egy kis debug milyatt volt meg lehet nézni melyik tereptárgyon vn papír
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

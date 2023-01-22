package map;
import java.util.ArrayList;
import obstacle.*;

/**
 * Egy pályát ír le amelyen elhelyezkednek különféle tereptárgyak.
 * @author Terminator
 *
 */
public class Map {
	private ArrayList<Obstacle> obstacles; //A tereptárgyakat tartalmazza
	private int width, height; //Pálya mérete
	private House house; //A ház egy speciális tereptárgy

	/**
	 * Új pálya létrehozása.
	 * @param width pálya szélessége
	 * @param height pálya magassága
	 * @param obstacles egy listát vár amely Obstacle-eket (tereptárgyakat) tartalmaz
	 */
	public Map(int width, int height, ArrayList<Obstacle> obstacles) {
		this.width = width;
		this.height = height;
		this.obstacles = obstacles;
		addPaperToObstacles();
		findHouse();
	}
	
	/**
	 * A megadott tereptárgyak között megkeresi a házat, hogy majd késõbb tudjuk külön kezelni
	 */
	private void findHouse () {
		for(int i = 0; i<obstacles.size(); i++) {
			if(obstacles.get(i).getType() == Obstacle.TYPE_HOUSE) {
				house = new House(obstacles.get(i));
				obstacles.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Visszaadja a tereptárgyakat a grafikának, és az majd ki bírja rajzolni
	 * @return egy lista amely Renderable-eket tartalmaz.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Renderable> getRenderables () {
		//Visszaadja a tereptárgyakat a grafikának, és az majd ki bírja rajzolni
		ArrayList<Renderable> renderables = ((ArrayList<Renderable>) obstacles.clone());
		
		if(house != null)
			renderables.add(house);
		
		return renderables;
	}
	
	/**
	 * Megnézi, hogy a megadott koordináta elérhetõ-e, tehát nincs ott akadály
	 * @param x megadott x koordináta
	 * @param y megadott y koordináta
	 * @return visszaadja igaz-hamis értékként, hogy az adott koordináta szabad-e
	 */
	public boolean canPassIt (int x, int y) {
		if(!(x>=0 && y>=0 && x<width && y<height)) {
			return false;
		}
		
		if(house.isCoordReserved(x, y)) {
			return false;
		}
		
		for(int i = 0; i<obstacles.size(); i++) {
			if(obstacles.get(i).isCoordReserved(x, y)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Megnézi, hogy a megadott kordinátán elérhetõ-e papír és ha igen felveszi.
	 * <br> x, y koordinátának a játékost kell megadni.
	 * @param x egy magadott x koordináta
	 * @param y egy magadott y koordináta
	 * @return
	 */
	public boolean takePaperIfAvailable (int x, int y) {
		if(house.takePaperIfAvailable(x, y)) {
			return true;
		}
		
		for (int i = 0; i<obstacles.size(); i++) {
			if(obstacles.get(i).takePaperIfAvailable(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Elõször összeszedi azokat a tereptárgyakat amelyekre lehet papírt tenni.<br>
	 * Ezután véletlenszerûen elhelyezi a papírokat a tereptárgyakon.
	 */
	private void addPaperToObstacles () {
		ArrayList<Obstacle> canHavePaperObstacles = new ArrayList<Obstacle>();
		
		for(int i = 0; i<obstacles.size(); i++) {
			if(obstacles.get(i).isCanHavePaper()) {
				canHavePaperObstacles.add(obstacles.get(i));
			}
		}
		
		int index;
		for(int i = 0; i<8; i++) {
			if(canHavePaperObstacles.size() > 0) {
				index = (int)(Math.random()*canHavePaperObstacles.size());
				canHavePaperObstacles.get(index).itHasPaper();
				canHavePaperObstacles.remove(index);
			}
		}
		canHavePaperObstacles.clear();
	}

}

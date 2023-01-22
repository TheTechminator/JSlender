package map;
import java.util.ArrayList;
import obstacle.*;

/**
 * Egy p�ly�t �r le amelyen elhelyezkednek k�l�nf�le terept�rgyak.
 * @author Terminator
 *
 */
public class Map {
	private ArrayList<Obstacle> obstacles; //A terept�rgyakat tartalmazza
	private int width, height; //P�lya m�rete
	private House house; //A h�z egy speci�lis terept�rgy

	/**
	 * �j p�lya l�trehoz�sa.
	 * @param width p�lya sz�less�ge
	 * @param height p�lya magass�ga
	 * @param obstacles egy list�t v�r amely Obstacle-eket (terept�rgyakat) tartalmaz
	 */
	public Map(int width, int height, ArrayList<Obstacle> obstacles) {
		this.width = width;
		this.height = height;
		this.obstacles = obstacles;
		addPaperToObstacles();
		findHouse();
	}
	
	/**
	 * A megadott terept�rgyak k�z�tt megkeresi a h�zat, hogy majd k�s�bb tudjuk k�l�n kezelni
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
	 * Visszaadja a terept�rgyakat a grafik�nak, �s az majd ki b�rja rajzolni
	 * @return egy lista amely Renderable-eket tartalmaz.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Renderable> getRenderables () {
		//Visszaadja a terept�rgyakat a grafik�nak, �s az majd ki b�rja rajzolni
		ArrayList<Renderable> renderables = ((ArrayList<Renderable>) obstacles.clone());
		
		if(house != null)
			renderables.add(house);
		
		return renderables;
	}
	
	/**
	 * Megn�zi, hogy a megadott koordin�ta el�rhet�-e, teh�t nincs ott akad�ly
	 * @param x megadott x koordin�ta
	 * @param y megadott y koordin�ta
	 * @return visszaadja igaz-hamis �rt�kk�nt, hogy az adott koordin�ta szabad-e
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
	 * Megn�zi, hogy a megadott kordin�t�n el�rhet�-e pap�r �s ha igen felveszi.
	 * <br> x, y koordin�t�nak a j�t�kost kell megadni.
	 * @param x egy magadott x koordin�ta
	 * @param y egy magadott y koordin�ta
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
	 * El�sz�r �sszeszedi azokat a terept�rgyakat amelyekre lehet pap�rt tenni.<br>
	 * Ezut�n v�letlenszer�en elhelyezi a pap�rokat a terept�rgyakon.
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

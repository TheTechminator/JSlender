package map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import obstacle.Obstacle;

/**
 * A tereptárgyak betöltését teszi lehetõvé.<br>
 * A tereptárgyakat betölthetjük fájlból, viszont ha nem áll rendelkezésre ilyen fájluk akkor használhatjuk a már elõre definiált tereptárgyakat is.
 * @author Terminator
 *
 */
public class MapLoader {
	/**
	 * Megpróbálja megkeresni a megadott fájlt a <b>maps</b> mappában és ha megvan akkor megpróbálja beolvasni és betölteni.<br><br>
	 * <b>Fontos: </b>int type;int rotation;int posX;int posY szerkezetûnek kell lenni-e a fájlnak<br><br>
	 * <b>Példa: </b> 6;0;0;12 <i>(szikla;0 fok os forgatás;bal alsó sarok x;y koordinátája)</i><br><br>
	 * <b>Elfogadott típusok: </b> TYPE_SMALL_TREE = 1, TYPE_LARGE_TREE = 2, TYPE_HOUSE = 3, TYPE_CAR = 4, TYPE_VAN = 5, TYPE_ROCK = 6, TYPE_BARREL = 7;<br><br>
	 * @param fileName egy megadott fájlnév, de a fájlnak a <b>maps</b> mappában kell lennie.<br><br>
	 * @return visszaadja a fáljból beolvasott és legenerált tereptárgyakat egy Lista-ként amely Obstacle-eket tartalmaz.
	 */
	
	public ArrayList<Obstacle> loadFromFile (String fileName) {		
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		
		try {
			Scanner be = new Scanner(new File("maps/"+fileName), "cp1250");
			while (be.hasNextLine()) {
				obstacles.add(createObstacleFromString(be.nextLine()));
			}
			be.close();
		} catch (FileNotFoundException e) {
			System.out.println("Nem található ilyen nevû fájl a 'maps' mappában!");
			System.exit(-1);
		}
		
		System.out.println("A fájl be van töltve!");
		
		return obstacles;
	}
	
	/**
	 * Megpróbálja a megadott szöveget felismerni és az alapján létrehoz egy tereptárgyat.<br><br>
	 * <b>Példa: </b> 6;0;0;12 <i>(szikla;0 fok os forgatás;bal alsó sarok x;y koordinátája)</i><br><br>
	 * @param line egy sor amely a megadott szerkezetû
	 * @return visszaadja a létrehozott Obstacle-t (tereptárgyat).
	 */
	private Obstacle createObstacleFromString (String line) {
		String[] temp = line.split(";");
		
		if(temp.length != 4) {
			System.out.println("Hibás fájl!");
			System.exit(-1);
		}
		
		int[] tempInt = new int[4];
		
		try {
			for(int i = 0; i<4; i++) {
				tempInt[i] = Integer.parseInt(temp[i]);
			}
		} catch (Exception e) {
			System.out.println("Hibás fájl!");
			System.exit(-1);
		}
		
		return new Obstacle(tempInt[0], tempInt[1], tempInt[2], tempInt[3]);
	}
	
	/**
	 * Visszaadja az elõre meghatározott tereptárgyakat.
	 * @return List amely Obstacle-eket tartalmaz.
	 */
	public ArrayList<Obstacle> loadPreDefined () {
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		obstacles.add(new Obstacle(Obstacle.TYPE_ROCK, 0, 0, 12));
		obstacles.add(new Obstacle(Obstacle.TYPE_ROCK, 0, 0, 5));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 0, 10));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 3, 13));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 7, 9));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 11, 14));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 13, 13));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 14, 9));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 2, 4));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 4, 2));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 12, 1));
		obstacles.add(new Obstacle(Obstacle.TYPE_SMALL_TREE, 0, 12, 2));
		obstacles.add(new Obstacle(Obstacle.TYPE_LARGE_TREE, 0, 0, 0));
		obstacles.add(new Obstacle(Obstacle.TYPE_LARGE_TREE, 0, 4, 0));
		obstacles.add(new Obstacle(Obstacle.TYPE_LARGE_TREE, 0, 5, 3));
		obstacles.add(new Obstacle(Obstacle.TYPE_LARGE_TREE, 0, 13, 4));
		obstacles.add(new Obstacle(Obstacle.TYPE_CAR, 0, 6, 5));
		obstacles.add(new Obstacle(Obstacle.TYPE_CAR, -1, 6, 13));
		obstacles.add(new Obstacle(Obstacle.TYPE_VAN, 0, 9, 0));
		obstacles.add(new Obstacle(Obstacle.TYPE_BARREL, -1, 3, 10));
		obstacles.add(new Obstacle(Obstacle.TYPE_HOUSE, 0, 8, 6));
		
		System.out.println("A pálya inicializálása megtörtént!");
		
		return obstacles;
	}

}

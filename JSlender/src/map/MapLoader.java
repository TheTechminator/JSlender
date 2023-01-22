package map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import obstacle.Obstacle;

/**
 * A terept�rgyak bet�lt�s�t teszi lehet�v�.<br>
 * A terept�rgyakat bet�lthetj�k f�jlb�l, viszont ha nem �ll rendelkez�sre ilyen f�jluk akkor haszn�lhatjuk a m�r el�re defini�lt terept�rgyakat is.
 * @author Terminator
 *
 */
public class MapLoader {
	/**
	 * Megpr�b�lja megkeresni a megadott f�jlt a <b>maps</b> mapp�ban �s ha megvan akkor megpr�b�lja beolvasni �s bet�lteni.<br><br>
	 * <b>Fontos: </b>int type;int rotation;int posX;int posY szerkezet�nek kell lenni-e a f�jlnak<br><br>
	 * <b>P�lda: </b> 6;0;0;12 <i>(szikla;0 fok os forgat�s;bal als� sarok x;y koordin�t�ja)</i><br><br>
	 * <b>Elfogadott t�pusok: </b> TYPE_SMALL_TREE = 1, TYPE_LARGE_TREE = 2, TYPE_HOUSE = 3, TYPE_CAR = 4, TYPE_VAN = 5, TYPE_ROCK = 6, TYPE_BARREL = 7;<br><br>
	 * @param fileName egy megadott f�jln�v, de a f�jlnak a <b>maps</b> mapp�ban kell lennie.<br><br>
	 * @return visszaadja a f�ljb�l beolvasott �s legener�lt terept�rgyakat egy Lista-k�nt amely Obstacle-eket tartalmaz.
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
			System.out.println("Nem tal�lhat� ilyen nev� f�jl a 'maps' mapp�ban!");
			System.exit(-1);
		}
		
		System.out.println("A f�jl be van t�ltve!");
		
		return obstacles;
	}
	
	/**
	 * Megpr�b�lja a megadott sz�veget felismerni �s az alapj�n l�trehoz egy terept�rgyat.<br><br>
	 * <b>P�lda: </b> 6;0;0;12 <i>(szikla;0 fok os forgat�s;bal als� sarok x;y koordin�t�ja)</i><br><br>
	 * @param line egy sor amely a megadott szerkezet�
	 * @return visszaadja a l�trehozott Obstacle-t (terept�rgyat).
	 */
	private Obstacle createObstacleFromString (String line) {
		String[] temp = line.split(";");
		
		if(temp.length != 4) {
			System.out.println("Hib�s f�jl!");
			System.exit(-1);
		}
		
		int[] tempInt = new int[4];
		
		try {
			for(int i = 0; i<4; i++) {
				tempInt[i] = Integer.parseInt(temp[i]);
			}
		} catch (Exception e) {
			System.out.println("Hib�s f�jl!");
			System.exit(-1);
		}
		
		return new Obstacle(tempInt[0], tempInt[1], tempInt[2], tempInt[3]);
	}
	
	/**
	 * Visszaadja az el�re meghat�rozott terept�rgyakat.
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
		
		System.out.println("A p�lya inicializ�l�sa megt�rt�nt!");
		
		return obstacles;
	}

}

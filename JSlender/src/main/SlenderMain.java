package main;
import java.util.Scanner;

import entity.Player;
import entity.Slenderman;
import graphics.Graphics;
import graphics.JFrameKeyListener;
import map.Map;
import map.MapLoader;
import obstacle.Renderable;

/**
 * A f� oszt�lyuk amely elind�tja a j�t�kot.
 * @author Terminator
 *
 */
public class SlenderMain {
	private Graphics game; //A j�t�k grafik�ja
	private Map map; //A p�lya
	private Player player; //A j�t�kos
	private JFrameKeyListener listener = null; //Esem�nykezel�s
	private Slenderman slenderman; //Veszedelmes sz�rnyeteg
	
	/* nempiszk�lni... */
	private static int oneBlockSize = 40; //Azt hat�rozza meg, hogy grafikusan egy cella h�ny pixel sz�les legyen
	private final int WIDTH = 15, HEIGHT = 15; //P�lya m�rete

	public static void main(String[] args) {
		new SlenderMain();

	}

	public SlenderMain() {
		System.out.println("Adjon meg egy f�jlt amib�l be szeretn� olvasni az adatokat!\n"
				+ "Fontos: a megadott f�jlnak a 'maps' mapp�ban kell lennie!\n"
				+ "Ha nincs ilyen f�jl csak �ss�n egy entert!");
		System.out.print("F�jl: ");
		Scanner be = new Scanner(System.in, "cp1250");
		String fileName = be.nextLine();
		be.close();
		
		System.out.println("\n\nLogs:");
		
		if(fileName.length()>0) {
			map = new Map(WIDTH, HEIGHT, new MapLoader().loadFromFile(fileName));
		} else {
			map = new Map(WIDTH, HEIGHT, new MapLoader().loadPreDefined());
		}
		
		init();
	}
	
	public void init () {
		slenderman = new Slenderman();
		player = new Player(14, 0, map, slenderman);
		game = new Graphics("The Game", WIDTH, HEIGHT, oneBlockSize);
		game.addRenderables(map.getRenderables(), (Renderable)(player), (Renderable)(slenderman));
		game.start();
		listener = new JFrameKeyListener(player);
		game.addKeyListener(listener);
	}
}

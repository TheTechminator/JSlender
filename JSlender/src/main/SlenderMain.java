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
 * A fõ osztályuk amely elindítja a játékot.
 * @author Terminator
 *
 */
public class SlenderMain {
	private Graphics game; //A játék grafikája
	private Map map; //A pálya
	private Player player; //A játékos
	private JFrameKeyListener listener = null; //Eseménykezelés
	private Slenderman slenderman; //Veszedelmes szörnyeteg
	
	/* nempiszkálni... */
	private static int oneBlockSize = 40; //Azt határozza meg, hogy grafikusan egy cella hány pixel széles legyen
	private final int WIDTH = 15, HEIGHT = 15; //Pálya mérete

	public static void main(String[] args) {
		new SlenderMain();

	}

	public SlenderMain() {
		System.out.println("Adjon meg egy fájlt amibõl be szeretné olvasni az adatokat!\n"
				+ "Fontos: a megadott fájlnak a 'maps' mappában kell lennie!\n"
				+ "Ha nincs ilyen fájl csak üssön egy entert!");
		System.out.print("Fájl: ");
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

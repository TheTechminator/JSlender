package entity;

import java.awt.Graphics2D;

import obstacle.Renderable;

/**
 * Egy slenderman-t ír le amely egy kijelölt játékost próbál elkapni.
 * @author Terminator
 *
 */
public class Slenderman extends Renderable{
	
	private int posX, posY; //x, y koordinátája
	private int playerStepsCount = 0; //a játékos lépéseinek a száma onnantól számolva amikor a slenderman megjelenik a pályán
	private int distanceFromPlayer = 0; //a slenderman játékostól való távolsága
	private int distanceEq1 = 0; //Azt számolja, hogy sorozatosan hány alkalommal volt 1-távolságra slenderman a játékostól
	private boolean playerWon = false; //nyert e a játékos

	public Slenderman() {
		posX = -10;
		posY = -10;
		
		this.addObstacle(this.posX, this.posY, 1, 1, 0);
		this.loadImage(8);
	}
	
	/**
	 * Abban az esetben ha a játékos lép egyet azzal zajt csap amit a slenderman meghall és megpróbálja elkapni a játékost.
	 * @param player egy játékost vár paraméterben amelyik a zajt csapta.
	 */
	public void hearNoise (Player player) {
		playerIsCatched(player);
		
		if(player.getPaperCount() == 8) {
			playerWon = true;
		}
		
		if(player.getPaperCount() > 0 && player.isAlive() && !playerWon) {
			playerStepsCount++;
			teleportInMap(player);
			this.changePosition(posX, posY);
			playerIsCatched(player);
		}else if(playerWon) {
			posX = -10;
			posY = -10;
			this.changePosition(posX, posY);
		}
		
		distanceFromPlayer = distance(player, posX, posY);
	}
	
	/**
	 * Slenderman megnézi, hogy a játékosnak mennyi papírja van és az alapján teleportál valahova.
	 * @param player egy játékost vár paraméterben
	 */
	public void teleportInMap (Player player) {
		if(playerStepsCount % 5 == 0) {
			teleportToRandomPlace();
		} else {
			if(player.getPaperCount()<2) {
				teleportToFar5(player);
			}else if(player.getPaperCount()>=2 && player.getPaperCount()<4) {
				teleportToLessThan(player, 5, 33);
			}else if(player.getPaperCount()>=4 && player.getPaperCount()<6) {
				teleportToLessThan(player, 4, 50);
			}else if(player.getPaperCount()>=6) {
				teleportToLessThan(player, 3, 66);
			}
		}
	}
	
	/**
	 * A pályán belül egy random helyre teleportál slenderman.
	 */
	public void teleportToRandomPlace () {
		posX = (int)(Math.random()*15);
		posY = (int)(Math.random()*15);
	}
	
	/**
	 * A játékostól legalább 5 távolságra teleportál
	 * @param player egy játékost vár paraméterben
	 */
	public void teleportToFar5 (Player player) {
		int x = 0, y = 0;
		
		do {
			x = (int)(Math.random()*15);
			y = (int)(Math.random()*15);
		} while (distance(player, x, y) < 5);
		
		posX = x;
		posY = y;
	}
	
	/**
	 * A megadott távolságon belül teleportál és ha 3-szor 1 távolságra volt, akkor a megadott százalékkal elkapja a játékost.
	 * @param player egy játékost vár
	 * @param distance maximum a megadott távolságra teleportál
	 * @param percent a megadott százalékkal próbálja majd meg elkapni a játékost.
	 */
	public void teleportToLessThan (Player player, int distance, int percent) {
		int x = 0, y = 0;
		
		do {
			x = (int)(Math.random()*15);
			y = (int)(Math.random()*15);
		} while (distance(player, x, y) > distance);
		
		if(distance(player, x, y) == 1) {
			distanceEq1++;
		} else {
			distanceEq1 = 0;
		}
		
		if(distanceEq1 == 3) {
			catchPlayer(player, percent);
			distanceEq1 = 0;
		}
		
		posX = x;
		posY = y;
	}
	
	/**
	 * A megadott százalékkal megpróbálja elkapni a játékost.
	 * @param player egy játékost vár paraméterben
	 * @param percent egy százalékot vár paraméterben 0 és 100 között
	 */
	public void catchPlayer (Player player, int percent) {
		int rand = (int)(Math.random()*100);
		if(rand<percent) {
			posX = player.getPosX();
			posY = player.getPosY();
		}
	}
	
	/**
	 * Mégnézi, hogy a játékos és slenderman pozíciója meg egyezik-e és ha igen akkor elkapja és a játékos meghal.
	 * @param player egy játékost vár paraméterben
	 */
	public void playerIsCatched (Player player) {
		if(player.getPosX() == posX && player.getPosY() == posY) {
			System.out.println("Elkaptalak");
			player.killed();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		//Csak akkor válik láthatóvá slenderman, ha a játékos maximum 3 távolságra van
		if(distanceFromPlayer <= 3) {
			super.render(g);
		}
	}
	
	/**
	 * A játékos és a megadott koordináták alapján kiszámolja a köztük levõ Manhattan távolságot.
	 * @param player egy játékost vár paraméterben
	 * @param x egy megadott x koordinátát vár
	 * @param y egy megadott y koordinátát vár
	 * @return egész értékként visszaadja a két pont között levõ Manhattan távolságot.
	 */
	public int distance (Player player, int x, int y) {
		return Math.abs(player.getPosX()-x) + Math.abs(player.getPosY()-y);
	}
}

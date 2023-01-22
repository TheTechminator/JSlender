package entity;

import java.awt.Graphics2D;

import obstacle.Renderable;

/**
 * Egy slenderman-t �r le amely egy kijel�lt j�t�kost pr�b�l elkapni.
 * @author Terminator
 *
 */
public class Slenderman extends Renderable{
	
	private int posX, posY; //x, y koordin�t�ja
	private int playerStepsCount = 0; //a j�t�kos l�p�seinek a sz�ma onnant�l sz�molva amikor a slenderman megjelenik a p�ly�n
	private int distanceFromPlayer = 0; //a slenderman j�t�kost�l val� t�vols�ga
	private int distanceEq1 = 0; //Azt sz�molja, hogy sorozatosan h�ny alkalommal volt 1-t�vols�gra slenderman a j�t�kost�l
	private boolean playerWon = false; //nyert e a j�t�kos

	public Slenderman() {
		posX = -10;
		posY = -10;
		
		this.addObstacle(this.posX, this.posY, 1, 1, 0);
		this.loadImage(8);
	}
	
	/**
	 * Abban az esetben ha a j�t�kos l�p egyet azzal zajt csap amit a slenderman meghall �s megpr�b�lja elkapni a j�t�kost.
	 * @param player egy j�t�kost v�r param�terben amelyik a zajt csapta.
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
	 * Slenderman megn�zi, hogy a j�t�kosnak mennyi pap�rja van �s az alapj�n teleport�l valahova.
	 * @param player egy j�t�kost v�r param�terben
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
	 * A p�ly�n bel�l egy random helyre teleport�l slenderman.
	 */
	public void teleportToRandomPlace () {
		posX = (int)(Math.random()*15);
		posY = (int)(Math.random()*15);
	}
	
	/**
	 * A j�t�kost�l legal�bb 5 t�vols�gra teleport�l
	 * @param player egy j�t�kost v�r param�terben
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
	 * A megadott t�vols�gon bel�l teleport�l �s ha 3-szor 1 t�vols�gra volt, akkor a megadott sz�zal�kkal elkapja a j�t�kost.
	 * @param player egy j�t�kost v�r
	 * @param distance maximum a megadott t�vols�gra teleport�l
	 * @param percent a megadott sz�zal�kkal pr�b�lja majd meg elkapni a j�t�kost.
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
	 * A megadott sz�zal�kkal megpr�b�lja elkapni a j�t�kost.
	 * @param player egy j�t�kost v�r param�terben
	 * @param percent egy sz�zal�kot v�r param�terben 0 �s 100 k�z�tt
	 */
	public void catchPlayer (Player player, int percent) {
		int rand = (int)(Math.random()*100);
		if(rand<percent) {
			posX = player.getPosX();
			posY = player.getPosY();
		}
	}
	
	/**
	 * M�gn�zi, hogy a j�t�kos �s slenderman poz�ci�ja meg egyezik-e �s ha igen akkor elkapja �s a j�t�kos meghal.
	 * @param player egy j�t�kost v�r param�terben
	 */
	public void playerIsCatched (Player player) {
		if(player.getPosX() == posX && player.getPosY() == posY) {
			System.out.println("Elkaptalak");
			player.killed();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		//Csak akkor v�lik l�that�v� slenderman, ha a j�t�kos maximum 3 t�vols�gra van
		if(distanceFromPlayer <= 3) {
			super.render(g);
		}
	}
	
	/**
	 * A j�t�kos �s a megadott koordin�t�k alapj�n kisz�molja a k�zt�k lev� Manhattan t�vols�got.
	 * @param player egy j�t�kost v�r param�terben
	 * @param x egy megadott x koordin�t�t v�r
	 * @param y egy megadott y koordin�t�t v�r
	 * @return eg�sz �rt�kk�nt visszaadja a k�t pont k�z�tt lev� Manhattan t�vols�got.
	 */
	public int distance (Player player, int x, int y) {
		return Math.abs(player.getPosX()-x) + Math.abs(player.getPosY()-y);
	}
}

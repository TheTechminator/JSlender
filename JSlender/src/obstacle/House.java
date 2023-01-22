package obstacle;

/**
 * Egy olyan különleges tereptárgyat ír le amely egy ház.
 * @author Terminator
 *
 */
public class House extends Obstacle{
	private final int HOUSE_WIDTH = 6, HOUSE_HEIGHT = 7; //Ház szélessége, magassága (nem érdemes piszkálni)
	
	private int[] walls = { 
			//A ház szerkezete, 1-es jelenti a falat, 0-ás jelenti, hogy az a terület szabad így oda
			//tud lépni a játékos
			1, 1, 1, 1, 1, 1,
			0, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 1, 1, 1, 0, 1
	};
	
	/**
	 * Létrehoz egy házat.
	 * @param obstacle olyan tereptárgyat vár paraméterben amely ház típusú
	 */
	public House(Obstacle obstacle) {
		super(Obstacle.TYPE_HOUSE, 0, obstacle.getPosX(), obstacle.getPosY());
		
		if(obstacle.isHasPaper()) {
			this.itHasPaper();
		}
	}
	
	@Override
	public boolean isCoordReserved(int x, int y) {
		//Megnézi, hogy a megadott koordináta szabad-e
		int xPos, yPos; //tömb index
		
		if(x>=this.getPosX() && y>=this.getPosY() && x<this.getPosX()+this.getWidth() && y<this.getPosY()+this.getHeight()) {
			xPos = x-this.getPosX();
			yPos = (HOUSE_HEIGHT-1)-(y-this.getPosY());
			
			if(walls[yPos*HOUSE_WIDTH+xPos] == 0) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
}

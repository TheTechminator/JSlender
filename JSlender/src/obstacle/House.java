package obstacle;

/**
 * Egy olyan k�l�nleges terept�rgyat �r le amely egy h�z.
 * @author Terminator
 *
 */
public class House extends Obstacle{
	private final int HOUSE_WIDTH = 6, HOUSE_HEIGHT = 7; //H�z sz�less�ge, magass�ga (nem �rdemes piszk�lni)
	
	private int[] walls = { 
			//A h�z szerkezete, 1-es jelenti a falat, 0-�s jelenti, hogy az a ter�let szabad �gy oda
			//tud l�pni a j�t�kos
			1, 1, 1, 1, 1, 1,
			0, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 0, 0, 0, 0, 1,
			1, 1, 1, 1, 0, 1
	};
	
	/**
	 * L�trehoz egy h�zat.
	 * @param obstacle olyan terept�rgyat v�r param�terben amely h�z t�pus�
	 */
	public House(Obstacle obstacle) {
		super(Obstacle.TYPE_HOUSE, 0, obstacle.getPosX(), obstacle.getPosY());
		
		if(obstacle.isHasPaper()) {
			this.itHasPaper();
		}
	}
	
	@Override
	public boolean isCoordReserved(int x, int y) {
		//Megn�zi, hogy a megadott koordin�ta szabad-e
		int xPos, yPos; //t�mb index
		
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

package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Player;

/**
 * A billentyüzet kezelését teszi lehetõvé, amely segítségével lehet mozgatni a játékosunkat.
 * @author Terminator
 *
 */
public class JFrameKeyListener implements KeyListener{
	//Kezeli a billentyûzetet, hogy a játékosunk meg bírjon mozdulni
	private Player player;

	public JFrameKeyListener(Player player) {
		this.player = player;
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		//KeyCodes: 87 = w, 65 = a, 83 = s, 68 = d
		
		switch (e.getKeyCode()) {
		case 87:
			player.goNorth();
			break;
		case 65:
			player.goWest();
			break;
		case 83:
			player.goSouth();
			break;
		case 68:
			player.goEast();
			break;
		}
		
	}

}

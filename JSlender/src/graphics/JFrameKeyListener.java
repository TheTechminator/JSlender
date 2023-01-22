package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Player;

/**
 * A billenty�zet kezel�s�t teszi lehet�v�, amely seg�ts�g�vel lehet mozgatni a j�t�kosunkat.
 * @author Terminator
 *
 */
public class JFrameKeyListener implements KeyListener{
	//Kezeli a billenty�zetet, hogy a j�t�kosunk meg b�rjon mozdulni
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

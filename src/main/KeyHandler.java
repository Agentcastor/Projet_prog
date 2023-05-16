package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'évènements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener{
	public int code ;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
<<<<<<< HEAD
		// rÃ©cupÃ¨re le code du boutton appuyÃ©
		code = e.getKeyCode();
=======
		// récupère le code du boutton appuyé
		int code = e.getKeyCode();
>>>>>>> parent of cff2447 (Modification commentaires)
		System.out.println(code);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}

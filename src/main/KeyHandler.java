package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'�v�nements (touche clavier)
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
		// récupère le code du boutton appuyé
		code = e.getKeyCode();
=======
		// r�cup�re le code du boutton appuy�
		int code = e.getKeyCode();
>>>>>>> parent of cff2447 (Modification commentaires)
		System.out.println(code);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}

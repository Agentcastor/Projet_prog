package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'évènements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener {
	private int code;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		code = e.getKeyCode();
		System.out.println(code);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		code = e.getKeyCode() * 10;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code; 
	}
}

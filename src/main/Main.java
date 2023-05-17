package main;

import javax.swing.JFrame;

import sounds.Sounds;

/**
 * 
 * Classe principale du jeu
 *
 */
public class Main {


	public static void main(String[] args) {

		//Fenetre de lancement du jeu
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("ESIR1 - Projet Prog");
		
		//Ajout du panel du jeu et demarrage du thread principal
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		Sounds background= new Sounds("/audio/Music_background.wav");
			background.run();
		gamePanel.startGameThread();	

	}

}

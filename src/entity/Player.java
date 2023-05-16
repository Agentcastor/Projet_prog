package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

/**
 * Défintition du comportement d'un joueur
 *
 */
public class Player extends MovingEntity{

	GamePanel m_gp;
	KeyHandler m_keyH;
	private boolean m_jumping; // true si le joueur est en train de sauter
	private int m_jumpDistance; // Distance verticale parcourue dans un saut
	private int m_maxJumpDistance; // Distance maximale verticale pouvant être parcourue dans un saut
	//private static Player instance = null; // Singleton pour recuperer le heros facilement dans toutes les classes
	//private int countDownDamage;
	//private int invulnerability;

	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		super(100, 100, "/Player/superhero.png", 6, 4,4, 0);
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
	}
	
	// Méthodes pour sauter
	public void jump() {
        if(m_jumping) {
            moveUp();
            m_jumpDistance += 1;
            if(isHeadBumped() || m_jumpDistance >= m_maxJumpDistance) { // On doit s'arrêter de sauter
                m_jumping = false;
                m_jumpDistance = 0; // On réinitialise la distance parcourue
            }
        }
    }

    public void tombe(){
        if(!m_jumping){
            moveDown();
        }
    }

    public void gravity(){
        if(isOnFloor()){
            if(m_keyH.getCode() == 32){ // Si on appuie sur la touche espace 
                m_jumping = true;
            }
        }
		else {
			tombe();
		}
        jump();
    }

	public boolean isHeadBumped() {
		return true;
	}

	public boolean isOnFloor() {
		return true;
	}
	
	/**
	 * Mise à jour des données du joueur
	 */
	public void update() {
		
	}
	
	/**
	 * Affichage du l'image du joueur dans la fenêtre du jeu
	 * @param a_g2 Graphics2D 
	 */
	public void draw(Graphics2D a_g2) {
		// récupère l'image du joueur
		BufferedImage l_image = getImage();
		// affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
		a_g2.drawImage(l_image, getX(), getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
	}
	
	
	
}

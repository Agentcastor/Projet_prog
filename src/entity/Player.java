package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.KeyHandler;
import tile.TileManager;

/**
 * Défintition du comportement d'un joueur
 *
 */
public class Player extends LivingEntity{
	KeyHandler m_keyH;
	private boolean m_jumping; // true si le joueur est en train de sauter
	private int m_jumpDistance; // Distance verticale parcourue dans un saut
	private int m_maxJumpDistance; // Distance maximale verticale pouvant être parcourue dans un saut
	private static Player m_instance = null; // Singleton pour recuperer le joueur facilement dans toutes les classes
	//private int countDownDamage;
	//private int invulnerability;

	//mouvements
	private boolean r;
	private boolean l;
	private boolean j;


	//animation 
	private boolean ori;//true : right, false : left
	private int timer;
	private int il = 7;
	private String[] pathl = {"/Player/Gob_l0.png", "/Player/Gob_l1.png", "/Player/Gob_l0.png", "/Player/Gob_l2.png"};
	private int ir = 7;
	private String[] pathr = {"/Player/Gob_r0.png", "/Player/Gob_r1.png", "/Player/Gob_r0.png", "/Player/Gob_r2.png"};

	/**
	 * Constructeur de Player
	 * @param tm Tilemanager, salle de jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(TileManager tm, KeyHandler a_keyH) {
		super(100, 100, "/Player/Gob_l0.png",tm, 6, 4,4, 0);
		this.m_keyH = a_keyH;
		m_jumpDistance = 0;
		m_maxJumpDistance = 40;
		Player.m_instance = this;
		ori = false;
		timer = 0;
	}
	
	// Méthodes pour sauter
	public void mouvement(){
		if(m_keyH.getCode() == 68){
			r = true;
		}
		else if(m_keyH.getCode() == 680){
			r = false;
		}

		if(m_keyH.getCode() == 81){
			l = true;
		}
		else if(m_keyH.getCode() == 810){
			l = false;
		}

		if(m_keyH.getCode() == 90){
			j = true;
		}
		else if(m_keyH.getCode() == 900){
			j = false;
		}

		if(r){
			moveRight();
			ori = true;
			animR();
		}
		else if(l){
			moveLeft();
			ori = false;
			animL();
		}
		else{//ne bouge pas
			timer = 0;
			il = 7;
			ir = 7;
			if(ori){
				setImage("/Player/Gob_r0.png");
			}
			else{
				setImage("/Player/Gob_l0.png");
			}
		}
	}

	public void animL(){
		timer +=1;
		System.out.println(timer);
		if(timer >8){
			timer = 0;
			il++;
			if(il>3)il=0;
			setImage(pathl[il]);
		}
	}

	public void animR(){
		timer +=1;
		if(timer >8){
			timer = 0;
			ir++;
			if(ir>3)ir=0;
			setImage(pathr[ir]);
		}
	}

    public void gravity(){
        if(getOnFloor()){
			if(!m_jumping && j){// Si on appuie sur la touche espace et qu'on ne saute pas
				m_jumping = true;
			} 
		} 
		else {//on est pas au sol donc on tombe
			if(!m_jumping){
				moveDown();//tomber à vitesse expo
			}
		}

        if(m_jumping) {
            moveUp();
            m_jumpDistance += 1; //sauter à vitesse 1/expo
            if(m_jumpDistance >= m_maxJumpDistance || getOnCeil()|| (!j&&m_jumpDistance >= m_maxJumpDistance/2)) { // On doit s'arrêter de sauter
                m_jumping = false;
                m_jumpDistance = 0; // On réinitialise la distance parcourue
            }
        }
    }

	/**
	 * Mise à jour des données du joueur
	 */
	@Override
	public void update() {
		gravity();
		mouvement();
		tilesCollisions();
	}
	
	/**
	 * Affichage du l'image du joueur dans la fenêtre du jeu
	 * @param a_g2 Graphics2D 
	 */
	public void draw(Graphics2D a_g2) {
		// récupère l'image du joueur
		BufferedImage l_image = getImage();
		// affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
		a_g2.drawImage(l_image, getX(), getY(), 48, 48, null);
	}
	
	public static Player getInstance(){
		return m_instance;
	}
	
	public int getMaxJumpDistance(){
		return m_maxJumpDistance;
	}

	public void setMaxJumpDistance(int d){
		m_maxJumpDistance = d;
	}
}

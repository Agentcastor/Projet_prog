package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

/**
 * Défintition du comportement d'un joueur
 *
 */
public class Player extends LivingEntity implements Attacker {
	KeyHandler m_keyH;
	private boolean m_jumping; // true si le joueur est en train de sauter
	private int m_jumpDistance; // Distance verticale parcourue dans un saut
	private int m_maxJumpDistance; // Distance maximale verticale pouvant être parcourue dans un saut
	private int countDownDamage;
	private int invulnerability;
	private static Player m_instance = null; // Singleton pour recuperer le joueur facilement dans toutes les classes

	// attacks
	private boolean canAttack;
	private int countDownAttack;

	//mouvements
	private boolean r; // right
	private boolean l; // left
	private boolean j; // jump


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
		super(100, 100, "/Player/Gob_l0.png",tm, 4, 4,2, 6, 6);
		this.m_keyH = a_keyH;
		m_jumpDistance = 0;
		m_maxJumpDistance = 40;
		countDownDamage = 0;
		invulnerability = 0;
		canAttack = true;
		countDownAttack = 0;
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
		else { //ne bouge pas
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
		if(m_keyH.getCode() == 32 ) {
			tryToAttack();
		}
	}

	public void tryToAttack() {
		if (canAttack) {
			if (ori) {
				setImage("/Player/Gob_ar.png");
			}
			else {
				setImage("/Player/Gob_al.png");
			}
			for (Entity entity : getTileMap().getListEntity()) {
				if (entity instanceof LivingEntity) {
					if((getX()-48 <= entity.getX()) && (entity.getX() <= getX() +48) && (getY()-48 <= entity.getY()) && (entity.getY() <= getY()+48)){
						attack(((LivingEntity) entity));
						canAttack = false;
						System.out.println("Il attaque");
					}
				}
			}
		} else {
			countDownAttack ++;
			if (countDownAttack >= 20) { // Le joueur peut a nouveau attaquer 
				countDownAttack = 0;
				canAttack = true;
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
		collisionEntity();
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

	/**
	 * Fonction permettant de dire si le heros peut prendre des degats ou non
	 * @return vrai si le joueur peut prendre des degats, faux sinon
	 */
	public boolean isVulnerable() {
		if (invulnerability == 0) { // Le joueur peut prendre des degats
			return true;
		} else if (invulnerability == 1){ // Le joueur vient de prendre des degats
			invulnerability = 2;
			countDownDamage = 0; 
		} else { 
			countDownDamage++; // On diminue le temps restant d'invulnerabilite
			if (countDownDamage >= 40) { // Le joueur peut reprendre des degats
				invulnerability = 0;
				countDownDamage = 0;
			}
		} return false;
	}

	/**
	 * Teste si le joueur est en collision avec une entite de la salle
	 * @param entity, l'entite avec laquelle on veut tester la collision
	 * @return vrai si le joueur est en collision avec l'entite, faux sinon
	 */
	public boolean isInCollision(Entity entity){
		return (entity.getX() > this.getX() && entity.getX() < this.getX() + 48 && entity.getY() > this.getY() && entity.getY() < this.getY() + 48);
	}

	public void collisionEntity() {
		LinkedList<Entity> entities = getTileMap().getListEntity(); // On recupere la liste des entités de la salle
		if (!(entities == null || entities.isEmpty())){ // On s assure de ne pas travailler dans une liste null ou vide
			for(Entity entity : entities) { // On parcourt la liste pour agir pour chaque entity 
				if (isInCollision(entity)) { // Fonction a developper
					if (entity instanceof Arrow) { // Si c'est une fleche
						if (isVulnerable()) { // Si le joueur est vulnerable, on lui inflige un degat et on change sa valeur de invulnerability
							invulnerability = 1;
							((Arrow) entity).attack(this);
						}
					}
					if (entity instanceof Spike) { // Si c'est un pique
						if (isVulnerable()) {
							invulnerability = 1;
							((Spike) entity).attack(this);
						}
					}
					if (entity instanceof Spike) { // Si c'est un pique
						if (isVulnerable()) {
							invulnerability = 1;
							((DynamicSpike) entity).attack(this);
						}
					}
					if (entity instanceof Bat) { // Si c'est un pique
						if (isVulnerable()) {
							invulnerability = 1;
							((Bat) entity).attack(this);
						}
					}
					if (entity instanceof Loot) { // Si c'est un item
						((Loot) entity).onAdded();
					}
				}
			}
		}
	}

	@Override
	public void attack(LivingEntity e) {
		e.setLife(e.getLife()- getDamage()); // On retire la vie à la cible
	}

}
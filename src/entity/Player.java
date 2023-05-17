package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import main.GamePanel;
import sounds.Sounds;
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
	private GamePanel m_gp ;
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
	public Player(TileManager tm, KeyHandler a_keyH, GamePanel gp ) {
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
		m_gp = gp ;
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
			Sounds attack = new Sounds("/audio/bruit_epee3.wav");
			attack.run();
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
				Sounds saut= new Sounds("/audio/saut.wav");
				saut.run();
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
		collisionEntity();
		tilesCollisions();

		if (m_keyH.getCode() == 84) {
			if (getLife() > 0) setLife(getLife()-1);
		}
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
			if (countDownDamage >= 20) { // Le joueur peut reprendre des degats
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
		Entity[] toRemove = new Entity[10];
		int i = 0;
		if (!(entities == null || entities.isEmpty())){ // On s assure de ne pas travailler dans une liste null ou vide
			for(Entity entity : entities) { // On parcourt la liste pour agir pour chaque entity 
				if (isInCollision(entity)) { // Fonction a developper
					if (entity instanceof Arrow) { // Si c'est une fleche
						if (isVulnerable()) { // Si le joueur est vulnerable, on lui inflige un degat et on change sa valeur de invulnerability
							invulnerability = 1;
							((Arrow) entity).attack(this);
							Sounds degat = new Sounds("/audio/degat_1.wav");
							degat.run();
						}
					}
					if (entity instanceof Spike) { // Si c'est un pique
						if (isVulnerable()) {
							invulnerability = 1;
							((Spike) entity).attack(this);
							Sounds degat = new Sounds("/audio/degat_1.wav");
							degat.run();
						}
					}
					
					if (entity instanceof Bat) { // Si c'est un pique
						if (isVulnerable()) {
							invulnerability = 1;
							((Bat) entity).attack(this);
							Sounds degat = new Sounds("/audio/degat_2.wav");
							degat.run();
						}
					}
					if (entity instanceof Loot) { // Si c'est un item
						if (((Loot) entity).onAdded()) {
							toRemove[i] = entity;
							i++;
						}
					}
				}
			}
		}
		for (int j = 0; j < i; j++) {
			getTileMap().getListEntity().remove(toRemove[j]);
		}
	}

	@Override
	public void attack(LivingEntity e) {
		e.setLife(e.getLife()- getDamage()); // On retire la vie à la cible
		Sounds damage = new Sounds("/audio/degat_2.wav");
		damage.run();
	}

	//collisions avec les tiles
	@Override
    public void tilesCollisions(){
        //coordonées dans le TileMap
        int xt = (getX()+24)/48; //point central de l'entité mise dans les coordonées de la tile map
        int yt = (getY()+24)/48;
        //System.out.println(xt + ", " + yt);
        //bords de l'entité 
        int x1 = getX(); //gauche
        int y1 = getY(); //haut
        int x2 = getX() + 48; //droit
        int y2 = getY() + 48;

        //collisions des tiles autour des entités
        boolean colU = getTileMap().getTileCollision(xt,yt-1); //haut
        boolean colL = getTileMap().getTileCollision(xt-1,yt); //gauche
        boolean colD = getTileMap().getTileCollision(xt,yt+1); //bas
        boolean colR = getTileMap().getTileCollision(xt+1,yt); //droite

		// Recuperation de l'ID des Tiles adjacentes
		int idU = getTileMap().getTileID(xt,yt-1); //haut
        int idL = getTileMap().getTileID(xt-1,yt); //gauche
        int idD = getTileMap().getTileID(xt,yt+1); //bas
        int idR = getTileMap().getTileID(xt+1,yt); //droite

        //bords des tiles
        //bord inf tileU : ((yt-1)*48)+24
        int bu = ((yt-1)*48)+48;
        int bl = ((xt-1)*48)+48;
        int bd = ((yt+1)*48);
        int br = ((xt+1)*48);

        if(colU && y1 < bu){ //collision tete
           setY(bu+1);
           onCeil = true;
           System.out.println("col haut");
		   m_gp.nextLevel(idU);
        }
        else{
            onCeil = false;
        }

        if(colL && x1 < bl){ //collision gauche
            setX(bl+1);
            System.out.println("col gauche");
			m_gp.nextLevel(idL);
        }

        if(colD && y2 > bd){ //collision bas
            setY(bd-49);
            onFloor = true;
			if(getTileMap().getMapTileNum(xt, yt+1) == 3){
                setLife(0);
            }
			m_gp.nextLevel(idD);
        }
        else{
            onFloor = false;
        }

        if(colR && x2 > br){ //collision droite
            setX(br-49);
            System.out.println("col droite");
			m_gp.nextLevel(idR);
        }
    }

}
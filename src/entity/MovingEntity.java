package entity;

import java.lang.Math;
import main.GamePanel;
import tile.TileManager;

public abstract class MovingEntity extends Entity{ // Classe des entités qui se déplacent
    private int m_life; // Vie de l'entité
    private int m_speedX, m_speedY; // Déplacement horizontal et vertical de l'entité
    private int m_damage; // Dégâts causés par l'entité

    public MovingEntity(int x, int y, String path, TileManager tm, int life, int speedX, int speedY, int damage){
        super(x,y,path,tm);
        m_life = life;
        m_speedX = speedX;
        m_speedY = speedY;
        m_damage = damage;
    }

    public int getLife() {
        return m_life;
    }

    public void setLife(int life) {
        m_life = life;
    }

    public int getSpeedX() {
        return m_speedX;
    }

    public void setSpeedX(int speedX) {
        m_speedX = speedX;
    }

    public int getSpeedY() {
        return m_speedY;
    }

    public void setSpeedY(int speedY) {
        m_speedX = speedY;
    }

    public int getDamage() {
        return m_damage;
    }

    public void setDamage(int damage) {
        m_damage = damage;
    }

    // Gestion des déplacements
    public void moveUp() {
        setY(getY() - m_speedY);
    }

    public void moveDown() {
        setY(getY() + m_speedY);
    }

    public void moveLeft() {
        setX(getX() - m_speedX);
    }

    public void moveRight() {
        setX(getX() + m_speedX);
    }

    

    //collisions avec les tiles
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
        boolean colU = getTileMap().getTileCollision(xt,yt-1);
        boolean colL = getTileMap().getTileCollision(xt-1,yt);
        boolean colD = getTileMap().getTileCollision(xt,yt+1);
        boolean colR = getTileMap().getTileCollision(xt+1,yt);

        //bords des tiles
        //bord inf tileU : ((yt-1)*48)+24
        int bu = ((yt-1)*48)+48;
        int bl = ((xt-1)*48)+48;
        int bd = ((yt+1)*48);
        int br = ((xt+1)*48);

        if(colU && y1 < bu){ //collision tete
           setY(bu+1);
           System.out.println("col haut");
        }
        if(colL && x1 < bl){ //collision gauche
            setX(bl+1);
            System.out.println("col gauche");
        }
        if(colD && y2 > bd){ //collision bas
            setY(bd-49);
            System.out.println("col bas");
        }
        if(colR && x2 > br){ //collision droite
            setX(br-49);
            System.out.println("col droite");
        }
    }

    /**
	 * Mise à jour des données de l'entité
	 */
    public void update() {

    }

    //Taper
    public void hit(MovingEntity e){
        e.setLife(e.getLife()-m_damage);
    }

    public boolean isDead(){
        return m_life <= 0;
    }
}

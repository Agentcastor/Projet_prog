package entity;

import java.lang.Math;
import main.GamePanel;
import tile.TileManager;

public abstract class MovingEntity extends Entity { // Classe des entités qui se déplacent
   
    private int m_speedX, m_speedY; // Déplacement horizontal et vertical de l'entité
    private int m_damage; // Dégâts causés par l'entité

    //booléens de positions
    protected boolean onFloor;
    protected boolean onCeil;

    public MovingEntity(int x, int y, String path, TileManager tm, int speedX, int speedY, int damage){
        super(x, y, path, tm);
        m_speedX = speedX;
        m_speedY = speedY;
        m_damage = damage;
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
        m_speedY = speedY;
    }

    public boolean getOnFloor(){
        return onFloor;
    }

    public boolean getOnCeil(){
        return onCeil;
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
        boolean colU = getTileMap().getTileCollision(xt,yt-1); //haut
        boolean colL = getTileMap().getTileCollision(xt-1,yt); //gauche
        boolean colD = getTileMap().getTileCollision(xt,yt+1); //bas
        boolean colR = getTileMap().getTileCollision(xt+1,yt); //droite

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
        }
        else{
            onCeil = false;
        }

        if(colL && x1 < bl){ //collision gauche
            setX(bl+1);
            System.out.println("col gauche");
        }

        if(colD && y2 > bd){ //collision bas
            setY(bd-49);
            onFloor = true;
            //System.out.println("col bas");
        }
        else{
            onFloor = false;
        }

        if(colR && x2 > br){ //collision droite
            setX(br-49);
            System.out.println("col droite");
        }
    }

}

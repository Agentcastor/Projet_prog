package entity;

import tile.TileManager;

public class LivingEntity extends MovingEntity {
    
    private int m_life; // Vie de l'entité
    private int m_maxLife ;
    


    public LivingEntity(int x, int y, String path, TileManager tm, int speedX, int speedY, int damage, int life, int maxLife) {
        super(x,y,path,tm,speedX, speedY, damage);

        m_life = life;
        m_maxLife = maxLife ;
    }

    public int getLife() {
        return m_life;
    }

    public void setLife(int life) {
        m_life = life;
    }

     //Taper
     public void hit(LivingEntity e){
        e.setLife(e.getLife()-getDamage());
    }

    public boolean isDead(){
        return m_life <= 0;
    }

    public int getMaxLife() { 
        return m_maxLife;
    
    }

    @Override
    public void tilesCollisions(){
        //coordonées dans le TileMap
        int xt = (getX()+24)/48; //point central de l'entité mise dans les coordonées de la tile map
        int yt = (getY()+24)/48;
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
           setOnCeil(true);
        }
        else{
            setOnCeil(false);
        }

        if(colL && x1 < bl){ //collision gauche
            setX(bl+1);
        }
        if(colD && y2 > bd){ //collision bas
            setY(bd-49);
            setOnFloor(true);
            if(getTileMap().getMapTileNum(xt, yt+1) == 3){
                setLife(0);
            }
        }
        else{
            setOnFloor(false);
        }
        if(colR && x2 > br){ //collision droite
            setX(br-49);
        }
    }


    public void setMaxLife(int maxLife) { 
        m_maxLife = maxLife ;
    }
}

package entity;

import java.awt.Image;

import tile.TileManager;

public class Arrow extends MovingEntity implements Attacker {
    private boolean m_left;
    public Arrow(int x , int y, TileManager tm, boolean left){
        super(x,y,"",tm, 5,0,1);
        if (left){
            m_left = true;
            this.setImage("res/tiles/arrowtoLeft.png");
        }
        else {
            m_left = false;
            this.setImage("res/tiles/arrowtoRight.png");
        }  
    }

    @Override
    public void update() {
        if(isOutside()) { // si la fleche n'est plus dans la map
            getTileMap().getListEntity().remove(this); // on l'enleve des entites de la salle
            setTilemap(null);
        }
        if (m_left) moveLeft();
        if (!m_left) moveRight();
    }

    /**
     * Dit si la fleche est sortie de la map
     * @return vrai si la fleche est sortie de la map, faux sinon
     */
    public boolean isOutside(){
        return this.getX() <= 0 || this.getX() >= 16*48 || this.getY() <= 0 || this.getY() >= 16*48;
    }
    
    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - getDamage());
        getTileMap().getListEntity().remove(this); // On retire la fleche de la salle
    }

    public boolean isLeft() {
        return m_left;
    }
}

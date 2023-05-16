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
        if (m_left) moveLeft();
        if (!m_left) moveRight();
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

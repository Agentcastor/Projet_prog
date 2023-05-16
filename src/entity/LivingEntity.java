package entity;

import tile.TileManager;

public class LivingEntity extends MovingEntity {
    
    private int m_life; // Vie de l'entité
    
    public LivingEntity(int x, int y, String path, TileManager tm, int speedX, int speedY, int damage, int life) {
        super(x,y,path,tm,speedX, speedY, damage);
        m_life = life;
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
}

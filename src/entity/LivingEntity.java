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

    public void setMaxLife(int maxLife) { 
        m_maxLife = maxLife ;
    }
}

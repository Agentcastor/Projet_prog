package entity;

import tile.TileManager;

public class DynamicSpike extends MovingEntity implements Attacker {

    private int m_clock;
    public DynamicSpike(int x, int y, TileManager tm) {
        super(x, y, "", tm, 0, 5, 1);
        m_clock = 0;
    }

    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - getDamage());
    }
    
    @Override 
    public void tilesCollisions() {
        // Pas de collision
    }

    @Override 
    public void update() {
        if(m_clock == 0) {
            moveUp();
            m_clock ++;
        } else if (m_clock == 20) {
            moveDown();
        }
    }
    
}

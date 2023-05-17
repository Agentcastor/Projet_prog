package entity;

import tile.TileManager;

public class Spike extends Entity implements Attacker {

    private int m_damage;
    public Spike(int x, int y, TileManager tm) {
        super(x, y, "../entities/spike.png", tm);
        m_damage = 1;
    }

    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - m_damage);
    }
    
}

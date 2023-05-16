package entity;

import tile.TileManager;

public class Spike extends Entity implements Attacker {

    private int m_damage;
    public Spike(int x, int y, String path, TileManager tm, int damage) {
        super(x, y, path, tm);
        m_damage = damage;
    }

    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - m_damage);
    }
    
}

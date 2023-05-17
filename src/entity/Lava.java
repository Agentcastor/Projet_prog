package entity;

import tile.TileManager;

public class Lava extends Entity implements Attacker {

    public Lava(int x, int y, TileManager tm, int damage) {
        super(x, y, "res/tiles/LAVA.png", tm);
        m_damage = damage;
    }

    private int m_damage;

    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - m_damage);
    }
    
}

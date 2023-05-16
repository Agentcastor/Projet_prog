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
        if(isOutside()) { // si la fleche n'est plus dans la map
            getTileMap().getListEntity().remove(this); // on l'enleve des entites de la salle
            setTilemap(null);
        }
        if(m_clock == 0) {
            moveUp();
            m_clock ++;
        } else if (m_clock == 1) {
            moveDown();
        }
    }

    /**
     * Dit si la fleche est sortie de la map
     * @return vrai si la fleche est sortie de la map, faux sinon
     */
    public boolean isOutside(){
        return this.getX() <= 0 || this.getX() >= 16*48 || this.getY() <= 0 || this.getY() >= 16*48;
    }
    
}

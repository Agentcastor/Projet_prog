package entity;

import tile.TileManager;

public class Bat extends MovingEntity implements Attacker {

    public Bat(int x, int y, TileManager tm) {
        super(x, y, "", tm, 1, 1, 1);
    }

    @Override
    public void update() {

    }
    @Override
    public void tilesCollisions() {
        // Pas de collision
    }

    public void move () {
		if (Player.getInstance().getX() < this.getX()) {
			this.moveLeft();
		} 
		if (Player.getInstance().getX() > this.getX()) {
			this.moveRight();
		} 
		if (Player.getInstance().getY() < this.getY()) {
			this.moveDown();
		}
		if (Player.getInstance().getY() > this.getY()) {
			this.moveUp();
		}
    }

    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - getDamage());
    }
    

}

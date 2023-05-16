package entity;

import tile.TileManager;

public class Bat extends LivingEntity implements Attacker {

    public Bat(int x, int y, TileManager tm) {
        super(x, y, "", tm, 1, 1, 1, 3);
    }

    @Override
    public void update() {
        move();
        if(isDead()) {
            getTileMap().getListEntity().remove(this);
            setTilemap(null);
        }
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

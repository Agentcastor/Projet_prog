package entity;

import tile.TileManager;

public class Bat extends LivingEntity implements Attacker {

    public Bat(int x, int y, TileManager tm) {
        super(x, y, "../entities/bat.png", tm, 1, 1, 1, 3, 3);
    }

    @Override
    public void update() {
        move();
    }

    public void move () {
		if (Player.getInstance().getX() < this.getX()) {
			this.moveLeft();
		} 
		if (Player.getInstance().getX() > this.getX()) {
			this.moveRight();
		} 
		if (Player.getInstance().getY() < this.getY()) {
			this.moveUp();
		}
		if (Player.getInstance().getY() > this.getY()) {
			this.moveDown();
		}
    }

    @Override
    public void attack(LivingEntity e) {
        e.setLife(e.getLife() - getDamage());
    }
    

}

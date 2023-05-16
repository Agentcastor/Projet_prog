package entity;

import tile.TileManager;

public class Sword extends Entity {
    
    public Sword(int x, int y, String path, TileManager tm) {
        super(x,y,path,tm);
    }

    public void onAdded() {
        Player.getInstance().setDamage(Player.getInstance().getDamage() + 1); // Avantage de l'item
        getTileMap().listEntity().remove(this); // On fait disparaître l'épée de la carte
    }
}

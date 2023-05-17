package entity;

import tile.TileManager;

public class Sword extends Entity implements Loot {
    
    public Sword(int x, int y, TileManager tm) {
        super(x,y,"../imgUI/sword.png",tm);
    }

    public void onAdded() {
        Player.getInstance().setDamage(Player.getInstance().getDamage() + 1); // Avantage de l'item
        getTileMap().getListEntity().remove(this); // On fait disparaître l'épée de la carte
    }
}

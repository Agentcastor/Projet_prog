package entity;

import tile.TileManager;

public class JumpBoots extends Entity implements Loot {
    
    public JumpBoots(int x, int y, TileManager tm) {
        super(x,y,"../entities/jumpBoots.png",tm);
    }

    public void onAdded() {
        Player.getInstance().setMaxJumpDistance(Player.getInstance().getMaxJumpDistance() + 20);; // Avantage de l'item
        getTileMap().getListEntity().remove(this); // On fait disparaître l'item de la carte
    }

} 

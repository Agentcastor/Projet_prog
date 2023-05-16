package entity;

import tile.TileManager;

public class JumpBoots extends Entity implements Loot {
    
    public JumpBoots(int x, int y, String path, TileManager tm) {
        super(x,y,path,tm);
    }

    public void onAdded() {
        Player.getInstance().setMaxJumpDistance(Player.getInstance().getMaxJumpDistance() + 1);; // Avantage de l'item
        getTileMap().getListEntity().remove(this); // On fait disparaître l'item de la carte
    }

} 

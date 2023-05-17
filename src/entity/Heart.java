package entity;

import tile.TileManager;

public class Heart extends Entity implements Loot {
    
    public Heart(int x, int y, String path, TileManager tm) {
        super(x,y,path,tm);
    }

    public void onAdded() {
        if ((Player.getInstance().getLife() != 6 )){ // Si la vie n'est pas maximale
            if ((Player.getInstance().getLife() + 1) <= 6) { // si on ne dépasse pas la vie maximale du joueur
                Player.getInstance().setLife(Player.getInstance().getLife() + 1); // Avantage de l'item
                getTileMap().getListEntity().remove(this); // On fait disparaître l'item de la carte
            } else {
                Player.getInstance().setLife(6); // Avantage de l'item
            }
        }
    }

}

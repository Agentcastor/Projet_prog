package entity;

import tile.TileManager;

public class Heart extends Entity implements Loot {
    
    public Heart(int x, int y, TileManager tm) {
        super(x,y,"../imgUI/heart_half.png",tm);
    }

    public boolean onAdded() {
        if ((Player.getInstance().getLife() != Player.getInstance().getMaxLife())){ // Si la vie n'est pas maximale
            if ((Player.getInstance().getLife() + 1) <= 6) { // si on ne dépasse pas la vie maximale du joueur
                Player.getInstance().setLife(Player.getInstance().getLife() + 1); // Avantage de l'item
            } else {
                Player.getInstance().setLife(6); // Avantage de l'item
            }
            return true; // On fait disparaître l'item de la carte
        }
        return false;
    }

}

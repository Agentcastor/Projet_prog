package entity;

public class Heart extends Entity {
    
    public Heart(int x, int y, String path, TileManager tm) {
        super(x,y,path,tm);
    }

    public void onAdded() {
        if (Player.getInstance().getLife() + 1) <= 6 { // si on ne dépasse pas la vie maximale du joueur
            Player.getInstance().setLife(Player.getInstance().getLife() + 1);; // Avantage de l'item
        } else {
            Player.getInstance().setLife(6);; // Avantage de l'item
        }
        getTileMap().listEntity().remove(this); // On fait disparaître l'item de la carte
    }

}

package entity;

public class JumpBoots extends Entity {
    
    public JumpBoots(int x, int y, String path, TileManager tm) {
        super(x,y,path,tm);
    }

    public void onAdded() {
        Player.getInstance().setMaxJumpDistance(Player.getInstance().getMaxJumpDistance() + 1);; // Avantage de l'item
        getTileMap().listEntity().remove(this); // On fait disparaître l'item de la carte
    }

} 

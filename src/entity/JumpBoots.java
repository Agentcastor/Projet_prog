package entity;

public class JumpBoots extends Entity {
    
    public JumpBoots(int x, int y, String path) {
        setX(x);
        setY(y);
        setImage(path);
    }

    public void onAdded() {
        Player.getInstance().setMaxJumpDistance(Player.getInstance().getMaxJumpDistance() + 1);; // Avantage de l'item
        getTileMap().listEntity().remove(this); // On fait disparaître l'item de la carte
    }

} 

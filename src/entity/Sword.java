package entity;

public class Sword extends Entity {
    
    public Sword(int x, int y, String path) {
        setX(x);
        setY(y);
        setImage(path);
    }

    public void onAdded() {
        Player.getInstance().setDamage(Player.getInstance().getDamage() + 1); // Avantage de l'item
        getTileMap().listEntity().remove(this); // On fait disparaître l'épée de la carte
    }
}

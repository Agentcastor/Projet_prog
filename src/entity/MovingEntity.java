package entity;

public class MovingEntity extends Entity{ // Classe des entités qui se déplacent
    private int m_life; // Vie de l'entité
    private int m_speedX, m_speedY; // Déplacement horizontal et vertical de l'entité
    private int m_damage; // Dégâts causés par l'entité

    public MovingEntity(int x, int y, String path, int life, int speedX, int speedY, int damage){
        setX(x);
        setY(y);
        setImage(path);
        m_life = life;
        m_speedX = speedX;
        m_speedY = speedY;
        m_damage = damage;
    }

    public int getLife() {
        return m_life;
    }

    public void setLife(int life) {
        m_life = life;
    }

    public int getSpeedX() {
        return m_speedX;
    }

    public void setSpeedX(int speedX) {
        m_speedX = speedX;
    }

    public int getSpeedY() {
        return m_speedY;
    }

    public void setSpeedY(int speedY) {
        m_speedX = speedY;
    }

    public int getDamage() {
        return m_damage;
    }

    public void setDamage(int damage) {
        m_damage = damage;
    }

    // Gestion des déplacements
    public void moveUp() {
        setY(getY() - m_speedY);
    }

    public void moveDown() {
        setY(getY() + m_speedY);
    }

    public void moveLeft() {
        setY(getX() - m_speedX);
    }

    public void moveRight() {
        setY(getX() + m_speedX);
    }

}

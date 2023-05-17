package entity;


public interface Attacker {

	/**
	 * Methode qui retire de la vie à une entité proportionnellement au damage de l'entite this
     * @param e, l'entité cible de l'attaque
	 */
	public void attack(LivingEntity e);
}

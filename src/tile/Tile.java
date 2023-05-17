package tile;

import java.awt.image.BufferedImage;

/**
 * 
 * Element graphique de la carte
 */
public class Tile {
	public BufferedImage m_image;		//image
	public boolean m_collision;			//d�but de gestion de collision entre �l�ments
	public int m_id ;
	
	Tile(boolean b, int id){
		m_collision = b;
		m_id = id ;
	}
}

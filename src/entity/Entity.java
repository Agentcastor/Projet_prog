package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tile.TileManager;

/**
 * Entité de base du jeu
 *
 */
public abstract class Entity {
	private int m_x, m_y;		// Position sur la map
	private BufferedImage m_image;	 // Une image de l'entité
	private TileManager m_tileMap;

	public Entity(int x, int y, String path, TileManager tm){
		m_x = x;
		m_y = y;
		setImage(path);
		m_tileMap = tm;
	}

	public TileManager getTileMap(){
		return m_tileMap;
	}

	public void setTilemap(TileManager tm){
		m_tileMap = tm;
	}

	public int getX() {
		return m_x;
	}

	public void setX(int x) {
		m_x = x;
	}

	public int getY() {
		return m_y;
	}

	public void setY(int y) {
		m_y = y;
	}

	public BufferedImage getImage(){
		return m_image;
	}

	public void setImage(String path){
		try {
			m_image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}




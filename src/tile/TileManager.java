package tile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import entity.Entity;
import entity.Lava;
import main.GamePanel;

/**
 * 
 * Gestionnaire des tiles du jeu
 *
 */
public class TileManager {
	GamePanel m_gp;			//panel du jeu principal
	Tile[] m_tile;			//tableau de toutes les tiles possibles dans le jeu
	int m_maxTiles = 20;	//nombre maximum de tiles chargeable dans le jeu
	int m_mapTileNum[][];	//répartition des tiles dans la carte du jeu
	

	BufferedImage background;
	LinkedList<Entity> listEntity;

	
	/**
	 * Constructeur
	 * @param gp
	 */
	public TileManager(GamePanel gp,int n_bc) {

		this.m_gp =  gp;
		m_tile = new Tile[m_maxTiles];
		m_mapTileNum = new int[gp.MAX_SCREEN_COL][gp.MAX_SCREE_ROW];
		this.getTileImage();

		listEntity = new LinkedList<Entity>();
		//listEntity.add(gp.getPlayer());
		
		try{
		if (n_bc == 0) { // selon le niveau
				background= ImageIO.read(getClass().getResource("/tiles/background.png"));
				this.loadMap("/maps/map2.txt");
			
		} else if (n_bc == 1)  {
				background= ImageIO.read(getClass().getResource("/tiles/background.png"));
				this.loadMap("/maps/map.txt");
			
		} else if (n_bc == 2)  {
			background= ImageIO.read(getClass().getResource("/tiles/background_cave.png"));
			this.loadMap("/maps/map3.txt");
		
		} else if (n_bc == 3)  {
			background= ImageIO.read(getClass().getResource("/tiles/background_cave.png"));
			this.loadMap("/maps/map4.txt");
		
		} else if (n_bc == 4)  {
			background= ImageIO.read(getClass().getResource("/tiles/background_cave.png"));
			this.loadMap("/maps/map5.txt");
		
		} else if (n_bc == 5)  {
			background= ImageIO.read(getClass().getResource("/tiles/background_lastroom.png"));
			this.loadMap("/maps/map6.txt");
		
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	public int getMapTileNum(int x, int y) {
		return m_mapTileNum[x][y];
	}
	
	/**
	 * Chargement de toutes les tuiles du jeu
	 */
	public void getTileImage() {
		try {
			m_tile[0] = new Tile(true,0);
			m_tile[0].m_image = ImageIO.read(getClass().getResource("../tiles/GRASS.png"));
			
			m_tile[1] = new Tile(true,0);
			m_tile[1].m_image = ImageIO.read(getClass().getResource("../tiles/bloc.png"));
			
			m_tile[2] = new Tile(true,0);
			m_tile[2].m_image = ImageIO.read(getClass().getResource("../tiles/WATER.png"));
			
			m_tile[3] = new Tile(true,0);
			m_tile[3].m_image = ImageIO.read(getClass().getResource("../tiles/LAVA.png"));
			
			m_tile[4] = new Tile(true,0);
			m_tile[4].m_image = ImageIO.read(getClass().getResource("../tiles/SAND.png"));
			
			m_tile[5] = new Tile(true,0);
			m_tile[5].m_image = ImageIO.read(getClass().getResource("../tiles/SNOW.png"));

			m_tile[6] = new Tile(false,0);
			m_tile[6].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
			
			m_tile[7] = new Tile(true,0);
			m_tile[7].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));

			m_tile[8] = new Tile(true,1); // TP 1
			m_tile[8].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
			
			m_tile[9] = new Tile(true,2); // TP 2
			m_tile[9].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
			
			m_tile[10] = new Tile(true,3); // TP 3
			m_tile[10].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));

			m_tile[11] = new Tile(true,4); // TP 4
			m_tile[11].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));

			m_tile[12] = new Tile(true,5); // TP 5
			m_tile[12].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
						
			m_tile[13] = new Tile(true,6); // TP 6
			m_tile[13].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
						
			m_tile[14] = new Tile(true,7); // TP 7
			m_tile[14].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
						
			m_tile[15] = new Tile(true,8); // TP 8
			m_tile[15].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
						
			m_tile[16] = new Tile(true,9); // TP 9
			m_tile[16].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
						
			m_tile[17] = new Tile(true,10); // TP 10
			m_tile[17].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
						
			m_tile[18] = new Tile(true,11); // TP 11
			m_tile[18].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));

			m_tile[19] = new Tile(true,12); // TP 12
			m_tile[19].m_image = ImageIO.read(getClass().getResource("../tiles/tiles_inv.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lecture du fichier txt contenant la map et chargement des tuiles correspondantes.
	 */
	public void loadMap(String filePath) {
		//charger le fichier txt de la map
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
			int col = 0;
			int row = 0;
			
			// Parcourir le fichier txt pour r�cup�rer les valeurs
			while (col < m_gp.MAX_SCREEN_COL && row < m_gp.MAX_SCREE_ROW) {
				String line = br.readLine();
				while (col < m_gp.MAX_SCREEN_COL) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					m_mapTileNum [col][row] = num;
					col++;
				}
				if (col == m_gp.MAX_SCREEN_COL) {
					col = 0;
					row ++;
				}
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//dit si la tile x, y a des collisions 
	public boolean getTileCollision(int x, int y){
		return m_tile[m_mapTileNum[x][y]].m_collision;
	}

	// Retourne l'id de la Tile
	public int getTileID(int x, int y){
		return m_tile[m_mapTileNum[x][y]].m_id;
	}
	
	public LinkedList<Entity> getListEntity() {
		return listEntity;
	}

	/**
	 * Affichage de la carte avec les diff�rentes tuiles
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		
        g2.drawImage(background,  0, 0, m_gp.SCREEN_WIDTH, m_gp.SCREEN_HEIGHT, null);
		
	 
		while (col < m_gp.MAX_SCREEN_COL && row < m_gp.MAX_SCREE_ROW) {
			int tileNum = m_mapTileNum[col][row];
			g2.drawImage(m_tile[tileNum].m_image, x, y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
			col ++;
			x += m_gp.TILE_SIZE;
			if (col == m_gp.MAX_SCREEN_COL) {
				col = 0;
				row ++;
				x = 0;
				y += m_gp.TILE_SIZE;
			}
		}
		
	}
}

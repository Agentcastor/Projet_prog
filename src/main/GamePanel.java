package main;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

import entity.Arrow;
import entity.Entity;
import entity.Player;
import tile.TileManager;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Panel principal du jeu contenant la map principale
 *
 */
public class GamePanel extends JPanel implements Runnable{
	
	//Param�tres de l'�cran
	final int ORIGINAL_TILE_SIZE = 16; 							// une tuile de taille 16x16
	final int SCALE = 3; 										// �chelle utilis�e pour agrandir l'affichage
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; 	// 48x48
	public final int MAX_SCREEN_COL = 16;
	public final int MAX_SCREE_ROW = 12; 					 	// ces valeurs donnent une r�solution 4:3
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW;	// 576 pixels

	// FPS : taux de rafraichissement
	int m_FPS;
	
	// Cr�ation des diff�rentes instances (Player, KeyHandler, TileManager, GameThread ...)
	KeyHandler m_keyH;
	Thread m_gameThread;
	Player m_player;
	TileManager[] m_tileM;
	TileManager m_tileC ;
	int compteur ;
	
		
	/**
	 * Constructeur
	 */
	public GamePanel() {
		m_FPS = 60;				
		m_keyH = new KeyHandler();
		m_tileM = new TileManager[6];
		m_tileM[0] = new TileManager(this,0) ;
		m_tileM[0].getListEntity().add(new Arrow(14*48, 7*48, m_tileM[0], true));
		m_tileM[1] = new TileManager(this,1) ;
		m_tileM[2] = new TileManager(this,2) ;
		m_tileM[3] = new TileManager(this,3) ;
		m_tileM[4] = new TileManager(this,4) ;
		m_tileM[5] = new TileManager(this,5) ;
		compteur = 0 ;
		m_tileC = m_tileM[compteur] ;
		m_player = new Player(this.m_tileC, m_keyH);

		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(m_keyH);
		this.setFocusable(true);
	}
	
	/**
	 * Lancement du thread principal
	 */
	public void startGameThread() {
		m_gameThread = new Thread(this);
		m_gameThread.start();
	}
	
		/**
	 * Getter
	 */

	 public TileManager getTileManager(){
		return m_tileC;
	}

	public void run() {
		
		double drawInterval = 1000000000/m_FPS; // rafraichissement chaque 0.0166666 secondes
		double nextDrawTime = System.nanoTime() + drawInterval; 
		
		while(m_gameThread != null) { //Tant que le thread du jeu est actif
			
			//Permet de mettre � jour les diff�rentes variables du jeu
			this.update();
			
			//Dessine sur l'�cran le personnage et la map avec les nouvelles informations. la m�thode "paintComponent" doit obligatoirement �tre appel�e avec "repaint()"
			this.repaint();
			
			//Calcule le temps de pause du thread
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Mise � jour des donn�es des entit�s
	 */
	public void update() {
		m_player.update();
		for (Entity entity : m_tileC.getListEntity()) {
			entity.update();
		}
		if (m_keyH.getCode() == 73) {
			this.nextLevel();
			m_player.setTilemap(m_tileC);
			m_keyH.setCode(-1);
		} 
	}
	
	/**
	 * Affichage des �l�ments
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		m_tileC.draw(g2);
		m_player.draw(g2);
		for (Entity entity : m_tileC.getListEntity()) {
			entity.draw(g2);
		}
		g2.dispose();
	}

	public void nextLevel() {
		compteur++;
		if (compteur >= m_tileM.length) compteur = 0 ;
		m_tileC = m_tileM[compteur];

	}

	public Player getPlayer() {
		return m_player ;
	}
	
}

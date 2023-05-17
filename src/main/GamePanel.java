package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Arrow;
import entity.Entity;
import entity.Player;
import sounds.Sounds;
import tile.TileManager;
import ui.Hearts;
import ui.Strength;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
	Hearts health ;
	Strength strength_bar ;
	
	BufferedImage background_gameOver ;
	Boolean endGame;
	
		
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
		m_tileC = m_tileM[0] ;
		m_player = new Player(this.m_tileC, m_keyH, this);
		health = new Hearts(0,0,m_player.getMaxLife(),this);
		strength_bar = new Strength(0,TILE_SIZE/2,m_player.getDamage());

		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(m_keyH);
		this.setFocusable(true);

		try {
			background_gameOver = ImageIO.read(getClass().getResource("/imgUI/GameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		endGame = false ;
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
			// Fin du jeu
			if (m_player.getLife() <= 0) {
				endGame = true;
			}
			if (endGame){
				paintComponent2(getGraphics());
				Sounds end= new Sounds("/audio/loose_song.wav");
			end.run();
			break;
			}
			//Permet de mettre a jour les differentes variables du jeu
			this.update();
			
			//Dessine sur l'ecran le personnage et la map avec les nouvelles informations. la m�thode "paintComponent" doit obligatoirement �tre appel�e avec "repaint()"
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
		health.update(m_player.getLife());
		strength_bar.update(m_player.getDamage());
	}
	
	/**
	 * Affichage des �l�ments
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Calibri", Font.BOLD, 20);
		g2.setColor(Color.WHITE);
        g2.setFont(font);
		m_tileC.draw(g2);
		m_player.draw(g2);
		for (Entity entity : m_tileC.getListEntity()) {
			entity.draw(g2);
		}
		health.draw(g2);
		strength_bar.draw(g2);
		if (endGame){
		g2.drawImage(background_gameOver,  0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		}
		g2.dispose();

	}
	public void paintComponent2(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (endGame){
			g2.drawImage(background_gameOver,  0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
			}
			g2.dispose();}

	/**
	 * Modifie le niveau selon l'ID de la tile
	 * @param ID
	 */
	public void nextLevel(int ID) {
		if (ID == 1 ) {
			m_tileC = m_tileM[1];
			m_player.setTilemap(m_tileC);
			m_player.setX(2*TILE_SIZE);
		}
		else if (ID == 2 ) {
			m_tileC = m_tileM[0];
			m_player.setTilemap(m_tileC);
			m_player.setX(SCREEN_WIDTH - 2*TILE_SIZE);
		} else if (ID == 3 ) {
			m_tileC = m_tileM[2];
			m_player.setTilemap(m_tileC);
			m_player.setY(2*TILE_SIZE);
		} 
		else if (ID == 4) {
			m_tileC = m_tileM[1];
			m_player.setTilemap(m_tileC);
			m_player.setY(SCREEN_HEIGHT - 2*TILE_SIZE);
		}
		else if (ID == 5) {
			m_tileC = m_tileM[3];
			m_player.setTilemap(m_tileC);
			m_player.setX(SCREEN_WIDTH - 2*TILE_SIZE);
		}
		else if (ID == 6) {
			m_tileC = m_tileM[2];
			m_player.setTilemap(m_tileC);
			m_player.setX( 2*TILE_SIZE);
		}
		else if (ID == 7) { 
			m_tileC = m_tileM[4];
			m_player.setTilemap(m_tileC);
			m_player.setX( 2*TILE_SIZE);
		}
		else if (ID == 8 ) {
			m_tileC = m_tileM[2];
			m_player.setTilemap(m_tileC);
			m_player.setX(SCREEN_WIDTH - 2*TILE_SIZE);
		} 
		else if (ID == 9 ) {
			m_tileC = m_tileM[3];
			m_player.setTilemap(m_tileC);
			m_player.setX( 2*TILE_SIZE);
		} 
		else if (ID == 10 ) {
			m_tileC = m_tileM[4];
			m_player.setTilemap(m_tileC);
			m_player.setX(SCREEN_WIDTH - 2*TILE_SIZE);
		} else if (ID == 11 ) {
			m_tileC = m_tileM[5];
			m_player.setTilemap(m_tileC);
			m_player.setY(SCREEN_HEIGHT - 2*TILE_SIZE);
			m_player.setX(SCREEN_WIDTH - 2*TILE_SIZE);
		} else if (ID == 12 ) {
			m_tileC = m_tileM[4];
			m_player.setTilemap(m_tileC);
			m_player.setY( 2*TILE_SIZE);
		} 
		
	}

	

	public Player getPlayer() {
		return m_player ;
	}
	
}

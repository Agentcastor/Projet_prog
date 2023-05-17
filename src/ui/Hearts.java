package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import main.GamePanel ;

public class Hearts{
    private int x ;
    private int y ;
    private int pv ;
    private int pv_max ;
    private int nb_coeurs ;
    private GamePanel gp ;

    private LinkedList<Ui> heartList ;
    private BufferedImage heart_img ;
    private BufferedImage heart_half_img ;
    private BufferedImage heart_empty_img ;

    public Hearts(int x, int y, int pv_max, GamePanel gp) {
        this.pv_max = pv_max ;
        pv = pv_max ;
        this.x = x ;
        this.y = y ;
        this.gp = gp ;

        int widthHeart = (16 * 3) / 2 ; // Moitié d'une tile
        int heightHeart = (16 * 3) / 2 ; // Moitié d'une tile

        // On cherche les images des coeurs
        try {
			heart_img = ImageIO.read(getClass().getResource("/imgUI/heart.png"));
            heart_half_img = ImageIO.read(getClass().getResource("/imgUI/heart_half.png"));
            heart_empty_img = ImageIO.read(getClass().getResource("/imgUI/heart_empty.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        

        
        int xi = x ; // Position de chaque coeurs (on commence par le coeur de 
                     // gauche, puis on ajoute la taille d'un coeur a chaque iteration)
        
        // On créé chaque coeurs
        nb_coeurs = pv_max / 2 ;
        heartList = new LinkedList<Ui>();
        for (int i = 0 ; i < nb_coeurs ; i++ ) { // pv_max/2 represente le nombre de coeurs
            heartList.add(new Ui(xi, y,heart_img, widthHeart, heightHeart)) ;
            xi += widthHeart ;
        }
    }

    /**
     * Fonction d'affichage
     */
    public void draw(Graphics2D g2) {
        for (Ui heart : heartList) {
            heart.draw(g2);
        }
    }

    /**
     * Fonction de mise à jour
     */
    public void update(int newPv){
        int compteur = newPv ;
        for (int i = 0 ; i < nb_coeurs ; i++) {
            if (compteur > 1) {
                heartList.get(i).setImg(heart_img);
                compteur -= 2 ;
            } else if (compteur == 1) {
                heartList.get(i).setImg(heart_half_img);
                compteur -= 2 ;
            } else {
                heartList.get(i).setImg(heart_empty_img);
            }
        }
    }
}

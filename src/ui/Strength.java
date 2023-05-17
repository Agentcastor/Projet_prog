package ui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Strength {
    private int value ;
    private BufferedImage sword_img ;
    private Ui ui ;
    private int x ;
    private int y ;

    private int widthSword ;
    private int heightSword ;

    public Strength(int x, int y, int value ) {
        this.x = x ;
        this.y = y ;
        this.value = value ;

        widthSword = (16 * 3) / 2 ; // Moitié d'une tile
        heightSword = (16 * 3) / 2 ; // Moitié d'une tile

        try {
			sword_img = ImageIO.read(getClass().getResource("/imgUI/sword.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        ui = new Ui(x,y,sword_img,widthSword,heightSword);
    }

    /**
     * Fonction d'affichage
     */
    public void draw(Graphics2D g2) {
        ui.draw(g2);
        
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString(" : " + value, x + widthSword, y + heightSword/2 + 5);
    }

    /**
     * Fonction de mise à jour
     */
    public void update(int newValue){
        value = newValue ;
    }
}

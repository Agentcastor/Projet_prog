package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Ui {
    private int x ;
    private int y ;
    private BufferedImage img ;
    private int width ;
    private int height ;

    public Ui(int x, int y, BufferedImage img, int width, int height) {
        this.x = x;
        this.y = y;
        this.img = img ;
        this.width = width ;
        this.height = height ;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(img,  x, y, width, height, null);
    }

    public int getX() {return x ;} 
    public void setX(int x) {this.x = x ;}
    public int getY() {return y ;} 
    public void setY(int y) {this.y = y ;}
    public BufferedImage getImg() {return img ;} 
    public void setImg(BufferedImage img) {this.img = img ;}
    public int getWidth() {return width ;} 
    public void setWidth(int width) {this.width = width ;}
    public int getHeight() {return height ;} 
    public void setHeight(int height) {this.height = height ;}

}

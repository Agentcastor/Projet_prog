package entity;

import java.awt.Image;

public class Fleche extends MovingEntity {
Fleche (int x , int y, boolean droite){
    super(x,y,"",100,3,0,1);
    if (droite){
        this.setImage("res/tiles/arrowtoRight.png");
        
    }
    else {
        this.setImage("res/tiles/arrowtoLeft.png");
        this.setSpeedX(-this.getSpeedX());
        
    }
    
    
}

}

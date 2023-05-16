package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hearts{
    int x ;
    int y ;
    int pv ;
    int pv_max ;

    public Hearts(int x, int y, int pv_max) {
        assert(pv%2 == 0) ;
        this.pv_max = pv_max ;
        pv = pv_max ;
        try {
			BufferedImage heart_img = ImageIO.read(getClass().getResource("/Ui/heart.png"));
            BufferedImage heart_half_img = ImageIO.read(getClass().getResource("/Ui/heart_half.png"));
            BufferedImage heart_empty_img = ImageIO.read(getClass().getResource("/Ui/heart_empty.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}

package sounds;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sounds {
    String url;

    public Sounds(String url){
    this.url=url;
    }
    public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(url));
          clip.open(inputStream);
          clip.start(); 
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }

}
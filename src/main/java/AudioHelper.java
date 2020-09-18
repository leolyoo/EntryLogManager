import javax.sound.sampled.*;
import java.io.IOException;

public enum AudioHelper {
    INSTANCE;

    public void play() {
        try (final AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("sound_enter.wav"));
             final Clip clip = AudioSystem.getClip()) {
            clip.stop();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

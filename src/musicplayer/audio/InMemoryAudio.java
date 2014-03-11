package musicplayer.audio;

import javax.sound.sampled.*;
import java.io.IOException;

public class InMemoryAudio implements Audio {
    private Clip clip;

    public InMemoryAudio(AudioInputStream stream)
            throws IOException, LineUnavailableException {
        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
    }

    @Override
    public void play() {
        clip.start();
    }

    @Override
    public void pause() {
        clip.stop();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public void close() {
        clip.close();
    }
}

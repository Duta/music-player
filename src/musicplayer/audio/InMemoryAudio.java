package musicplayer.audio;

import javax.sound.sampled.*;
import java.io.IOException;

public class InMemoryAudio implements Audio {
    private static final int MICROSECONDS_PER_SECOND = 1_000_000;
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

    @Override
    public void setCurrentSecs(int currentSecs) {
        clip.setMicrosecondPosition((long) currentSecs * MICROSECONDS_PER_SECOND);
    }

    @Override
    public int getCurrentSecs() {
        return (int) clip.getMicrosecondPosition() / MICROSECONDS_PER_SECOND;
    }

    @Override
    public int getTotalSecs() {
        return (int) clip.getMicrosecondLength() / MICROSECONDS_PER_SECOND;
    }
}

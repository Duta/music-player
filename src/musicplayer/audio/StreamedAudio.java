package musicplayer.audio;

import javax.sound.sampled.AudioInputStream;

public class StreamedAudio implements Audio {
    public StreamedAudio(AudioInputStream audioUrl) {
        // TODO
        throw new RuntimeException("StreamedAudio is unimplemented");
    }

    @Override
    public void play() {
        // TODO
    }

    @Override
    public void pause() {
        // TODO
    }

    @Override
    public void stop() {
        // TODO
    }

    @Override
    public void close() {
        // TODO
    }

    @Override
    public void setCurrentSecs(int currentSecs) {
        // TODO
    }

    @Override
    public int getCurrentSecs() {
        // TODO
        return 0;
    }

    @Override
    public int getTotalSecs() {
        // TODO
        return 0;
    }
}

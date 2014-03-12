package musicplayer.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioFactory {
    private static AudioFactory instance;

    private AudioFactory() {}

    public static AudioFactory getInstance() {
        if(instance == null) {
            instance = new AudioFactory();
        }
        return instance;
    }

    public Audio getAudio(File file) {
        return getAudio(file, 10_000_000L);
    }

    public Audio getAudio(File file, long maxInMemoryLength) {
        if(!file.exists()) {
            return null;
        }
        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(file);
        } catch(UnsupportedAudioFileException | IOException e) {
            return null;
        }
        if(file.length() < maxInMemoryLength) {
            try {
                return new InMemoryAudio(stream);
            } catch(IOException | LineUnavailableException e) {
                return null;
            }
        } else {
            return new StreamedAudio(stream);
        }
    }
}

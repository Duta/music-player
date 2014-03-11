package musicplayer.audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class AudioFactory {
    private static AudioFactory instance;

    private AudioFactory() {
    }

    public static AudioFactory getInstance() {
        if(instance == null) {
            instance = new AudioFactory();
        }
        return instance;
    }

    public Audio getAudio(String file) {
        return getAudio(file, 200_000L);
    }

    public Audio getAudio(String file, long maxInMemoryLength) {
        URL audioUrl = getClass().getResource(file);
        URLConnection conn;
        try {
            conn = audioUrl.openConnection();
        } catch(IOException e) {
            return null;
        }
        if(conn.getContentLengthLong() < maxInMemoryLength) {
            try {
                return new InMemoryAudio(audioUrl);
            } catch(IOException | UnsupportedAudioFileException |
                    LineUnavailableException e) {
                return null;
            }
        } else {
            return new StreamedAudio(audioUrl);
        }
    }
}

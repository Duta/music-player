package musicplayer.audio;

public interface Audio {
    public void play();

    public void pause();

    public void stop();

    public void close();

    public void setCurrentSecs(int currentSecs);

    public int getCurrentSecs();

    public int getTotalSecs();
}

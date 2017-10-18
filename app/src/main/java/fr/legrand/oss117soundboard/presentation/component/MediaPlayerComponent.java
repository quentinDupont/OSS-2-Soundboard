package fr.legrand.oss117soundboard.presentation.component;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface MediaPlayerComponent {
    void playSoundMedia(int mediaId);

    void releaseCurrentMediaPlayer();

    void releaseAllRunningPlayer();
}

package fr.legrand.oss117soundboard.presentation.component;

import io.reactivex.Completable;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface MediaPlayerComponent {
    Completable playSoundMedia(int mediaId);

    void releaseCurrentMediaPlayer();

    void releaseAllRunningPlayer();

    boolean isPlayerCurrentlyRunning();
}

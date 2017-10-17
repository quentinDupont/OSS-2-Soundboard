package fr.legrand.oss117soundboard.data.entity;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by Benjamin on 17/10/2017.
 */

public class RunningPlayer {

    private SimpleExoPlayer simpleExoPlayer;
    private int mediaId;

    public RunningPlayer(SimpleExoPlayer simpleExoPlayer, int mediaId) {
        this.simpleExoPlayer = simpleExoPlayer;
        this.mediaId = mediaId;
    }

    public SimpleExoPlayer getSimpleExoPlayer() {
        return simpleExoPlayer;
    }

    public void setSimpleExoPlayer(SimpleExoPlayer simpleExoPlayer) {
        this.simpleExoPlayer = simpleExoPlayer;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }
}

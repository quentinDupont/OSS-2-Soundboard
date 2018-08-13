package fr.legrand.oss117soundboard.presentation.component;

import android.net.Uri;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.entity.RunningPlayer;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.ui.base.BaseActivity;
import io.reactivex.Completable;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class MediaPlayerComponentImpl implements MediaPlayerComponent {

    private static final int MULTI_LISTEN_NUMBER_LIMIT = 8;

    private BaseActivity activity;

    private ContentRepository contentRepository;
    private ErrorComponent errorComponent;

    private List<RunningPlayer> runningMediaPlayerList;

    private long startListenTimestamp;

    @Inject
    public MediaPlayerComponentImpl(BaseActivity activity, ContentRepository contentRepository, ErrorComponent errorComponent) {
        this.activity = activity;
        this.contentRepository = contentRepository;
        this.errorComponent = errorComponent;
        this.runningMediaPlayerList = new ArrayList<>();
        this.startListenTimestamp = 0;
    }

    @Override
    public Completable playSoundMedia(int mediaId) {
        return Completable.create(emitter -> {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(null);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector);
            Uri soundUri = RawResourceDataSource.buildRawResourceUri(mediaId);
            RawResourceDataSource dataSource = new RawResourceDataSource(activity);

            try {
                dataSource.open(new DataSpec(soundUri));
                ExtractorMediaSource soundSource = new ExtractorMediaSource(soundUri, () -> dataSource, Mp3Extractor.FACTORY, null, null);
                simpleExoPlayer.prepare(soundSource);
                simpleExoPlayer.setPlayWhenReady(true);
                if (startListenTimestamp == 0) {
                    startListenTimestamp = System.currentTimeMillis();
                }
                if (runningMediaPlayerList.size() >= MULTI_LISTEN_NUMBER_LIMIT) {
                    stopRunningPlayer(0);
                }
            } catch (Exception e) {
                errorComponent.displayListenErrorSnackbar();
            }
            simpleExoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object o) {
                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
                }

                @Override
                public void onLoadingChanged(boolean b) {
                }

                @Override
                public void onPlayerStateChanged(boolean b, int state) {
                    //Media ended or we used the stop() method
                    if (state == ExoPlayer.STATE_ENDED || state == ExoPlayer.STATE_IDLE) {
                        releaseRunningPlayerById(mediaId);
                        emitter.onComplete();
                    }
                }

                @Override
                public void onRepeatModeChanged(int i) {
                }

                @Override
                public void onPlayerError(ExoPlaybackException e) {
                    errorComponent.displayListenErrorSnackbar();
                    emitter.onError(e);
                }

                @Override
                public void onPositionDiscontinuity() {
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                }
            });

            if (!contentRepository.isMultiListenEnabled() && !runningMediaPlayerList.isEmpty()) {
                stopRunningPlayer(0);
            }
            runningMediaPlayerList.add(new RunningPlayer(simpleExoPlayer, mediaId));
        });
    }

    @Override
    public void releaseAllRunningPlayer() {
        for (int i = 0; i < runningMediaPlayerList.size(); i++) {
            //stop() method will trigger the callback in SimpleExoPlayer listener with STATE_IDLE
            //The releasing of the player will be done in this callback
            stopRunningPlayer(i);
        }
    }

    @Override
    public boolean isPlayerCurrentlyRunning() {
        return !runningMediaPlayerList.isEmpty();
    }

    private void releaseRunningPlayerById(int mediaId) {
        for (int i = 0; i < runningMediaPlayerList.size(); i++) {
            if (runningMediaPlayerList.get(i).getMediaId() == mediaId) {
                releaseRunningPlayer(i);
            }
        }
    }

    private void releaseRunningPlayer(int position) {
        if (position < runningMediaPlayerList.size()) {
            RunningPlayer runningPlayer = runningMediaPlayerList.get(position);
            runningMediaPlayerList.remove(position);
            runningPlayer.getSimpleExoPlayer().release();
        }
        if (runningMediaPlayerList.isEmpty()) {
            contentRepository.increaseTotalReplyTime(System.currentTimeMillis() - startListenTimestamp);
            startListenTimestamp = 0;
        }
    }

    private void stopRunningPlayer(int position) {
        if (position < runningMediaPlayerList.size()) {
            //stop() method will call the callback in SimpleExoPlayer listener with STATE_IDLE
            runningMediaPlayerList.get(position).getSimpleExoPlayer().stop();
        }
    }
}

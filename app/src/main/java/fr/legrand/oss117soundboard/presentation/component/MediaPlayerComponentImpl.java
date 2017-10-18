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
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.entity.RunningPlayer;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 30/09/2017.
 */

@PerActivity
public class MediaPlayerComponentImpl implements MediaPlayerComponent {

    private static final int MULTI_LISTEN_NUMBER_LIMIT = 8;

    private BaseActivity activity;
    private SimpleExoPlayer mediaPlayer;

    private ContentRepository contentRepository;
    private ErrorComponent errorComponent;

    private List<RunningPlayer> runningMediaPlayerList;

    @Inject
    public MediaPlayerComponentImpl(BaseActivity activity, ContentRepository contentRepository, ErrorComponent errorComponent) {
        this.activity = activity;
        this.contentRepository = contentRepository;
        this.errorComponent = errorComponent;
        this.runningMediaPlayerList = new ArrayList<>();
    }

    @Override
    public void playSoundMedia(int mediaId) {
        if (!contentRepository.isMultiListenEnabled()) {
            releaseCurrentMediaPlayer();
        }
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(null);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector);

        Uri soundUri = RawResourceDataSource.buildRawResourceUri(mediaId);
        RawResourceDataSource dataSource = new RawResourceDataSource(activity);

        try {
            dataSource.open(new DataSpec(soundUri));
            ExtractorMediaSource soundSource = new ExtractorMediaSource(soundUri, () -> dataSource, Mp3Extractor.FACTORY, null, null);

            if (runningMediaPlayerList.size() >= MULTI_LISTEN_NUMBER_LIMIT) {
                removeRunningPlayerWithPosition(0);
            }
            mediaPlayer.addListener(new Player.EventListener() {
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
                    if (state == ExoPlayer.STATE_ENDED) {
                        removeRunningPlayer(mediaId);
                    }
                }

                @Override
                public void onRepeatModeChanged(int i) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException e) {
                    errorComponent.displayListenErrorSnackbar();
                }

                @Override
                public void onPositionDiscontinuity() {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }
            });
            mediaPlayer.prepare(soundSource);
            mediaPlayer.setPlayWhenReady(true);
            runningMediaPlayerList.add(new RunningPlayer(mediaPlayer, mediaId));
        } catch (Exception e) {
            errorComponent.displayListenErrorSnackbar();
        }

    }

    @Override
    public void releaseCurrentMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            runningMediaPlayerList.clear();
        }
    }

    @Override
    public void releaseAllRunningPlayer() {
        for (RunningPlayer runningPlayer : runningMediaPlayerList) {
            runningPlayer.getSimpleExoPlayer().release();
        }
        runningMediaPlayerList.clear();
    }

    private void removeRunningPlayer(int mediaId) {
        for (Iterator<RunningPlayer> iterator = runningMediaPlayerList.iterator(); iterator.hasNext(); ) {
            RunningPlayer runningPlayer = iterator.next();
            if (runningPlayer.getMediaId() == mediaId) {
                iterator.remove();
                releaseMediaPlayer(runningPlayer.getSimpleExoPlayer());
                return;
            }
        }
    }

    private void removeRunningPlayerWithPosition(int position) {
        if (position < runningMediaPlayerList.size()) {
            releaseMediaPlayer(runningMediaPlayerList.get(position).getSimpleExoPlayer());
            runningMediaPlayerList.remove(position);
        }
    }

    private void releaseMediaPlayer(SimpleExoPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}

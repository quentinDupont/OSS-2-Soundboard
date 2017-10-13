package fr.legrand.oss117soundboard.presentation.component;

import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 30/09/2017.
 */

@PerActivity
public class MediaPlayerComponentImpl implements MediaPlayerComponent {

    private BaseActivity activity;
    private SimpleExoPlayer mediaPlayer;

    @Inject
    public MediaPlayerComponentImpl(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void playSoundMedia(int mediaId) {
        releaseMediaPlayer();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(null);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mediaPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector);

        Uri soundUri = RawResourceDataSource.buildRawResourceUri(mediaId);
        RawResourceDataSource dataSource = new RawResourceDataSource(activity);

        try {
            dataSource.open(new DataSpec(soundUri));
            ExtractorMediaSource soundSource = new ExtractorMediaSource(soundUri, () -> dataSource, Mp3Extractor.FACTORY, null, null);

            mediaPlayer.prepare(soundSource);
            mediaPlayer.setPlayWhenReady(true);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

package fr.legrand.oss117soundboard.presentation.di.module;

import dagger.Module;
import dagger.Provides;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponentImpl;
import fr.legrand.oss117soundboard.presentation.component.TutorialComponent;
import fr.legrand.oss117soundboard.presentation.component.TutorialComponentImpl;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.navigator.listener.BaseNavigatorListener;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 12/09/2017.
 */
@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    BaseActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    BaseNavigatorListener baseNavigatorListener() {
        return (BaseNavigatorListener) activity;
    }

    @Provides
    @PerActivity
    TutorialComponent tutorialComponent(TutorialComponentImpl tutorialComponent){
        return tutorialComponent;
    }

    @Provides
    @PerActivity
    MediaPlayerComponent mediaPlayerComponent(MediaPlayerComponentImpl mediaPlayerComponent){
        return mediaPlayerComponent;
    }
}

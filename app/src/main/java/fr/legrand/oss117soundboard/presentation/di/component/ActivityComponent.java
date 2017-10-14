package fr.legrand.oss117soundboard.presentation.di.component;

import dagger.Component;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.di.module.ActivityModule;
import fr.legrand.oss117soundboard.presentation.navigator.listener.BaseNavigatorListener;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.MainActivity;
import fr.legrand.oss117soundboard.presentation.ui.fragment.ReplyListFragment;

/**
 * Created by Benjamin on 12/09/2017.
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    ContentRepository contentRepository();
    BaseNavigatorListener baseNavigatorListener();
    MediaPlayerComponent mediaPlayerComponent();
    BaseActivity baseActivity();

    void inject(MainActivity mainActivity);
}

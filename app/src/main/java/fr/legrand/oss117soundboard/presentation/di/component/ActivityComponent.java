package fr.legrand.oss117soundboard.presentation.di.component;

import dagger.Component;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.di.module.ActivityModule;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.MainActivity;
import fr.legrand.oss117soundboard.presentation.ui.fragment.HomeFragment;

/**
 * Created by Benjamin on 12/09/2017.
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);
}

package fr.legrand.oss117soundboard.presentation.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.di.module.ApplicationModule;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 12/09/2017.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
    ContentRepository contentRepository();
}

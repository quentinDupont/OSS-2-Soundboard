package fr.legrand.oss117soundboard.presentation;

import android.app.Application;

import fr.legrand.oss117soundboard.presentation.di.component.ApplicationComponent;
import fr.legrand.oss117soundboard.presentation.di.component.DaggerApplicationComponent;
import fr.legrand.oss117soundboard.presentation.di.module.ApplicationModule;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class OSSApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}

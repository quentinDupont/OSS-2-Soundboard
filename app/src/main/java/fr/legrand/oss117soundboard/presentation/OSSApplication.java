package fr.legrand.oss117soundboard.presentation;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

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
        FlowManager.init(this);
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

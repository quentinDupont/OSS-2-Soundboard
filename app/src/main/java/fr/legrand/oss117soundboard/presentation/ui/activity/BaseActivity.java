package fr.legrand.oss117soundboard.presentation.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import fr.legrand.oss117soundboard.presentation.OSSApplication;
import fr.legrand.oss117soundboard.presentation.di.component.ActivityComponent;
import fr.legrand.oss117soundboard.presentation.di.component.DaggerActivityComponent;
import fr.legrand.oss117soundboard.presentation.di.module.ActivityModule;

/**
 * Created by Benjamin on 30/09/2017.
 */

public abstract class BaseActivity extends Activity {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }

    private void initializeInjector() {
        activityComponent = DaggerActivityComponent.builder().applicationComponent(((OSSApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this)).build();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public abstract View getRootView();

}

package fr.legrand.oss117soundboard.presentation.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.navigator.MainNavigator;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class MainActivity extends BaseActivity{

    @Inject
    MainNavigator mainNavigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);

        mainNavigator.displayHomeFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainNavigator.displayHomeFragment();
    }
}

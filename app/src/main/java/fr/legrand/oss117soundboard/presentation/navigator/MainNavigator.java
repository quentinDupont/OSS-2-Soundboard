package fr.legrand.oss117soundboard.presentation.navigator;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 30/09/2017.
 */
@PerActivity
public class MainNavigator implements BaseNavigator {

    private FragmentManager fragmentManager;
    private BaseActivity baseActivity;

    @Inject
    public MainNavigator(BaseActivity activity) {
        this.fragmentManager = activity.getFragmentManager();
        this.baseActivity = activity;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public void launchBrowsingApp() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseActivity.getString(R.string.easter_egg_url)));
        baseActivity.startActivity(browserIntent);
    }

}

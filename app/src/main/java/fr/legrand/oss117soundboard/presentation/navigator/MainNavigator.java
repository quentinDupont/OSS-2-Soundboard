package fr.legrand.oss117soundboard.presentation.navigator;

import android.app.FragmentManager;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.fragment.HomeFragment;

/**
 * Created by Benjamin on 30/09/2017.
 */
@PerActivity
public class MainNavigator implements BaseNavigator {

    private FragmentManager fragmentManager;

    @Inject
    public MainNavigator(BaseActivity activity) {
        this.fragmentManager = activity.getFragmentManager();
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

    public void displayHomeFragment() {
        fragmentManager.beginTransaction().replace(R.id.activity_main_fragment_container, HomeFragment.newInstance()).commit();
    }
}

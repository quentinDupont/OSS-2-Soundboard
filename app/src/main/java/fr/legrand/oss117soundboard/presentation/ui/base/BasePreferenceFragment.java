package fr.legrand.oss117soundboard.presentation.ui.base;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import fr.legrand.oss117soundboard.presentation.di.component.DaggerFragmentComponent;
import fr.legrand.oss117soundboard.presentation.ui.base.BaseActivity;

/**
 * Created by Benjamin on 13/10/2017.
 */

public class BasePreferenceFragment extends PreferenceFragment {

    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }

    private void initializeInjector() {
        fragmentComponent = DaggerFragmentComponent.builder().activityComponent(((BaseActivity) getActivity()).getActivityComponent()).build();
    }

    public FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }
}

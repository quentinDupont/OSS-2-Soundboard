package fr.legrand.oss117soundboard.presentation.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.legrand.oss117soundboard.presentation.di.component.DaggerFragmentComponent;
import fr.legrand.oss117soundboard.presentation.di.component.FragmentComponent;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 13/10/2017.
 */

public class BaseFragment extends Fragment {

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

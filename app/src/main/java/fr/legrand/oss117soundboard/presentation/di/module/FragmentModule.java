package fr.legrand.oss117soundboard.presentation.di.module;

import dagger.Module;
import dagger.Provides;
import fr.legrand.oss117soundboard.presentation.di.PerFragment;
import fr.legrand.oss117soundboard.presentation.ui.fragment.BaseFragment;

/**
 * Created by Benjamin on 12/09/2017.
 */
@Module
public class FragmentModule {

    private final BaseFragment fragment;

    public FragmentModule(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    BaseFragment fragment() {
        return this.fragment;
    }
}

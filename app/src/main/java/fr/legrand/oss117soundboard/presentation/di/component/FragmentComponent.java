package fr.legrand.oss117soundboard.presentation.di.component;

import dagger.Component;
import fr.legrand.oss117soundboard.presentation.di.PerFragment;
import fr.legrand.oss117soundboard.presentation.di.module.FragmentModule;
import fr.legrand.oss117soundboard.presentation.ui.fragment.ReplyListFragment;

/**
 * Created by Benjamin on 12/09/2017.
 */
@PerFragment
@Component(modules = FragmentModule.class, dependencies = ActivityComponent.class)
public interface FragmentComponent {
    void inject(ReplyListFragment replyListFragment);
}

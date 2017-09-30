package fr.legrand.oss117soundboard.presentation.presenter;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.HomeView;

/**
 * Created by Benjamin on 30/09/2017.
 */
@PerActivity
public class HomePresenter implements BasePresenter {

    private HomeView homeView;
    private ContentRepository contentRepository;

    @Inject
    public HomePresenter(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
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

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }
}

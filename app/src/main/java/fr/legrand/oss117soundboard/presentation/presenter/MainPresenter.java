package fr.legrand.oss117soundboard.presentation.presenter;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.MainView;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Benjamin on 30/09/2017.
 */

@PerActivity
public class MainPresenter implements BasePresenter {

    private ContentRepository contentRepository;
    private MediaPlayerComponent mediaPlayerComponent;
    private MainView mainView;

    @Inject
    public MainPresenter(ContentRepository contentRepository, MediaPlayerComponent mediaPlayerComponent) {
        this.contentRepository = contentRepository;
        this.mediaPlayerComponent = mediaPlayerComponent;
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
        mediaPlayerComponent.releaseCurrentMediaPlayer();
    }

    @Override
    public void onDestroy() {

    }

    public void initAllReply() {
        contentRepository.initAllReply().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mainView.requestDisplayReplyListFragment();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}

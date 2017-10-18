package fr.legrand.oss117soundboard.presentation.presenter;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.data.exception.NoListenedReplyException;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.ParameterView;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Benjamin on 17/10/2017.
 */

public class ParameterPresenter implements BasePresenter {

    private ContentRepository contentRepository;
    private MediaPlayerComponent mediaPlayerComponent;

    private ParameterView parameterView;

    @Inject
    public ParameterPresenter(ContentRepository contentRepository, MediaPlayerComponent mediaPlayerComponent) {
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

    }

    @Override
    public void onDestroy() {

    }

    public void setParameterView(ParameterView parameterView) {
        this.parameterView = parameterView;
    }

    public void updateMultiListenParameter(boolean multiListen) {
        contentRepository.updateMultiListenParameter(multiListen).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mediaPlayerComponent.releaseAllRunningPlayer();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void checkMultiListenEnabled() {
        parameterView.updateSwitch(contentRepository.isMultiListenEnabled());
    }

    public void getMostListenedReply() {
        contentRepository.getMostListenedReply().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reply>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Reply reply) {
                        parameterView.updateMostListenedReply(new ReplyViewModel(reply));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof NoListenedReplyException) {
                            parameterView.updateMostListenedReply(null);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

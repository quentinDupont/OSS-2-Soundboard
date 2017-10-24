package fr.legrand.oss117soundboard.presentation.presenter;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.data.exception.NoListenedReplyException;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent;
import fr.legrand.oss117soundboard.presentation.navigator.listener.BaseNavigatorListener;
import fr.legrand.oss117soundboard.presentation.navigator.listener.MainNavigatorListener;
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

    private final static long MS_TO_S = 1000;
    private final static long MS_TO_M = 60 * 1000;
    private final static long MS_TO_H = 60 * 60 * 1000;
    private final static long M_S_MODULO_VALUE = 60;

    private ContentRepository contentRepository;
    private MediaPlayerComponent mediaPlayerComponent;
    private MainNavigatorListener mainNavigatorListener;

    private ParameterView parameterView;

    @Inject
    public ParameterPresenter(BaseNavigatorListener baseNavigatorListener, ContentRepository contentRepository, MediaPlayerComponent mediaPlayerComponent) {
        this.mainNavigatorListener = (MainNavigatorListener) baseNavigatorListener;
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

    public void getTotalReplyTime() {
        contentRepository.getTotalReplyTime().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long totalTime) {
                        parameterView.updateTotalReplyTime(totalTime / MS_TO_H, totalTime / MS_TO_M % M_S_MODULO_VALUE, totalTime / MS_TO_S % M_S_MODULO_VALUE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void listenToRandomReply() {
        contentRepository.getRandomReplyIdToListen().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer replyId) {
                        mainNavigatorListener.onReplyClicked();
                        listenToReply(replyId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void listenToReply(int replyId) {
        mediaPlayerComponent.playSoundMedia(replyId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (!mediaPlayerComponent.isPlayerCurrentlyRunning()) {
                            mainNavigatorListener.onReplyListened();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}

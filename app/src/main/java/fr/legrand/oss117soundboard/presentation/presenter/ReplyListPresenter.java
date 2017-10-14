package fr.legrand.oss117soundboard.presentation.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.di.PerFragment;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.ReplyListView;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Benjamin on 30/09/2017.
 */
@PerFragment
public class ReplyListPresenter implements BasePresenter {

    private ReplyListView replyListView;
    private ContentRepository contentRepository;

    private MediaPlayerComponent mediaPlayerComponent;

    @Inject
    public ReplyListPresenter(ContentRepository contentRepository, MediaPlayerComponent mediaPlayerComponent) {
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

    public void getAllReplyWithSearch(String search, boolean fromFavorite) {
        contentRepository.getReplyWithSearch(search, fromFavorite).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Reply>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Reply> replies) {
                        if (replies.isEmpty()) {
                            replyListView.displayPlaceholder();
                        } else {
                            List<ReplyViewModel> replyViewModelList = new ArrayList<>();
                            for (Reply reply : replies) {
                                replyViewModelList.add(new ReplyViewModel(reply));
                            }
                            replyListView.displayReplyList();
                            replyListView.updateSearchReplyList(replyViewModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getAllReply(boolean fromFavorite) {
        contentRepository.getAllReply(fromFavorite).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Reply>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Reply> replies) {
                        if (replies.isEmpty()) {
                            replyListView.displayPlaceholder();
                        } else {
                            List<ReplyViewModel> replyViewModelList = new ArrayList<>();
                            for (Reply reply : replies) {
                                replyViewModelList.add(new ReplyViewModel(reply));
                            }
                            replyListView.displayReplyList();
                            replyListView.updateSearchReplyList(replyViewModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateFavoriteReply(int replyId, boolean addToFavorite) {
        contentRepository.updateFavoriteReply(replyId, addToFavorite).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reply>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Reply reply) {
                        replyListView.updateFavoriteReply(new ReplyViewModel(reply));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setReplyListView(ReplyListView replyListView) {
        this.replyListView = replyListView;
    }

    public void listenToReply(int replyId) {
        mediaPlayerComponent.playSoundMedia(replyId);
    }


}

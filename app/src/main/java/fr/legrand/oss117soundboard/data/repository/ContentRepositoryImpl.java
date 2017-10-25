package fr.legrand.oss117soundboard.data.repository;

import android.content.Context;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;
import javax.inject.Singleton;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.data.manager.db.DatabaseManager;
import fr.legrand.oss117soundboard.data.manager.file.FileManager;
import fr.legrand.oss117soundboard.data.manager.sharedpref.SharedPrefManager;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Benjamin on 30/09/2017.
 */
@Singleton
public class ContentRepositoryImpl implements ContentRepository {

    private DatabaseManager databaseManager;
    private SharedPrefManager sharedPrefManager;
    private FileManager fileManager;
    private Context context;

    @Inject
    public ContentRepositoryImpl(DatabaseManager databaseManager, SharedPrefManager sharedPrefManager, FileManager fileManager, Context context) {
        this.databaseManager = databaseManager;
        this.sharedPrefManager = sharedPrefManager;
        this.fileManager = fileManager;
        this.context = context;
    }

    @Override
    public Observable<List<Reply>> getReplyWithSearch(String search, boolean fromFavorite) {
        return Observable.defer(() -> {
                    List<Reply> replyList = databaseManager.getReplyWithSearch(search, fromFavorite);
                    if (sharedPrefManager.getReplySort() == null || sharedPrefManager.getReplySort().equals(context.getString(R.string.alphabetical_order))) {
                        Collections.sort(replyList, (r1, r2) -> r1.getName().compareTo(r2.getName()));
                    } else if (sharedPrefManager.getReplySort().equals(context.getString(R.string.random_order))) {
                        Collections.shuffle(replyList);
                    }
                    return Observable.just(replyList);
                }
        );
    }

    @Override
    public Completable initAllReply() {
        return Completable.defer(() ->
                Completable.fromCallable(() ->
                        databaseManager.getAllReply())
                        .onErrorResumeNext(throwable -> {
                            List<Reply> replyList = fileManager.buildReplyListFromResource();
                            databaseManager.saveReplyList(replyList);
                            return Completable.complete();
                        })
        );
    }

    @Override
    public Observable<Reply> updateFavoriteReply(int replyId, boolean addToFavorite) {
        return Observable.defer(() -> Observable.just(databaseManager.updateFavoriteReply(replyId, addToFavorite)));
    }

    @Override
    public Observable<List<Reply>> getAllReply(boolean fromFavorite) {
        return Observable.defer(() -> {
            List<Reply> replyList = databaseManager.getAllReply(fromFavorite);
            if (sharedPrefManager.getReplySort() == null || sharedPrefManager.getReplySort().equals(context.getString(R.string.alphabetical_order))) {
                Collections.sort(replyList, (r1, r2) -> r1.getName().compareTo(r2.getName()));
            } else if (sharedPrefManager.getReplySort().equals(context.getString(R.string.random_order))) {
                Collections.shuffle(replyList);
            }
            return Observable.just(replyList);
        });
    }

    @Override
    public Completable updateMultiListenParameter(boolean multiListen) {
        sharedPrefManager.setMultiListenEnabled(multiListen);
        return Completable.complete();
    }

    @Override
    public boolean isMultiListenEnabled() {
        return sharedPrefManager.isMultiListenEnabled();
    }

    @Override
    public Observable<Reply> getMostListenedReply() {
        return Observable.fromCallable(() -> databaseManager.getMostListenedReply());
    }

    @Override
    public Completable incrementReplyListenCount(int replyId) {
        return Completable.defer(() -> {
            databaseManager.incrementReplyListenCount(replyId);
            return Completable.complete();
        });
    }

    @Override
    public void increaseTotalReplyTime(long duration) {
        sharedPrefManager.increaseTotalReplyTime(duration);
    }

    @Override
    public Observable<Long> getTotalReplyTime() {
        return Observable.fromCallable(() -> sharedPrefManager.getTotalReplyTime());
    }

    @Override
    public Observable<Integer> getRandomReplyIdToListen() {
        return Observable.defer(() -> {
            List<Reply> replyList = databaseManager.getAllReply();
            int randomNumber = ThreadLocalRandom.current().nextInt(0, replyList.size() + 1);
            int replyId = replyList.get(randomNumber).getId();
            databaseManager.incrementReplyListenCount(replyId);
            return Observable.just(replyId);
        });
    }

    @Override
    public Completable updateReplySort(String replySort) {
        return Completable.defer(() -> {
            sharedPrefManager.setReplySort(replySort);
            return Completable.complete();
        });
    }

    @Override
    public Observable<String> getReplySort() {
        return Observable.fromCallable(() -> sharedPrefManager.getReplySort());
    }
}

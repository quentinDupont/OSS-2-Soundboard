package fr.legrand.oss117soundboard.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    @Inject
    public ContentRepositoryImpl(DatabaseManager databaseManager, SharedPrefManager sharedPrefManager, FileManager fileManager) {
        this.databaseManager = databaseManager;
        this.sharedPrefManager = sharedPrefManager;
        this.fileManager = fileManager;
    }

    @Override
    public Observable<List<Reply>> getReplyWithSearch(String search, boolean fromFavorite) {
        return Observable.defer(() -> Observable.just(databaseManager.getReplyWithSearch(search, fromFavorite)));
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
        return Observable.defer(() -> Observable.just(databaseManager.getAllReply(fromFavorite)));
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
}

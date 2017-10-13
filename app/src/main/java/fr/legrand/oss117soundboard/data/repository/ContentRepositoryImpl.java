package fr.legrand.oss117soundboard.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.data.manager.db.DatabaseManager;
import fr.legrand.oss117soundboard.data.manager.file.FileManager;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Benjamin on 30/09/2017.
 */
@Singleton
public class ContentRepositoryImpl implements ContentRepository {

    private DatabaseManager databaseManager;
    private FileManager fileManager;

    @Inject
    public ContentRepositoryImpl(DatabaseManager databaseManager, FileManager fileManager) {
        this.databaseManager = databaseManager;
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
                        .doOnError(throwable ->
                                databaseManager.saveReplyList(fileManager.buildReplyListFromResource())));
    }

    @Override
    public Observable<Reply> updateFavoriteReply(int replyId, boolean addToFavorite) {
        return Observable.defer(() -> Observable.just(databaseManager.updateFavoriteReply(replyId, addToFavorite)));
    }

    @Override
    public Observable<List<Reply>> getAllReply(boolean fromFavorite) {
        return Observable.defer(() -> Observable.just(databaseManager.getAllReply(fromFavorite)));
    }
}

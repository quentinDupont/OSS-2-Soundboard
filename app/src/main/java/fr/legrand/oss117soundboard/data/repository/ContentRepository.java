package fr.legrand.oss117soundboard.data.repository;

import java.util.List;

import fr.legrand.oss117soundboard.data.entity.Reply;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface ContentRepository {
    Observable<List<Reply>> getReplyWithSearch(String search, boolean fromFavorite);

    Completable initAllReply();

    Observable<Reply> updateFavoriteReply(int replyId, boolean addToFavorite);

    Observable<List<Reply>> getAllReply(boolean fromFavorite);

    boolean isMultiListenEnabled();

}

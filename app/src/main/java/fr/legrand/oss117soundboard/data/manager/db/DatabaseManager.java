package fr.legrand.oss117soundboard.data.manager.db;

import com.raizlabs.android.dbflow.annotation.Database;

import java.util.List;

import fr.legrand.oss117soundboard.data.entity.Reply;
import io.reactivex.Observable;

/**
 * Created by Benjamin on 30/09/2017.
 */
@Database(name = DatabaseManager.NAME, version = DatabaseManager.VERSION)
public interface DatabaseManager {
    String NAME = "OSSDatabase";
    int VERSION = 1;

    List<Reply> getReplyWithSearch(String search, boolean fromFavorite);
    List<Reply> getAllReply(boolean fromFavorite);
    List<Reply> getAllReply();

    Reply updateFavoriteReply(int replyId, boolean addToFavorite);

    void saveReplyList(List<Reply> replyList);

    Reply getMostListenedReply();

    void incrementReplyListenCount(int replyId);
}

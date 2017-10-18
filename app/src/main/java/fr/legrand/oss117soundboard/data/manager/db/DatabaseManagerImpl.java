package fr.legrand.oss117soundboard.data.manager.db;

import android.database.Cursor;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.data.entity.Reply_Table;
import fr.legrand.oss117soundboard.data.exception.NoListenedReplyException;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class DatabaseManagerImpl implements DatabaseManager {

    @Inject
    public DatabaseManagerImpl() {
    }

    @Override
    public List<Reply> getReplyWithSearch(String search, boolean fromFavorite) {
        OperatorGroup searchOperator = OperatorGroup.clause().and(Reply_Table.description.like(String.format("%1$s%2$s%3$s", "%", search, "%")))
                .or(Reply_Table.name.like(String.format("%1$s%2$s%3$s", "%", search, "%")));
        return SQLite.select().from(Reply.class)
                .where(searchOperator)
                .and(Reply_Table.favorite.is(fromFavorite))
                .orderBy(Reply_Table.name, true)
                .queryList();
    }

    @Override
    public List<Reply> getAllReply(boolean fromFavorite) {
        return SQLite.select().from(Reply.class)
                .where(Reply_Table.favorite.is(fromFavorite))
                .orderBy(Reply_Table.name, true)
                .queryList();
    }

    @Override
    public List<Reply> getAllReply() {
        List<Reply> replies = SQLite.select().from(Reply.class).orderBy(Reply_Table.name, true).queryList();
        if (replies.isEmpty()) {
            throw new RuntimeException();
        } else {
            return replies;
        }
    }

    @Override
    public Reply updateFavoriteReply(int replyId, boolean addToFavorite) {
        Reply reply = SQLite.select().from(Reply.class).where(Reply_Table.id.eq(replyId)).querySingle();
        if (reply != null) {
            reply.setFavorite(addToFavorite);
            reply.update();
        } else {
            throw new RuntimeException();
        }
        return reply;
    }

    @Override
    public void saveReplyList(List<Reply> replyList) {
        FlowManager.getDatabase(DatabaseManager.class).executeTransaction(databaseWrapper -> {
            for (Reply reply : replyList) {
                reply.save();
            }
        });
    }

    @Override
    public Reply getMostListenedReply() {
        Cursor cursor = SQLite.select(Method.max(Reply_Table.listenCount).as("max")).from(Reply.class).query();
        if (cursor != null && cursor.moveToFirst()) {
            int max = cursor.getInt(0);
            cursor.close();
            Reply reply = SQLite.select().from(Reply.class).where(Reply_Table.listenCount.eq(max)).querySingle();
            if (reply == null || reply.getListenCount() == 0) {
                throw new NoListenedReplyException();
            } else {
                return reply;
            }
        } else {
            throw new NoListenedReplyException();
        }
    }

    @Override
    public void incrementReplyListenCount(int replyId) {
        Reply reply = SQLite.select().from(Reply.class).where(Reply_Table.id.eq(replyId)).querySingle();
        if (reply != null) {
            reply.setListenCount(reply.getListenCount() + 1);
            reply.save();
        }
    }
}

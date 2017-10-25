package fr.legrand.oss117soundboard.data.manager.sharedpref;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface SharedPrefManager {
    boolean isMultiListenEnabled();

    void setMultiListenEnabled(boolean multiListen);

    void increaseTotalReplyTime(long duration);

    long getTotalReplyTime();

    String getReplySort();

    void setReplySort(String replySort);
}

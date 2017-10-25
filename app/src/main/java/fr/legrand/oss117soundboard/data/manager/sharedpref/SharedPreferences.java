package fr.legrand.oss117soundboard.data.manager.sharedpref;

import com.cocosw.favor.AllFavor;

/**
 * Created by Benjamin on 30/09/2017.
 */

@AllFavor
public interface SharedPreferences {

    boolean isMultiListenEnabled();

    void setMultiListenEnabled(boolean multiListenEnabled);

    long getTotalReplyTime();

    void setTotalReplyTime(long l);

    String getReplySort();

    void setReplySort(String replySort);
}

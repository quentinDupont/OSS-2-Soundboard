package fr.legrand.oss117soundboard.data.manager.sharedpref;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Benjamin on 30/09/2017.
 */

@Singleton
public class SharedPrefManagerImpl implements SharedPrefManager {

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefManagerImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isMultiListenEnabled() {
        return sharedPreferences.isMultiListenEnabled();
    }

    @Override
    public void setMultiListenEnabled(boolean multiListen) {
        sharedPreferences.setMultiListenEnabled(multiListen);
    }

    @Override
    public void increaseTotalReplyTime(long duration) {
        sharedPreferences.setTotalReplyTime(sharedPreferences.getTotalReplyTime() + duration);
    }

    @Override
    public long getTotalReplyTime() {
        return sharedPreferences.getTotalReplyTime();
    }

    @Override
    public String getReplySort() {
        return sharedPreferences.getReplySort();
    }

    @Override
    public void setReplySort(String replySort) {
        sharedPreferences.setReplySort(replySort);
    }
}

package fr.legrand.oss117soundboard.data.manager.sharedpref;

import javax.inject.Singleton;

/**
 * Created by Benjamin on 30/09/2017.
 */

@Singleton
public class SharedPrefManagerImpl implements SharedPrefManager {

    private SharedPreferences sharedPreferences;

    public SharedPrefManagerImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isMultiListenEnabled() {
        return sharedPreferences.isMultiListenEnabled();
    }
}

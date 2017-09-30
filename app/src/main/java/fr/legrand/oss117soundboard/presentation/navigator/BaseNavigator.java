package fr.legrand.oss117soundboard.presentation.navigator;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface BaseNavigator {
    void onStart();
    void onPause();
    void onResume();
    void onStop();
    void onDestroy();
}

package fr.legrand.oss117soundboard.presentation.presenter;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface BasePresenter {
    void onStart();
    void onPause();
    void onResume();
    void onStop();
    void onDestroy();
}

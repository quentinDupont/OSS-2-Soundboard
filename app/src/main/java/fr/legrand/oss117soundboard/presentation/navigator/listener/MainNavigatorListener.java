package fr.legrand.oss117soundboard.presentation.navigator.listener;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface MainNavigatorListener extends BaseNavigatorListener{
    void updateAllLayout();

    void onReplyListened();
    void onReplyClicked();
}

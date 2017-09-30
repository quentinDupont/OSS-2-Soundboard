package fr.legrand.oss117soundboard.presentation.component;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface TutorialComponent {
    void startMainTutorial(int focusedViewId, String title, String content, OnShowcaseEventListener eventListener);
}

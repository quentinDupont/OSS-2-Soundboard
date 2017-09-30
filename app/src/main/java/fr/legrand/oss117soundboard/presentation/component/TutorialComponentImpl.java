package fr.legrand.oss117soundboard.presentation.component;

import android.app.Activity;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.presentation.di.PerActivity;

/**
 * Created by Benjamin on 30/09/2017.
 */
@PerActivity
public class TutorialComponentImpl implements TutorialComponent {

    private Activity activity;

    @Inject
    public TutorialComponentImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void startMainTutorial(int focusedViewId, String title, String content, OnShowcaseEventListener eventListener) {
        ViewTarget target = new ViewTarget(focusedViewId, activity);
        ShowcaseView showcaseView = new ShowcaseView.Builder(activity)
                .setTarget(target)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(R.style.ShowcaseView)
                .setShowcaseEventListener(eventListener)
                .build();
        showcaseView.show();
    }
}

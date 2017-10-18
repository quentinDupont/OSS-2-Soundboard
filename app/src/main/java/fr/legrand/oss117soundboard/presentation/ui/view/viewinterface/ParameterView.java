package fr.legrand.oss117soundboard.presentation.ui.view.viewinterface;

import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 17/10/2017.
 */

public interface ParameterView {
    void updateSwitch(boolean checked);

    void updateMostListenedReply(ReplyViewModel replyViewModel);

}

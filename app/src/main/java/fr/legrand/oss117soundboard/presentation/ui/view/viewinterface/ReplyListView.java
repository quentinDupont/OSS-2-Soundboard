package fr.legrand.oss117soundboard.presentation.ui.view.viewinterface;

import java.util.List;

import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface ReplyListView {
    void updateSearchReplyList(List<ReplyViewModel> replyViewModelList);

    void displayPlaceholder();

    void displayReplyList();

    void updateFavoriteReply(ReplyViewModel replyViewModel);

}

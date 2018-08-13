package fr.legrand.oss117soundboard.presentation.ui.main.ui;


import android.app.Fragment;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.ui.base.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.base.BasePreferenceFragment;
import fr.legrand.oss117soundboard.presentation.ui.settings.ParameterFragment;
import fr.legrand.oss117soundboard.presentation.ui.reply.ReplyListFragment;
import fr.legrand.oss117soundboard.presentation.ui.listener.OnListenReplyListener;
import fr.legrand.oss117soundboard.presentation.ui.listener.OnSearchListener;

/**
 * Created by Benjamin on 12/10/2017.
 */
public class ReplyPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FRAGMENT_COUNT = 3;

    private static final int FAVORITE_LIST_POSITION = 0;
    private static final int REPLY_LIST_POSITION = 1;
    private static final int PARAMETER_POSITION = 2;

    private int currentPosition;

    private List<String> pagerTitles;
    private List<OnSearchListener> searchListenerList;
    private List<OnListenReplyListener> listenListenerList;

    @Inject
    public ReplyPagerAdapter(Context context, BaseActivity activity) {
        super(activity.getFragmentManager());
        searchListenerList = new ArrayList<>();
        listenListenerList = new ArrayList<>();
        pagerTitles = new ArrayList<>();
        pagerTitles.add(FAVORITE_LIST_POSITION, context.getString(R.string.favorite_list_title));
        pagerTitles.add(REPLY_LIST_POSITION, context.getString(R.string.reply_list_title));
        pagerTitles.add(PARAMETER_POSITION, context.getString(R.string.parameter_title));
    }

    @Override
    public Fragment getItem(int position) {
        //No need to handle removal from list for now because all fragments are instantiated at the beginning
        switch (position) {
            case FAVORITE_LIST_POSITION:
                BaseFragment favoriteFragment = ReplyListFragment.newInstance(true);
                if (favoriteFragment instanceof OnSearchListener) {
                    searchListenerList.add((OnSearchListener) favoriteFragment);
                }
                return favoriteFragment;
            case REPLY_LIST_POSITION:
                BaseFragment replyFragment = ReplyListFragment.newInstance(false);
                if (replyFragment instanceof OnSearchListener) {
                    searchListenerList.add((OnSearchListener) replyFragment);
                }
                return replyFragment;
            case PARAMETER_POSITION:
                BasePreferenceFragment parameterFragment = ParameterFragment.newInstance();
                if (parameterFragment instanceof OnListenReplyListener) {
                    listenListenerList.add((OnListenReplyListener) parameterFragment);
                }
                return parameterFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitles.get(position);
    }

    public void onSearch(String search, boolean updateCurrent) {
        for (int i = 0; i < searchListenerList.size(); i++) {
            //No need to handle removal from list for now because all fragments are instantiated at the beginning
            if (i != currentPosition || updateCurrent) {
                searchListenerList.get(i).onSearch(search);
            }
        }
    }

    public void onSearchCurrent(String search) {
        for (int i = 0; i < searchListenerList.size(); i++) {
            //No need to handle removal from list for now because all fragments are instantiated at the beginning
            if (i == currentPosition) {
                searchListenerList.get(i).onSearch(search);
            }
        }
    }

    public void onReplyListened() {
        for (int i = 0; i < listenListenerList.size(); i++) {
            //No need to handle removal from list for now because all fragments are instantiated at the beginning
            listenListenerList.get(i).onReplyListened();
        }
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void onReplyClicked() {
        for (int i = 0; i < listenListenerList.size(); i++) {
            //No need to handle removal from list for now because all fragments are instantiated at the beginning
            listenListenerList.get(i).onReplyClicked();
        }
    }


}

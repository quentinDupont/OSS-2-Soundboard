package fr.legrand.oss117soundboard.presentation.adapter;


import android.app.Fragment;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.fragment.BaseFragment;
import fr.legrand.oss117soundboard.presentation.ui.fragment.ParameterFragment;
import fr.legrand.oss117soundboard.presentation.ui.fragment.ReplyListFragment;
import fr.legrand.oss117soundboard.presentation.ui.listener.OnSearchListener;

/**
 * Created by Benjamin on 12/10/2017.
 */
@PerActivity
public class ReplyPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FRAGMENT_COUNT = 3;

    private static final int FAVORITE_LIST_POSITION = 0;
    private static final int REPLY_LIST_POSITION = 1;
    private static final int PARAMETER_POSITION = 2;

    private int currentPosition;

    private List<String> pagerTitles;
    private List<OnSearchListener> searchListenerList;

    @Inject
    public ReplyPagerAdapter(Context context, BaseActivity activity) {
        super(activity.getFragmentManager());
        searchListenerList = new ArrayList<>();
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
                return ParameterFragment.newInstance();
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

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}

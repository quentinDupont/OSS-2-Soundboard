package fr.legrand.oss117soundboard.presentation.ui.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.presenter.ParameterPresenter;
import fr.legrand.oss117soundboard.presentation.ui.listener.OnListenReplyListener;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.ParameterView;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 17/10/2017.
 */

public class ParameterFragment extends BasePreferenceFragment implements ParameterView, OnListenReplyListener {

    @Inject
    ParameterPresenter parameterPresenter;

    private SwitchPreference multiListenPreference;
    private ListPreference replySortPreference;
    private Preference mostListenedReplyPreference;
    private Preference totalReplyTimePreference;
    private Preference randomReplyPreference;

    public static ParameterFragment newInstance() {

        Bundle args = new Bundle();

        ParameterFragment fragment = new ParameterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getFragmentComponent().inject(this);
        parameterPresenter.setParameterView(this);
        initializePreferences();
    }

    @Override
    public void onStart() {
        super.onStart();
        parameterPresenter.checkMultiListenEnabled();
        parameterPresenter.getMostListenedReply();
        parameterPresenter.getTotalReplyTime();
        parameterPresenter.getReplySort();
    }

    @Override
    public void updateSwitch(boolean checked) {
        multiListenPreference.setChecked(checked);
    }

    @Override
    public void updateMostListenedReply(ReplyViewModel replyViewModel) {
        if (replyViewModel != null) {
            mostListenedReplyPreference.setSummary(replyViewModel.getMostListenedText());
        } else {
            mostListenedReplyPreference.setSummary(getString(R.string.no_listened_reply));
        }
    }

    @Override
    public void updateReplySort(String replySort) {
        replySortPreference.setSummary(replySort);
    }

    @Override
    public void updateTotalReplyTime(long hours, long minutes, long seconds) {
        totalReplyTimePreference.setSummary(getString(R.string.total_reply_time_text, hours, minutes, seconds));
    }

    @Override
    public void onReplyClicked() {
        parameterPresenter.getMostListenedReply();
    }

    @Override
    public void onReplyListened() {
        parameterPresenter.getTotalReplyTime();
    }

    private void initializePreferences() {
        multiListenPreference = (SwitchPreference) this.getPreferenceManager().findPreference(getString(R.string.multi_listen_preference_key));
        replySortPreference = (ListPreference) this.getPreferenceManager().findPreference(getString(R.string.reply_sort_preference_key));
        mostListenedReplyPreference = this.getPreferenceManager().findPreference(getString(R.string.most_listened_reply_preference_key));
        totalReplyTimePreference = this.getPreferenceManager().findPreference(getString(R.string.total_reply_time_preference_key));
        randomReplyPreference = this.getPreferenceManager().findPreference(getString(R.string.random_reply_preference_key));

        multiListenPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            parameterPresenter.updateMultiListenParameter((boolean) newValue);
            return true;
        });

        randomReplyPreference.setOnPreferenceClickListener(preference -> {
            parameterPresenter.listenToRandomReply();
            return true;
        });
        replySortPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            parameterPresenter.updateReplySort((String) newValue);
            replySortPreference.setSummary((String) newValue);
            return true;
        });
    }
}

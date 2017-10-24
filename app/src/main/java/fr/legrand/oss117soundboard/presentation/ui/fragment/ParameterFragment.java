package fr.legrand.oss117soundboard.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.presenter.ParameterPresenter;
import fr.legrand.oss117soundboard.presentation.ui.listener.OnListenReplyListener;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.ParameterView;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 17/10/2017.
 */

public class ParameterFragment extends BaseFragment implements ParameterView, OnListenReplyListener {

    @Inject
    ParameterPresenter parameterPresenter;

    @BindView(R.id.fragment_parameter_multi_listen_switch)
    Switch multiListenSwitch;

    @BindView(R.id.fragment_parameter_most_listened_reply_name)
    TextView mostListenedReplyName;

    @BindView(R.id.fragment_parameter_total_reply_time)
    TextView totalReplyTime;

    public static ParameterFragment newInstance() {

        Bundle args = new Bundle();

        ParameterFragment fragment = new ParameterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_parameter, container, false);
        ButterKnife.bind(this, view);
        getFragmentComponent().inject(this);

        parameterPresenter.setParameterView(this);
        initializeSwitch();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        parameterPresenter.checkMultiListenEnabled();
        parameterPresenter.getMostListenedReply();
        parameterPresenter.getTotalReplyTime();
    }


    @Override
    public void updateSwitch(boolean checked) {
        multiListenSwitch.setChecked(checked);
    }

    @Override
    public void updateMostListenedReply(ReplyViewModel replyViewModel) {
        if (replyViewModel != null) {
            mostListenedReplyName.setText(replyViewModel.getMostListenedText());
        } else {
            mostListenedReplyName.setText(getString(R.string.no_listened_reply));
        }
    }

    @Override
    public void updateTotalReplyTime(long hours, long minutes, long seconds) {
        totalReplyTime.setText(getString(R.string.total_reply_time_text, hours, minutes, seconds));
    }

    @Override
    public void onReplyClicked() {
        parameterPresenter.getMostListenedReply();
    }

    @Override
    public void onReplyListened() {
        parameterPresenter.getTotalReplyTime();
    }

    @OnClick(R.id.fragment_parameter_random_reply)
    public void randomReplyClicked(){
        parameterPresenter.listenToRandomReply();
    }

    public void initializeSwitch() {
        multiListenSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            parameterPresenter.updateMultiListenParameter(isChecked);
        });
    }


}

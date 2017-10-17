package fr.legrand.oss117soundboard.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.presenter.ParameterPresenter;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.ParameterView;

/**
 * Created by Benjamin on 17/10/2017.
 */

public class ParameterFragment extends BaseFragment implements ParameterView {

    @Inject
    ParameterPresenter parameterPresenter;

    @BindView(R.id.fragment_parameter_multi_listen_switch)
    Switch multiListenSwitch;

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
    }

    public void initializeSwitch() {
        multiListenSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            parameterPresenter.updateMultiListenParameter(isChecked);
        });
    }


    @Override
    public void updateSwitch(boolean checked) {
        multiListenSwitch.setChecked(checked);
    }
}

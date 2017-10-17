package fr.legrand.oss117soundboard.presentation.component;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;

/**
 * Created by Benjamin on 17/10/2017.
 */

public class ErrorComponentImpl implements ErrorComponent {
    private Context context;
    private BaseActivity baseActivity;

    @Inject
    public ErrorComponentImpl(Context context, BaseActivity baseActivity) {
        this.context = context;
        this.baseActivity = baseActivity;
    }

    @Override
    public void displayListenErrorToast() {
        Toast.makeText(context, R.string.listen_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayListenErrorSnackbar() {
        Snackbar.make(baseActivity.getRootView(), R.string.listen_error, Snackbar.LENGTH_LONG)
                .show();
    }

}

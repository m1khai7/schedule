package myapp.schedule.misha.myapplication.common.core.snack;

import android.view.View;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

import myapp.schedule.misha.myapplication.ScheduleApp;

public class SnackBehavior extends AbsSnackBehavior {

    public SnackBehavior(View root) {
        super(root);
    }

    @Override
    public void showSnack(String message) {
        Snackbar snack = Snackbar.make(getRoot(), message, Snackbar.LENGTH_LONG);
        snack.show();
    }

    @Override
    public void showSnack(String message, String button, View.OnClickListener callback) {
        Snackbar snack = Snackbar.make(getRoot(), message, Snackbar.LENGTH_LONG);
        snack.setAction(button, callback);
        snack.show();
    }

    @Override
    public void showSnack(@StringRes int stringRes) {
        Snackbar snack = Snackbar.make(getRoot(), ScheduleApp.getStr(stringRes), Snackbar.LENGTH_LONG);
        snack.show();
    }

}

package myapp.schedule.misha.myapplication.module.settings.menu;

import android.app.Dialog;

import myapp.schedule.misha.myapplication.common.core.BaseView;

public interface MenuFragmentView extends BaseView {

    void openFragmentTransferData();

    Dialog showDialogDevInfo();

    void showDialogSelectTheme();

    void showFragmentCreateLesson();
}

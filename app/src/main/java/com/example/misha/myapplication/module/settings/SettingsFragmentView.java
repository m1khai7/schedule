package com.example.misha.myapplication.module.settings;

import android.app.Dialog;

import com.example.misha.myapplication.common.core.BaseView;

public interface SettingsFragmentView extends BaseView {

    void openFragmentTransferData();

    Dialog onCreateDialogAbout();

    void showDialogSelectTheme();

}

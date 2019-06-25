package com.example.misha.myapplication.module.settings;

import android.app.Dialog;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.Licenses;

import java.util.ArrayList;

public interface SettingsFragmentView extends BaseView {

    void openFragmentTransferData();

    Dialog onCreateDialogAbout();

    void showDialogSelectTheme();

    void showLicensesDialog(ArrayList<Licenses> licensesList);
}

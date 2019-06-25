package myapp.schedule.misha.myapplication.module.settings;

import android.app.Dialog;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Licenses;

public interface SettingsFragmentView extends BaseView {

    void openFragmentTransferData();

    Dialog onCreateDialogAbout();

    void showDialogSelectTheme();

    void showLicensesDialog(ArrayList<Licenses> licensesList);
}

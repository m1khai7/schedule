package com.example.misha.myapplication.module.settings;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.misha.myapplication.common.core.BaseActivity;
import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.preferences.Preferences;

import java.util.Calendar;

public class SettingsPresenter extends BaseMainPresenter<SettingsFragmentView> implements SettingsPresenterInterface {

    private Context context;

    public SettingsPresenter(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void init() {
    }

    @Override
    public void onClickDate() {
        getCurrentDate();
    }

    @Override
    public void onCreateDialogSelectTheme() {
        getView().showDialogSelectTheme();
    }

    @Override
    public void onCreateDialogAbout() {
        getView().onCreateDialogAbout().show();
    }

    @Override
    public void onOpenFragmentTransferData() {
        getView().openFragmentTransferData();
    }

    public void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        final Calendar selectedDate = Calendar.getInstance();
        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Preferences.getInstance().setSemesterStart(String.valueOf(selectedDate.getTimeInMillis()));

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(Long.parseLong(Preferences.getInstance().getSemestrStart()));
            mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
            mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Preferences.getInstance().setSemesterStart(String.valueOf(mCalendar.getTimeInMillis()));

        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
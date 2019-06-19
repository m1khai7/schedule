package com.example.misha.myapplication.module.schedule.exploreDays;

import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.util.DataUtil;

import java.util.Calendar;

public class SchedulePresenter extends BaseMainPresenter<ScheduleFragmentView> implements SchedulePresenterInterface {


    @Override
    public void init() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int currentDay = day <= 2 ? 0 : day - 2;
        getView().selectCurrentDay(currentDay);
        selectDefaultWeek();

    }

    @Override
    public void onWeekSelected(int position) {
        getView().selectWeek(position);
        Preferences.getInstance().setSelectedWeekSchedule(position);
    }

    @Override
    public void onButtonClicked() {
        getView().openEditor();
    }

    @Override
    public void onPageSwiped(int position) {
        getView().swipePage(position);
        Preferences.getInstance().setSelectedPositionTabDays(position);
    }

    @Override
    public void onPageSelected(int position) {
        getView().selectPage(position);
        Preferences.getInstance().setSelectedPositionTabDays(position);
    }

    @Override
    public void selectDefaultWeek() {
        int currentWeek = (int) DataUtil.getCurrWeek();
        getView().selectCurrentWeek(currentWeek);
        Preferences.getInstance().setSelectedWeekSchedule((int) DataUtil.getCurrWeek() - 1);
    }
}

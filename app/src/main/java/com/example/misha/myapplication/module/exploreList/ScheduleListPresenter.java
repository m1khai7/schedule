package com.example.misha.myapplication.module.exploreList;

import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.util.DataUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleListPresenter extends BaseMainPresenter<ScheduleListFragmentView> implements ScheduleListPresenterInterface {

    private ArrayList<Lesson> lessons;

    public ScheduleListPresenter() {

    }

    @Override
    public void init() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int currentDay = day <= 2 ? 0 : day - 2;
        // getView().selectCurrentDay(currentDay);
        //  selectDefaultWeek();

        if (lessons == null) {
            load();
        } else {
            getView().updateList(lessons);
        }
    }


    public void load() {
        this.lessons = LessonDao.getInstance().getAllData();
        getView().updateList(this.lessons);
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
    public void selectDefaultWeek() {
        int currentWeek = (int) DataUtil.getCurrWeek();
        getView().selectCurrentWeek(currentWeek);
        Preferences.getInstance().setSelectedWeekSchedule((int) DataUtil.getCurrWeek() - 1);
    }
}

package com.example.misha.myapplication.module.schedule.edit;

import com.example.misha.myapplication.common.core.BaseView;

public interface EditScheduleFragmentView extends BaseView {

    void selectWeek(int position);

    void selectCurrentDay(int currentDay);

    void selectCurrentWeek(int currentWeek);

    void swipePage(int position);

    void selectPage(int position);

    void animateFAB();

}

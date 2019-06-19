package com.example.misha.myapplication.module.schedule.exploreDays.page;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.Lesson;

import java.util.ArrayList;

public interface SchedulePageFragmentView extends BaseView {

    void showProgressBar();

    void hideProgressBar();

    void showErrorView();

    void hideErrorView();

    void updateList(ArrayList<Lesson> lessonList);

    void setWeek(int position);

}

package com.example.misha.myapplication.module.exploreList;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.Lesson;

import java.util.ArrayList;

public interface ScheduleListFragmentView extends BaseView {

    void selectWeek(int position);

    void openEditor();

    void selectCurrentDay(int currentDay);

    void selectCurrentWeek(int currentWeek);

    void hideProgressBar();

    void updateList(ArrayList<Lesson> lessonList);


}

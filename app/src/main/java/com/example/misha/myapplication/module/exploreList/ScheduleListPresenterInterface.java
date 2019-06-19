package com.example.misha.myapplication.module.exploreList;

public interface ScheduleListPresenterInterface {

    void onWeekSelected(int position);

    void onButtonClicked();

    void selectDefaultWeek();

    public void load();

}

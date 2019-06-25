package com.example.misha.myapplication.data;

import com.example.misha.myapplication.data.database.DatabaseInterface;
import com.example.misha.myapplication.data.database.DatabaseManager;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.data.preferences.PreferencesInterface;
import com.example.misha.myapplication.entity.Lesson;

import java.util.List;

import io.reactivex.Single;

public class Repository implements RepositoryManager {

    private static volatile Repository instance;

    private DatabaseInterface databaseInterface;


    private PreferencesInterface preferencesInterface;

    private Repository() {
        databaseInterface = DatabaseManager.getInstance();
        preferencesInterface = Preferences.getInstance();
    }

    public static Repository getInstance() {
        if (instance != null) return instance;
        instance = new Repository();
        return instance;
    }

    @Override
    public Single<List<Lesson>> getLessonsByDayWeek(int week, int day) {
        return databaseInterface.getLessonsByDayWeek(week, day);
    }


    @Override
    public boolean isHintsOpened() {
        return preferencesInterface.isHintsOpened();
    }

    @Override
    public void setHintsOpened() {
        preferencesInterface.setHintsOpened();
    }

    @Override
    public void setSemesterStart(String date) {
        preferencesInterface.setSemesterStart(date);
    }

    @Override
    public String getSemestrStart() {
        return preferencesInterface.getSemestrStart();
    }

    @Override
    public boolean isCallsOpened() {
        return preferencesInterface.isCallsOpened();
    }

    @Override
    public void setCallsOpened(boolean state) {
        preferencesInterface.setCallsOpened(state);
    }

    @Override
    public int getSelectedWeekSchedule() {
        return preferencesInterface.getSelectedWeekSchedule();
    }

    @Override
    public void setSelectedWeekSchedule(int position) {
        preferencesInterface.setSelectedWeekSchedule(position);
    }

    @Override
    public int getSelectedPositionTabDays() {
        return preferencesInterface.getSelectedPositionTabDays();
    }

    @Override
    public void setSelectedPositionTabDays(int position) {
        preferencesInterface.setSelectedPositionTabDays(position);
    }

    @Override
    public boolean getFabOpen() {
        return preferencesInterface.getFabOpen();
    }

    @Override
    public void setFabOpen(boolean state) {
        preferencesInterface.setFabOpen(state);
    }

    @Override
    public int getSelectedPositionLesson() {
        return preferencesInterface.getSelectedPositionLesson();
    }

    @Override
    public void setSelectedPositionLesson(int position) {
        preferencesInterface.setSelectedPositionLesson(position);
    }

    @Override
    public String getSelectDate() {
        return preferencesInterface.getSelectDate();
    }

    @Override
    public void setSelectDate(String selectDate) {
        preferencesInterface.setSelectDate(selectDate);
    }

    @Override
    public String getSelectedTheme() {
        return preferencesInterface.getSelectedTheme();
    }

    @Override
    public void setSelectedTheme(String stringTheme) {
        preferencesInterface.setSelectedTheme(stringTheme);
    }

    @Override
    public String getSelectedLesson() {
        return preferencesInterface.getSelectedLesson();
    }

    @Override
    public String getSelectedWeek() {
        return preferencesInterface.getSelectedWeek();
    }

    @Override
    public String getSelectedDay() {
        return preferencesInterface.getSelectedDay();
    }

    @Override
    public String getSelectedDate() {
        return preferencesInterface.getSelectedDate();
    }

    @Override
    public String getSelectedCurrentLesson() {
        return preferencesInterface.getSelectedCurrentLesson();
    }

    @Override
    public void setSelectedCurrentLesson(String currentLesson) {
        preferencesInterface.setSelectedCurrentLesson(currentLesson);
    }

    @Override
    public Boolean getWeek() {
        return preferencesInterface.getWeek();
    }

    @Override
    public void selectWeek(boolean select) {
        preferencesInterface.selectWeek(select);
    }
}

package myapp.schedule.misha.myapplication.data.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

import myapp.schedule.misha.myapplication.ScheduleApp;

public final class Preferences implements PreferencesInterface {

    public static final String SELECT_DATE = "SELECT_DATE";
    public static final String DARK_THEME = "DARK_THEME";
    public static final String LIGHT_THEME = "LIGHT_THEME";
    private static final String PREF_KEY_FIRST_OPEN_HINT = "PREF_KEY_FIRST_OPEN_HINT";
    private static final String PREF_KEY_CALLS_OPEN_HINT = "PREF_KEY_CALLS_OPEN_HINT";
    private static final String PREF_KEY_SEMESTER_START = "PREF_KEY_SEMESTER_START";
    private static final String PREF_KEY_SELECT_WEEK = "PREF_KEY_SELECT_WEEK";
    private static final String PREF_KEY_SELECT_TAB_DAYS = "PREF_KEY_SELECT_TAB_DAYS";
    private static final String PREF_KEY_SELECT_LESSON = "PREF_KEY_SELECT_LESSON";
    private static final String PREF_KEY_FAB_OPEN = "PREF_KEY_FAB_OPEN";
    private static final String SELECTED_WEEK = "SELECTED_WEEK";
    private static final String SELECTED_DAY = "SELECTED_DAY";
    private static final String SELECTED_LESSON = "SELECTED_LESSON";
    private static final String CURRENT_LESSON = "CURRENT_LESSON";
    private static final String WEEK = "WEEK";
    private static volatile Preferences instance;
    private final SharedPreferences mPrefs;
    public String SELECT_THEME = "SELECT_THEME";

    private Preferences() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ScheduleApp.getAppContext());
    }

    public static Preferences getInstance() {
        if (instance != null) return instance;
        instance = new Preferences();
        return instance;
    }

    @Override
    public boolean isHintsOpened() {
        return mPrefs.getBoolean(PREF_KEY_FIRST_OPEN_HINT, false);
    }

    @Override
    public void setHintsOpened() {
        mPrefs.edit().putBoolean(PREF_KEY_FIRST_OPEN_HINT, true).apply();
    }


    @Override
    public void setSemesterStart(String date) {
        mPrefs.edit().putString(PREF_KEY_SEMESTER_START, date).apply();
    }

    @Override
    public String getSemestrStart() {
        return mPrefs.getString(PREF_KEY_SEMESTER_START, "1549227600000");
    }


    @Override
    public boolean isCallsOpened() {
        return mPrefs.getBoolean(PREF_KEY_CALLS_OPEN_HINT, true);
    }

    @Override
    public void setCallsOpened(boolean state) {
        mPrefs.edit().putBoolean(PREF_KEY_CALLS_OPEN_HINT, state).apply();
    }

    @Override
    public int getSelectedWeekSchedule() {
        return mPrefs.getInt(PREF_KEY_SELECT_WEEK, 0);
    }

    @Override
    public void setSelectedWeekSchedule(int position) {
        mPrefs.edit().putInt(PREF_KEY_SELECT_WEEK, position).apply();
    }

    public int getSelectedPositionTabDays() {
        return mPrefs.getInt(PREF_KEY_SELECT_TAB_DAYS, 0);
    }

    public void setSelectedPositionTabDays(int position) {
        mPrefs.edit().putInt(PREF_KEY_SELECT_TAB_DAYS, position).apply();

    }

    public boolean getFabOpen() {
        return mPrefs.getBoolean(PREF_KEY_FAB_OPEN, true);
    }

    public void setFabOpen(boolean state) {
        mPrefs.edit().putBoolean(PREF_KEY_FAB_OPEN, state).apply();
    }

    public int getSelectedPositionLesson() {
        return mPrefs.getInt(PREF_KEY_SELECT_LESSON, 0);
    }

    public void setSelectedPositionLesson(int position) {
        mPrefs.edit().putInt(PREF_KEY_SELECT_LESSON, position).apply();
    }

    public String getSelectDate() {
        return mPrefs.getString(SELECT_DATE, String.valueOf(Calendar.getInstance().getTimeInMillis()));
    }

    public void setSelectDate(String selectDate) {
        mPrefs.edit().putString(SELECT_DATE, selectDate).apply();
    }

    @Override
    public String getSelectedTheme() {
        return mPrefs.getString(SELECT_THEME, DARK_THEME);
    }

    @Override
    public void setSelectedTheme(String stringTheme) {
        mPrefs.edit().putString(SELECT_THEME, stringTheme).apply();
    }

    @Override
    public String getSelectedWeek() {
        return mPrefs.getString(SELECTED_WEEK, "");
    }

    @Override
    public String getSelectedDay() {
        return mPrefs.getString(SELECTED_DAY, "");
    }

    @Override
    public String getSelectedLesson() {
        return mPrefs.getString(SELECTED_LESSON, "");
    }

    @Override
    public String getSelectedDate() {
        return mPrefs.getString(SELECTED_LESSON, "");
    }

    @Override
    public String getSelectedCurrentLesson() {
        return mPrefs.getString(CURRENT_LESSON, "0");
    }

    @Override
    public void setSelectedCurrentLesson(String currentLesson) {
        mPrefs.edit().putString(CURRENT_LESSON, currentLesson).apply();
    }

    @Override
    public Boolean getWeek() {
        return mPrefs.getBoolean(WEEK, false);
    }

    @Override
    public void selectWeek(boolean select) {
        mPrefs.edit().putBoolean(WEEK, select).apply();
    }


}

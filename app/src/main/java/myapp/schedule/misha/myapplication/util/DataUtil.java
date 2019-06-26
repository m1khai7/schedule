package myapp.schedule.misha.myapplication.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Lesson;

public final class DataUtil {

    public static long getCurrWeek() {
        long selectDate = Long.valueOf(Preferences.getInstance().getSemestrStart());
        long differentBetweenDate = Calendar.getInstance().getTimeInMillis() - selectDate;
        int calculateWeek = (int) (differentBetweenDate / (7 * 24 * 60 * 60 * 1000));
        return calculateWeek >= 16 ? 16 : calculateWeek;
    }

    public static void hintKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public static void showKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

        public static String dateDay(ArrayList<Lesson> lessonArrayList, int position) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(Long.valueOf(Preferences.getInstance().getSemestrStart()));
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String pattern = "EEEE, d MMMM";
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            mCalendar.add(Calendar.WEEK_OF_YEAR, Integer.parseInt(lessonArrayList.get(position).getNumber_week()) - 1);
            mCalendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(lessonArrayList.get(position).getNumber_day()) - 1);
        } catch (NumberFormatException ignored) {
        }
        String date = df.format(mCalendar.getTime());
        return date.substring(0, 1).toUpperCase() + date.substring(1);
    }

}

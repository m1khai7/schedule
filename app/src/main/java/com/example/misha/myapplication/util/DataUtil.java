package com.example.misha.myapplication.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Lesson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public final class DataUtil {

    public static long getCurrWeek() {
        long selectDate = Long.valueOf(Preferences.getInstance().getSemestrStart());
        long differentBetweenDate = Calendar.getInstance().getTimeInMillis() - selectDate;
        int calculateWeek = (int) (differentBetweenDate / (7 * 24 * 60 * 60 * 1000));
        return calculateWeek >= 16 ? 16 : calculateWeek;
    }

    public static void hintKeyboard(Activity contex) {
        InputMethodManager imm = (InputMethodManager) contex.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (contex.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(contex.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}

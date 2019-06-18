package com.example.misha.myapplication.data.database;

import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.entity.Lesson;

import java.util.List;

import io.reactivex.Single;

public class DatabaseManager implements DatabaseInterface {

    private static volatile DatabaseManager instance;

    private DatabaseManager() {

    }

    public static DatabaseManager getInstance() {
        if (instance != null) return instance;
        instance = new DatabaseManager();
        return instance;
    }

    @Override
    public Single<List<Lesson>> getLessonsByDayWeek(int week, int day) {
        return Single.fromCallable(() -> LessonDao.getInstance().getLessonByWeekAndDay(week, day));
    }
}

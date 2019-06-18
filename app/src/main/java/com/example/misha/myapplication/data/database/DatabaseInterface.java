package com.example.misha.myapplication.data.database;

import com.example.misha.myapplication.entity.Lesson;

import java.util.List;

import io.reactivex.Single;

public interface DatabaseInterface {

    Single<List<Lesson>> getLessonsByDayWeek(int week, int day);
}

package com.example.misha.myapplication.data;

import com.example.misha.myapplication.data.database.DatabaseInterface;
import com.example.misha.myapplication.data.preferences.PreferencesInterface;
import com.example.misha.myapplication.entity.Lesson;

import java.util.List;

import io.reactivex.Single;

public interface RepositoryManager extends DatabaseInterface, PreferencesInterface {

    @Override
    default Single<List<Lesson>> getLessonsByDayWeek(int week, int day) {
        return null;
    }
}

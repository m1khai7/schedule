package myapp.schedule.misha.myapplication.data.database;

import myapp.schedule.misha.myapplication.entity.Lesson;

import java.util.List;

import io.reactivex.Single;

public interface DatabaseInterface {

    Single<List<Lesson>> getLessonsByDayWeek(int week, int day);
}

package myapp.schedule.misha.myapplication.data.database;

import java.util.List;

import io.reactivex.Single;
import myapp.schedule.misha.myapplication.entity.Lesson;

public interface DatabaseInterface {

    Single<List<Lesson>> getLessonsByDayWeek(int week, int day);
}

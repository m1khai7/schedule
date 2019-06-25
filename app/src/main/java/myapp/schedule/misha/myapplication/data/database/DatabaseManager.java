package myapp.schedule.misha.myapplication.data.database;

import java.util.List;

import io.reactivex.Single;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.entity.Lesson;

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

package myapp.schedule.misha.myapplication.data;

import myapp.schedule.misha.myapplication.data.database.DatabaseInterface;
import myapp.schedule.misha.myapplication.data.preferences.PreferencesInterface;
import myapp.schedule.misha.myapplication.entity.Lesson;

import java.util.List;

import io.reactivex.Single;

public interface RepositoryManager extends DatabaseInterface, PreferencesInterface {

    @Override
    default Single<List<Lesson>> getLessonsByDayWeek(int week, int day) {
        return null;
    }
}

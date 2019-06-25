package myapp.schedule.misha.myapplication.data;

import java.util.List;

import io.reactivex.Single;
import myapp.schedule.misha.myapplication.data.database.DatabaseInterface;
import myapp.schedule.misha.myapplication.data.preferences.PreferencesInterface;
import myapp.schedule.misha.myapplication.entity.Lesson;

public interface RepositoryManager extends DatabaseInterface, PreferencesInterface {

    @Override
    default Single<List<Lesson>> getLessonsByDayWeek(int week, int day) {
        return null;
    }
}

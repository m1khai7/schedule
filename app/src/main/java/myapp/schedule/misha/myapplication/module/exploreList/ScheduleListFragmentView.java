package myapp.schedule.misha.myapplication.module.exploreList;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Lesson;

public interface ScheduleListFragmentView extends BaseView {

    void selectWeek(int position);

    void openEditor();

    void selectCurrentWeek(int currentWeek);

    void hideProgressBar();

    void updateList(ArrayList<Lesson> lessonList);
}

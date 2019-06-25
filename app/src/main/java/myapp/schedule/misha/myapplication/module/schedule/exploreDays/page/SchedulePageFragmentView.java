package myapp.schedule.misha.myapplication.module.schedule.exploreDays.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Lesson;

public interface SchedulePageFragmentView extends BaseView {

    void showProgressBar();

    void hideProgressBar();

    void showErrorView();

    void hideErrorView();

    void updateList(ArrayList<Lesson> lessonList);

    void setWeek(int position);

}

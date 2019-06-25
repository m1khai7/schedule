package myapp.schedule.misha.myapplication.module.schedule.exploreDays;

import myapp.schedule.misha.myapplication.common.core.BaseView;

public interface ScheduleFragmentView extends BaseView {

    void selectWeek(int position);

    void openEditor();

    void selectCurrentDay(int currentDay);

    void selectCurrentWeek(int currentWeek);

    void swipePage(int position);

    void selectPage(int position);
}

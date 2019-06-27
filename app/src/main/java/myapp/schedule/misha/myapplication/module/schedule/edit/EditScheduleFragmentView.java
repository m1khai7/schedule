package myapp.schedule.misha.myapplication.module.schedule.edit;

import myapp.schedule.misha.myapplication.common.core.BaseView;

public interface EditScheduleFragmentView extends BaseView {

    void selectWeek(int position);

    void selectCurrentDay(int currentDay);

    void selectCurrentWeek(int currentWeek);

    void swipePage(int position);

    void selectPage(int position);


}

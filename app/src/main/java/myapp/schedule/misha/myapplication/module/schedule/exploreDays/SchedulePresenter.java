package myapp.schedule.misha.myapplication.module.schedule.exploreDays;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.util.DataUtil;

public class SchedulePresenter extends BaseMainPresenter<ScheduleFragmentView> implements SchedulePresenterInterface {


    @Override
    public void init() {
        getView().selectCurrentDay(DataUtil.currentDay());
        selectDefaultWeek();
    }

    @Override
    public void onWeekSelected(int position) {
        getView().selectWeek(position);
        Preferences.getInstance().setSelectedWeekSchedule(position);
    }

    @Override
    public void onButtonClicked() {
        getView().openEditor();
    }

    @Override
    public void onPageSwiped(int position) {
        getView().swipePage(position);
        Preferences.getInstance().setSelectedPositionTabDays(position);
    }

    @Override
    public void onPageSelected(int position) {
        getView().selectPage(position);
        Preferences.getInstance().setSelectedPositionTabDays(position);
    }

    @Override
    public void selectDefaultWeek() {
        int currentWeek = (int) DataUtil.getCurrWeek();
        getView().selectCurrentWeek(currentWeek);
        Preferences.getInstance().setSelectedWeekSchedule((int) DataUtil.getCurrWeek() - 1);
    }
}

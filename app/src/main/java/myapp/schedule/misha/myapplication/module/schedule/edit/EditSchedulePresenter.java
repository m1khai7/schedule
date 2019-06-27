package myapp.schedule.misha.myapplication.module.schedule.edit;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;

public class EditSchedulePresenter extends BaseMainPresenter<EditScheduleFragmentView> implements EditSchedulePresenterInterface {


    public EditSchedulePresenter() {
    }

    @Override
    public void init() {
        int currentDay = Preferences.getInstance().getSelectedPositionTabDays();
        int currentWeek = Preferences.getInstance().getSelectedWeekSchedule();
        getView().selectCurrentDay(currentDay);
        getView().selectCurrentWeek(currentWeek);
    }

    @Override
    public void onWeekSelected(int position) {
        getView().selectWeek(position);
    }

    @Override
    public void onPageSwiped(int position) {
        Preferences.getInstance().setSelectedPositionTabDays(position);
        getView().swipePage(position);
    }

    @Override
    public void onPageSelected(int position) {
        Preferences.getInstance().setSelectedPositionTabDays(position);
        getView().selectPage(position);
    }


}

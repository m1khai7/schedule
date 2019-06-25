package myapp.schedule.misha.myapplication.module.exploreList;

public interface ScheduleListPresenterInterface {

    void onWeekSelected(int position);

    void onButtonClicked();

    void selectDefaultWeek();

    void load();
}

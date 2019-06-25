package myapp.schedule.misha.myapplication.module.schedule.exploreDays.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.entity.Lesson;

public class SchedulePagePresenter extends BaseMainPresenter<SchedulePageFragmentView> implements SchedulePagePresenterInterface {

    private int day;

    private int positionWeek;

    private ArrayList<Lesson> lessons;

    public SchedulePagePresenter(int day, int positionWeek) {
        this.day = day;
        this.positionWeek = positionWeek;
    }

    @Override
    public void init() {
        if (lessons == null) {
            getView().showProgressBar();
            loadWeek();
        } else {
            getView().updateList(lessons);
        }
    }

    private void loadWeek() {
        getCompositeDisposable().add(getRepositoryManager()
                .getLessonsByDayWeek(positionWeek, day)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(lessons -> {
                    getView().hideProgressBar();
                    getView().hideErrorView();
                    this.lessons = (ArrayList<Lesson>) lessons;
                    getView().updateList((ArrayList<Lesson>) lessons);
                }, throwable -> {
                    getView().hideProgressBar();
                    getView().showErrorView();
                    processGlobalError(throwable);
                })
        );
    }


    @Override
    public void setWeek(int positionWeek) {
        this.positionWeek = positionWeek;
        loadWeek();
    }
}

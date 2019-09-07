package myapp.schedule.misha.myapplication.module.schedule.exploreDays.page;

import java.util.List;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

public class SchedulePagePresenter extends BaseMainPresenter<SchedulePageFragmentView> implements SchedulePagePresenterInterface {

    private int day;

    private int positionWeek;

    private List<Lesson> listLessons;

    private Subject subject;
    private Audience audience;
    private Educator educator;
    private Typelesson typelesson;

    SchedulePagePresenter(int day, int positionWeek) {
        this.day = day;
        this.positionWeek = positionWeek;
    }

    @Override
    public void init() {
        if (listLessons == null) {
            getView().showProgressBar();
            loadWeek();
        } else {
            getView().updateView(listLessons);
        }
    }

    private void loadWeek() {
        getCompositeDisposable().add(getRepositoryManager()
                .getLessonsByDayWeek(positionWeek, day)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(loadListLesson -> {
                    getView().hideProgressBar();
                    getView().hideErrorView();
                    listLessons = loadListLesson;
                    if (checkEmptyDay(listLessons)) {
                        getView().setEmptyDay();
                    } else {
                        getView().updateView(listLessons);
                    }
                }, throwable -> {
                    getView().hideProgressBar();
                    getView().showErrorView();
                    processGlobalError(throwable);
                })
        );
    }

    private boolean checkEmptyDay(List<Lesson> listLesson) {
        int countPositiveItems = 0;
        for (Lesson lesson : listLesson) {
            try {
                subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
                audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
                educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
                typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
            } catch (NumberFormatException ignored) {
            }

            if (subject == null || audience == null || educator == null || typelesson == null) {
                countPositiveItems += 1;
            }
        }
        return countPositiveItems == 6;
    }

    @Override
    public void setWeek(int positionWeek) {
        this.positionWeek = positionWeek;
        loadWeek();
    }
}

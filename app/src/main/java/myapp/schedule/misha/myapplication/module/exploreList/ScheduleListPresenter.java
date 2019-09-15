package myapp.schedule.misha.myapplication.module.exploreList;

import java.util.ArrayList;
import java.util.Calendar;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;
import myapp.schedule.misha.myapplication.util.DataUtil;

public class ScheduleListPresenter extends BaseMainPresenter<ScheduleListFragmentView> implements ScheduleListPresenterInterface {

    @Override
    public void init() {
        selectDefaultWeek();
        load();
    }

    public void load() {
        ArrayList<Lesson> lessonsNew = new ArrayList<>();
        String currentDay = "0";
        String currentWeek = "0";
        ArrayList<Lesson> lessons = LessonDao.getInstance().getAllData();
        Lesson lesson;
        Subject subject;
        Audience audience;
        Educator educator;
        Typelesson typelesson;
        for (int i = 0; i < lessons.size(); i++) {
            lesson = lessons.get(i);
            subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
            audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
            educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
            typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));

            if (subject != null && audience != null && educator != null && typelesson != null) {
                if (!currentDay.equals(lesson.getNumber_day()) || !currentWeek.equals(lesson.getNumber_week())) {
                    Lesson les = new Lesson();
                    les.setId_subject("0");
                    les.setId_audience("0");
                    les.setId_educator("0");
                    les.setId_typelesson("0");
                    les.setNumber_day(lessons.get(i).getNumber_day());
                    les.setNumber_week(lessons.get(i).getNumber_week());
                    lessonsNew.add(les);
                    currentDay = lessons.get(i).getNumber_day();
                    currentWeek = lessons.get(i).getNumber_week();
                }
                lessonsNew.add(lesson);
            }
        }
        try {
            lessonsNew.remove(0);
        } catch (IndexOutOfBoundsException error) {
        }
        getView().hideProgressBar();
        getView().updateList(lessonsNew);
    }

    @Override
    public void onWeekSelected(int position) {
        getView().selectWeek(position);
    }

    @Override
    public void onButtonClicked() {
        getView().openEditor();
    }


    @Override
    public void selectDefaultWeek() {
        int currentWeek = (int) DataUtil.getCurrWeek();
        getView().selectCurrentWeek(currentWeek);
        Preferences.getInstance().setSelectedWeekSchedule((int) DataUtil.getCurrWeek() - 1);
    }
}

package com.example.misha.myapplication.module.exploreList;

import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Audience;
import com.example.misha.myapplication.entity.Educator;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.entity.Subject;
import com.example.misha.myapplication.entity.Typelesson;
import com.example.misha.myapplication.util.DataUtil;

import java.util.ArrayList;

public class ScheduleListPresenter extends BaseMainPresenter<ScheduleListFragmentView> implements ScheduleListPresenterInterface {

    public ArrayList<Lesson> lessonsNew = new ArrayList<>();
    private String currentDay = "0";
    private String currentWeek = "0";

    public ScheduleListPresenter() {

    }

    @Override
    public void init() {
        //  Calendar calendar = Calendar.getInstance();
        // int day = calendar.get(Calendar.DAY_OF_WEEK);
        // int currentDay = day <= 2 ? 0 : day - 2;
        // getView().selectCurrentDay(currentDay);
        // selectDefaultWeek();

        load();
    }

    public void load() {
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

            if (subject != null || audience != null || educator != null || typelesson != null) {
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
        lessonsNew.remove(0);
        getView().updateList(lessonsNew);

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
    public void selectDefaultWeek() {
        int currentWeek = (int) DataUtil.getCurrWeek();
        getView().selectCurrentWeek(currentWeek);
        Preferences.getInstance().setSelectedWeekSchedule((int) DataUtil.getCurrWeek() - 1);
    }
}

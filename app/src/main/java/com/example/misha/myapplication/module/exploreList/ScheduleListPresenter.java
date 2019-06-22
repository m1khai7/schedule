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

    private ArrayList<Lesson> lessons;
    public ArrayList<Lesson> lessonsNew = new ArrayList<>();

    public ScheduleListPresenter() {

    }

    @Override
    public void init() {
        //  Calendar calendar = Calendar.getInstance();
        // int day = calendar.get(Calendar.DAY_OF_WEEK);
        // int currentDay = day <= 2 ? 0 : day - 2;
        // getView().selectCurrentDay(currentDay);
        // selectDefaultWeek();

        if (lessons == null) {
            load();
        } else {
            getView().updateList(lessons);
        }
    }

    public void load() {
        lessons = LessonDao.getInstance().getAllData();
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
                lessonsNew.add(lesson);
            }
        }
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

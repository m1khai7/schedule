package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;


public class EditSchedulePagePresenter extends BaseMainPresenter<EditSchedulePageFragmentView> implements EditSchedulePagePresenterInterface {


    private ArrayList<Lesson> lessonList = new ArrayList<>();

    private int positionWeek;
    private int day;

    public EditSchedulePagePresenter(int positionWeek, int day) {
        this.positionWeek = positionWeek;
        this.day = day;
    }

    @Override
    public void init() {
        updateList(day, positionWeek);
    }


    @Override
    public void onSubjectClick(int position) {
        ArrayList<Subject> subjectList = SubjectDao.getInstance().getAllData();
        getView().showEditDialog(subjectList, position, FRAGMENT_SUBJECTS);
        updateList(day, positionWeek);
    }

    @Override
    public void onAudienceClick(int position) {
        ArrayList<Audience> audienceList = AudienceDao.getInstance().getAllData();
        getView().showEditDialog(audienceList, position, FRAGMENT_AUDIENCES);
    }

    @Override
    public void onEducatorClick(int position) {
        ArrayList<Educator> educatorList = EducatorDao.getInstance().getAllData();
        getView().showEditDialog(educatorList, position, FRAGMENT_EDUCATORS);
    }

    @Override
    public void onTypelessonClick(int position) {
        ArrayList<Typelesson> typelessonList = TypelessonDao.getInstance().getAllData();
        getView().showEditDialog(typelessonList, position, FRAGMENT_TYPELESSONS);
    }

    @Override
    public void setWeek(int positionWeek) {
        this.positionWeek = positionWeek;
        updateList(day, positionWeek);
    }

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    @Override
    public void onCopyUpClick(int position) {
        lessonList.get(position - 1).setId_subject(lessonList.get(position).getId_subject());
        lessonList.get(position - 1).setId_audience(lessonList.get(position).getId_audience());
        lessonList.get(position - 1).setId_typelesson(lessonList.get(position).getId_typelesson());
        lessonList.get(position - 1).setId_educator(lessonList.get(position).getId_educator());
        getView().updateView(lessonList);
        LessonDao.getInstance().updateItemByID(lessonList.get(position - 1));
    }

    @Override
    public void onCopyDownClick(int position) {
        lessonList.get(position + 1).setId_subject(lessonList.get(position).getId_subject());
        lessonList.get(position + 1).setId_audience(lessonList.get(position).getId_audience());
        lessonList.get(position + 1).setId_typelesson(lessonList.get(position).getId_typelesson());
        lessonList.get(position + 1).setId_educator(lessonList.get(position).getId_educator());
        getView().updateView(lessonList);
        LessonDao.getInstance().updateItemByID(lessonList.get(position + 1));
    }

    @Override
    public void onClearLessonClick(int position) {
        lessonList.get(position).setId_subject("0");
        lessonList.get(position).setId_audience("0");
        lessonList.get(position).setId_typelesson("0");
        lessonList.get(position).setId_educator("0");
        getView().updateView(lessonList);
        LessonDao.getInstance().updateItemByID(lessonList.get(position));
    }

    public void updateList(int day, int positionWeek) {
        lessonList = LessonDao.getInstance().getLessonByWeekAndDay(positionWeek, day);
        getView().updateView(lessonList);
    }

}

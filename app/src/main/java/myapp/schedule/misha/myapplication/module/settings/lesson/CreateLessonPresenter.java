package myapp.schedule.misha.myapplication.module.settings.lesson;

import java.util.ArrayList;

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

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class CreateLessonPresenter extends BaseMainPresenter<CreateLessonView> implements CreateLessonPresenterInterface {


    @Override
    public void init() {
    }

    @Override
    public void onSubjectClick() {
        ArrayList<Subject> subjectList = SubjectDao.getInstance().getAllData();
        getView().showEditDialog(subjectList, FRAGMENT_SUBJECTS);
    }

    @Override
    public void onAudienceClick() {
        ArrayList<Audience> audienceList = AudienceDao.getInstance().getAllData();
        getView().showEditDialog(audienceList, FRAGMENT_AUDIENCES);
    }

    @Override
    public void onEducatorClick() {
        ArrayList<Educator> educatorList = EducatorDao.getInstance().getAllData();
        getView().showEditDialog(educatorList, FRAGMENT_EDUCATORS);
    }

    @Override
    public void onTypelessonClick() {
        ArrayList<Typelesson> typelessonList = TypelessonDao.getInstance().getAllData();
        getView().showEditDialog(typelessonList, FRAGMENT_TYPELESSONS);
    }

    @Override
    public void onNext(String nameSubject, String nameAudience, String nameEducator, String nameTypelesson) {
        Lesson lesson = new Lesson();
        Subject subject = SubjectDao.getInstance().getItemByName(nameSubject);
        Audience audience = AudienceDao.getInstance().getItemByName(nameAudience);
        Educator educator = EducatorDao.getInstance().getItemByName(nameEducator);
        Typelesson typelesson = TypelessonDao.getInstance().getItemByName(nameTypelesson);
        if (subject == null) {
            SubjectDao.getInstance().insertItem(new Subject(nameSubject));
        } else {
            lesson.setId_subject(subject.getId());
        }
        if (audience == null) {
            AudienceDao.getInstance().insertItem(new Audience(nameAudience));
        } else {
            lesson.setId_audience(audience.getId());
        }
        if (educator == null) {
            EducatorDao.getInstance().insertItem(new Educator(nameEducator));
        } else {
            lesson.setId_educator(educator.getId());
        }
        if (typelesson == null) {
            TypelessonDao.getInstance().insertItem(new Typelesson(nameTypelesson));
        } else {
            lesson.setId_typelesson(typelesson.getId());
        }
        getView().showCopyLesson(lesson);
    }
}



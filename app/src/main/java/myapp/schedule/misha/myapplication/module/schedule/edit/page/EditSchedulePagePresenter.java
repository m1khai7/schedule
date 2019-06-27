package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
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
    private ArrayList<CopyLesson> lessonList1= new ArrayList<>();
    private List<Lesson> lessonListWeek = new ArrayList<>();
    private List<Lesson> lessonListWeekCurrent = new ArrayList<>();

    private int positionWeek;
    private int day;
    private Context context;

    public EditSchedulePagePresenter(BaseActivity context, int positionWeek, int day) {
        this.context = context;
        this.positionWeek = positionWeek;
        this.day = day;
    }

    @Override
    public void init() {
       updateList(Preferences.getInstance().getSelectedPositionTabDays(), positionWeek + 1);
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
    public void onButtonClicked(int id) {
        if (id == R.id.main_fab) {
            getView().animateFAB();
        }
        if (id == R.id.copyWeek) {
            copyWeek();
        }
        if (id == R.id.clearDay) {
            Toast.makeText(context, "Пока здесь пусто :)", Toast.LENGTH_SHORT).show();

        }
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

    @Override
    public void onCopyLessonOtherDay(int position) {
       getView().showCopyDialog(lessonList1,position);
    }

    @Override
    public void onClearDayClick() {
        for (int i=0;i<6;i++){
        lessonList.get(i).setId_subject("0");
        lessonList.get(i).setId_audience("0");
        lessonList.get(i).setId_typelesson("0");
        lessonList.get(i).setId_educator("0");
        LessonDao.getInstance().updateItemByID(lessonList.get(i));}
        getView().updateView(lessonList);
    }

    public void copyWeek() {
        onCreateDialogCopyWeek().show();
    }


    private Dialog onCreateDialogCopyWeek() {
        int currentWeek = Preferences.getInstance().getSelectedWeekSchedule();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setPositiveButton(R.string.ack, (dialog, id) -> {
            for (int idWeek = 2; idWeek < 18; idWeek += 2) {
                lessonListWeekCurrent = LessonDao.getInstance().getLessonByWeek(currentWeek);

                lessonListWeek = LessonDao.getInstance().getLessonByWeek(idWeek);
                for (int idRowWeek = 0; idRowWeek < 36; idRowWeek++) {
                    lessonListWeek.get(idRowWeek).setData(lessonListWeekCurrent.get(idRowWeek).getId_subject(), lessonListWeekCurrent.get(idRowWeek).getId_audience(),
                            lessonListWeekCurrent.get(idRowWeek).getId_educator(), lessonListWeekCurrent.get(idRowWeek).getId_typelesson());
                    LessonDao.getInstance().updateItemByID(lessonListWeek.get(idRowWeek));
                }
            }
        }).setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel()).setTitle("");
        return builder.create();
        /*   {
            for (int idWeek = 1; idWeek < 18; idWeek += 2) {
                lessonListWeekCurrent = LessonDao.getInstance().getLessonByWeek(currentWeek);
                lessonListWeek = LessonDao.getInstance().getLessonByWeek(idWeek);
                for (int idRowWeek = 0; idRowWeek < 36; idRowWeek++) {
                    lessonListWeek.get(idRowWeek).setData(lessonListWeekCurrent.get(idRowWeek).getId_subject(), lessonListWeekCurrent.get(idRowWeek).getId_audience(),
                            lessonListWeekCurrent.get(idRowWeek).getId_educator(), lessonListWeekCurrent.get(idRowWeek).getId_typelesson());
                    LessonDao.getInstance().updateItemByID(lessonListWeek.get(idRowWeek));
                }
            }
        }*/
    }


    public void updateList(int day, int positionWeek) {
        lessonList = LessonDao.getInstance().getLessonByWeekAndDay(positionWeek, day);
        getView().updateView(lessonList);
    }

}

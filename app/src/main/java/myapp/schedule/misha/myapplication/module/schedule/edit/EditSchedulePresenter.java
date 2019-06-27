package myapp.schedule.misha.myapplication.module.schedule.edit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Lesson;

public class EditSchedulePresenter extends BaseMainPresenter<EditScheduleFragmentView> implements EditSchedulePresenterInterface {

    private List<Lesson> lessonListWeek = new ArrayList<>();
    private List<Lesson> lessonListWeekCurrent = new ArrayList<>();

    private Context context;

    public EditSchedulePresenter(BaseActivity context) {
        this.context = context;
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

    public void evenWeekFab() {
        onCreateDialogCopyEvenWeek().show();
    }

    public void unevenWeekFab() {
        onCreateDialogCopyUnevenWeek().show();
    }


    @Override
    public void onButtonClicked(int id) {
        if (id == R.id.main_fab) {
            getView().animateFAB();
        }
        if (id == R.id.even_weekFab) {
            evenWeekFab();
        }
        if (id == R.id.uneven_weekFab) {
            unevenWeekFab();
        }
    }

    @Override
    public void onPageSwiped(int position) {
        getView().swipePage(position);
        Preferences.getInstance().setSelectedPositionTabDays(position);
    }

    private Dialog onCreateDialogCopyEvenWeek() {
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
        }).setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel()).setTitle(R.string.copy_to_evenweek);
        return builder.create();
    }


    private Dialog onCreateDialogCopyUnevenWeek() {
        int currentWeek = Preferences.getInstance().getSelectedWeekSchedule();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setPositiveButton(R.string.ack, (dialog, id) -> {
            for (int idWeek = 1; idWeek < 18; idWeek += 2) {
                lessonListWeekCurrent = LessonDao.getInstance().getLessonByWeek(currentWeek);
                lessonListWeek = LessonDao.getInstance().getLessonByWeek(idWeek);
                for (int idRowWeek = 0; idRowWeek < 36; idRowWeek++) {
                    lessonListWeek.get(idRowWeek).setData(lessonListWeekCurrent.get(idRowWeek).getId_subject(), lessonListWeekCurrent.get(idRowWeek).getId_audience(),
                            lessonListWeekCurrent.get(idRowWeek).getId_educator(), lessonListWeekCurrent.get(idRowWeek).getId_typelesson());
                    LessonDao.getInstance().updateItemByID(lessonListWeek.get(idRowWeek));
                }
            }
        }).setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel()).setTitle(R.string.copy_to_unevenweek);
        return builder.create();
    }

    @Override
    public void onPageSelected(int position) {
        getView().selectPage(position);
    }


}

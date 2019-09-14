package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.CopyLesson;

public interface FragmentPresenterInterface {

    void onDialogLessonClick();

    void onImageAddClick(int id, int day, String timeLesson);

    void onImageDeleteClick(ArrayList<CopyLesson> listLessonsForCopy, int position);

    void showWeeks();
}

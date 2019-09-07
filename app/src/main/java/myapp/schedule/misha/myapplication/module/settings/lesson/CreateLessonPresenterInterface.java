package myapp.schedule.misha.myapplication.module.settings.lesson;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.CopyLesson;

public interface CreateLessonPresenterInterface {

    void onDialogLessonClick();

    void onImageAddClick(int id, int day, String timeLesson);

    void onImageDeleteClick(ArrayList<CopyLesson> itemList, int position);

    void showWeeks();
}

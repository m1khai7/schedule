package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.CopyLesson;

public interface DialogCopyFragmentPresenterInterface {

    void onDialogWeekClick();

    void onDialogLessonClick();

    void onImageAddClick(String day, String timeLesson);

    void onImageDeleteClick(ArrayList<CopyLesson> itemList, int position);
}

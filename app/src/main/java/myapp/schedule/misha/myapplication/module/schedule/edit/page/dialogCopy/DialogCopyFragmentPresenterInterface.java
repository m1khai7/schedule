package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.Weeks;

public interface DialogCopyFragmentPresenterInterface {

    void onDialogLessonClick();

    void onImageAddClick(int day, String timeLesson);

    void onImageDeleteClick(ArrayList<CopyLesson> itemList, int position);

    void showWeeks(ArrayList<Weeks> listWeeks);
}

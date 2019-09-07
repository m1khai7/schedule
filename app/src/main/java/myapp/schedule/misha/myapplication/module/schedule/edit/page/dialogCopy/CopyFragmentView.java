package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.Weeks;

public interface CopyFragmentView extends BaseView {

    String CURRENT_LESSON= "CURRENT_LESSON";

    int LESSON = 1233;

    void showLessonDialog();

    void openWeekDialog();

    void updateItemsAdapter(ArrayList<CopyLesson> itemList);

    void copyLesson();
}

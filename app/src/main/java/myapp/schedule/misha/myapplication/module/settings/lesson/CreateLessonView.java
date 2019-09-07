package myapp.schedule.misha.myapplication.module.settings.lesson;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.CopyLesson;

public interface CreateLessonView extends BaseView {

    String CURRENT_LESSON= "CURRENT_LESSON";

    int LESSON = 1233;

    void showLessonDialog();

    void openWeekDialog();

    void updateItemsAdapter(ArrayList<CopyLesson> itemList);

    void copyLesson();
}

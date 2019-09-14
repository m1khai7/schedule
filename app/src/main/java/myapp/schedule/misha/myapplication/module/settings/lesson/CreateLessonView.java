package myapp.schedule.misha.myapplication.module.settings.lesson;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface CreateLessonView extends BaseView {

    void showEditDialog(ArrayList<? extends SimpleItem> itemList, int fragmentCode);

    void showCopyLesson(Lesson lesson);
}

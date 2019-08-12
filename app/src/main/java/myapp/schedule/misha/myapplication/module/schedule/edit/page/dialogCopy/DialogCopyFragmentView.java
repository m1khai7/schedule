package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.Weeks;

public interface DialogCopyFragmentView extends BaseView {

    String ITEMS = "ITEMS";

    String POSITION = "POSITION";

    int SELECT_LESSON = 1233;

    void showLessonDialog();

    void openWeekDialog(ArrayList<Weeks> listWeeks);

    void updateItemsAdapter(ArrayList<CopyLesson> itemList);
}

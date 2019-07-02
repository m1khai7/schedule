package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.CopyLesson;

public interface DialogCopyFragmentView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";

    void showLessonDialog();

    void showWeekDialog();

    void updateItemsAdapter(ArrayList<CopyLesson> itemList);
}

package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

import myapp.schedule.misha.myapplication.common.core.BaseView;

public interface DialogSelectWeekFragmentView extends BaseView {

    int LIST_ITEMS = 1313;

    int CLEAR = 1234;

    int COPY = 4321;

    void selectAll();

    void updateAdapter();
}

package myapp.schedule.misha.myapplication.module.editData;

import myapp.schedule.misha.myapplication.data.database.AbsDao;

public interface EditDataPresenterInterface {

    void onClear(AbsDao absDao);

    void onCreateDialog(AbsDao absDao, int selectedTabPosition);

    void onClearClick(int id);
}

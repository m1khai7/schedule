package myapp.schedule.misha.myapplication.module.editData;

import android.app.Dialog;
import android.view.MenuItem;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.data.database.AbsDao;

public interface EditDataFragmentView extends BaseView {

    boolean onOptionsItemSelected(MenuItem item);

    Dialog onCreateDialogClear(AbsDao dao, int titleClear);

    void updateView();
}

package myapp.schedule.misha.myapplication.module.editData;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;

public class EditDataPresenter extends BaseMainPresenter<EditDataFragmentView> implements EditDataPresenterInterface {


    @Override
    public void init() {
    }

    @Override
    public void onClear(AbsDao absDao) {
        absDao.deleteAll();
        getView().updateView();
    }


    @Override
    public void onCreateDialog(AbsDao absDao, int id) {
        getView().onCreateDialogClear(absDao, id).show();
    }

    @Override
    public void onClearClick(int id) {
        if (id == 0) {
            onCreateDialog(SubjectDao.getInstance(), R.string.clear_subjects);
        }
        if (id == 1) {
            onCreateDialog(AudienceDao.getInstance(), R.string.clear_audience);
        }
        if (id == 2) {
            onCreateDialog(EducatorDao.getInstance(), R.string.clear_educators);
        }
        if (id == 3) {
            onCreateDialog(TypelessonDao.getInstance(), R.string.clear_typelessons);
        }
    }
}

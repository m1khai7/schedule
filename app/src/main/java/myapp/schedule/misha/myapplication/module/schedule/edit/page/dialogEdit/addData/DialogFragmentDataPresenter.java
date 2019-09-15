package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit.addData;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.SimpleItem;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class DialogFragmentDataPresenter extends BaseMainPresenter<DialogFragmentAddDataView> implements DialogFragmentDataPresenterInterface {

    private AbsDao absDao;

    public DialogFragmentDataPresenter(EditDataModel editDataModel) {
        absDao = editDataModel.getDao();
    }

    @Override
    public void insert(String itemName, int type) {
        SimpleItem item = null;
        if (type == FRAGMENT_SUBJECTS) {
            item = new Subject();
        }
        if (type == FRAGMENT_AUDIENCES) {
            item = new Audience();
        }
        if (type == FRAGMENT_EDUCATORS) {
            item = new Educator();
        }
        if (type == FRAGMENT_TYPELESSONS) {
            item = new Typelesson();
        }
        item.setName(itemName);
        absDao.insertItem(item);
        init();
    }

    @Override
    public void init() {

    }
}

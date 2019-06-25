package myapp.schedule.misha.myapplication.module.editData.page.edit;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.Constants;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.EditDataModel;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.SimpleItem;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

public class DialogFragmentEditDataPresenter extends BaseMainPresenter<DialogFragmentEditDataView> implements DialogFragmentEditDataPresenterInterface {

    private final EditDataModel editDataModel;
    private AbsDao absDao;
    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    public DialogFragmentEditDataPresenter(EditDataModel editDataModel) {
        this.editDataModel = editDataModel;
        absDao = editDataModel.getDao();
    }


    @Override
    public void init() {
        listItems = absDao.getAllData();
    }


    @Override
    public void insert(String itemName, int type) {
        SimpleItem item = null;
        if (type == Constants.FRAGMENT_SUBJECTS) {
            item = new Subject();
        }
        if (type == Constants.FRAGMENT_AUDIENCES) {
            item = new Audience();
        }
        if (type == Constants.FRAGMENT_EDUCATORS) {
            item = new Educator();
        }
        if (type == Constants.FRAGMENT_TYPELESSONS) {
            item = new Typelesson();
        }
        item.setName(itemName);
        absDao.insertItem(item);
        listItems.add(item);
        init();
    }

    @Override
    public void updateItem(String itemName, int type, int position) {
        SimpleItem item = null;
        if (type == Constants.FRAGMENT_SUBJECTS) {
            item = new Subject();
        }
        if (type == Constants.FRAGMENT_AUDIENCES) {
            item = new Audience();
        }
        if (type == Constants.FRAGMENT_EDUCATORS) {
            item = new Educator();
        }
        if (type == Constants.FRAGMENT_TYPELESSONS) {
            item = new Typelesson();
        }
        item.setId(String.valueOf(listItems.get(position).getId()));
        item.setName(itemName);
        absDao.updateItem(item, Long.parseLong(listItems.get(position).getId()));
        init();
    }

    @Override
    public void deleteItem(int position) {
        absDao.deleteItemById(Long.parseLong(listItems.get(position).getId()));
        listItems.remove(position);
        init();
    }


}

package com.example.misha.myapplication.module.schedule.edit.page.dialog.addData;

import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.database.AbsDao;
import com.example.misha.myapplication.entity.Audience;
import com.example.misha.myapplication.entity.EditDataModel;
import com.example.misha.myapplication.entity.Educator;
import com.example.misha.myapplication.entity.SimpleItem;
import com.example.misha.myapplication.entity.Subject;
import com.example.misha.myapplication.entity.Typelesson;

import java.util.ArrayList;

import static com.example.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static com.example.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static com.example.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static com.example.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class DialogFragmentDataPresenter extends BaseMainPresenter<DialogFragmentAddDataView> implements DialogFragmentDataPresenterInterface {

    private final EditDataModel editDataModel;
    private AbsDao absDao;
    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    public DialogFragmentDataPresenter(EditDataModel editDataModel) {
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
        listItems.add(item);
        init();
    }


}

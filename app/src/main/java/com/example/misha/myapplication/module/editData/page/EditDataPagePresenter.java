package com.example.misha.myapplication.module.editData.page;

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

public class EditDataPagePresenter extends BaseMainPresenter<EditDataFragmentPageView> implements EditDataPagePresenterInterface {

    private final EditDataModel editDataModel;

    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    private AbsDao absDao;


    public EditDataPagePresenter(EditDataModel editDataModel) {
        this.editDataModel = editDataModel;
        absDao = editDataModel.getDao();
    }

    public void onClearClick(int position) {
        getView().showEditDataDialog(listItems, position);
    }

    @Override
    public void init() {
        listItems = absDao.getAllData();
        getView().setupWidgets(editDataModel);
        getView().updateItemsAdapter(listItems);
    }

    public ArrayList<SimpleItem> getItemList() {
        listItems = absDao.getAllData();
        return listItems;
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

    @Override
    public String getNameAt(int position) {
        return listItems.get(position).getName();
    }

}

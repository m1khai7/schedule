package com.example.misha.myapplication.module.schedule.edit.page.dialog;

import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.database.AbsDao;
import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;
import com.example.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

import static com.example.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static com.example.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static com.example.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static com.example.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class DialogFragmentPresenter extends BaseMainPresenter<DialogFragmentListItemsView> implements DialogFragmentPresenterInterface {

    private AbsDao absDao;
    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    public DialogFragmentPresenter(int fragmentCode) {
        if (fragmentCode == FRAGMENT_SUBJECTS) {
            absDao = SubjectDao.getInstance();
        }
        if (fragmentCode == FRAGMENT_AUDIENCES) {
            absDao = AudienceDao.getInstance();
        }
        if (fragmentCode == FRAGMENT_EDUCATORS) {
            absDao = EducatorDao.getInstance();
        }
        if (fragmentCode == FRAGMENT_TYPELESSONS) {
            absDao = TypelessonDao.getInstance();
        }
    }

    @Override
    public void init() {
    }


    @Override
    public void onItemClick(int fragmentCode) {
        getView().showAddDataDialog(listItems, fragmentCode);
    }

    public ArrayList<SimpleItem> getItemList() {
        listItems = absDao.getAllData();
        return listItems;
    }


}

package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class DialogFragmentWeeksPresenter extends BaseMainPresenter<DialogFragmentWeeksView> implements DialogFragmentWeeksPresenterInterface {

    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    public DialogFragmentWeeksPresenter() {
    }

    @Override
    public void init() {
    }


    @Override
    public void onItemClick() {

    }

    public ArrayList<SimpleItem> getItemList() {
        return listItems;
    }


}

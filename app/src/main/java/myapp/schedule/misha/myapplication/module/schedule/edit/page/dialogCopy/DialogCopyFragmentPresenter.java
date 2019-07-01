package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class DialogCopyFragmentPresenter extends BaseMainPresenter<DialogCopyFragmentView> implements DialogCopyFragmentPresenterInterface {


    private ArrayList<CopyLesson> listItems = new ArrayList<>();

    public DialogCopyFragmentPresenter() {

    }

    @Override
    public void init() {
    }


    @Override
    public void onItemClick() {
        //getView().showLessonsDialog(listItems, fragmentCode);
    }

   /* public ArrayList<CopyLesson> getItemList() {

        return listItems;
    }*/


}

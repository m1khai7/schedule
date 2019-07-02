package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public class DialogSelectLessonFragmentPresenter extends BaseMainPresenter<DialogSelectLessonFragmentView> implements DialogSelectLessonFragmentPresenterInterface {

    private ArrayList<Calls> listItems = new ArrayList<>();

    public DialogSelectLessonFragmentPresenter() {
    }

    @Override
    public void init() {
    }


    @Override
    public void onItemClick(int position) {

    }

    public ArrayList<Calls> getItemList() {
        return listItems;
    }


}

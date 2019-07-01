package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public class DialogSelectWeekFragmentPresenter extends BaseMainPresenter<DialogSelectWeekFragmentView> implements DialogSelectWeekFragmentPresenterInterface {

    private ArrayList<SimpleItem> listItems = new ArrayList<>();

    public DialogSelectWeekFragmentPresenter() {
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

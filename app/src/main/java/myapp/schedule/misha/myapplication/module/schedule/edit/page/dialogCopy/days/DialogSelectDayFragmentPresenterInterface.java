package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.days;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface DialogSelectDayFragmentPresenterInterface {

    void onItemClick(int fragmentCode);

    ArrayList<SimpleItem> getItemList();


}

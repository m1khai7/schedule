package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialog;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface DialogFragmentPresenterInterface {

    void onItemClick(int fragmentCode);

    ArrayList<SimpleItem> getItemList();


}

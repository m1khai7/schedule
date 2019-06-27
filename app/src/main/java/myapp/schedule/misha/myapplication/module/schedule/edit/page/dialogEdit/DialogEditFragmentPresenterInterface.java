package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface DialogEditFragmentPresenterInterface {

    void onItemClick(int fragmentCode);

    ArrayList<SimpleItem> getItemList();


}

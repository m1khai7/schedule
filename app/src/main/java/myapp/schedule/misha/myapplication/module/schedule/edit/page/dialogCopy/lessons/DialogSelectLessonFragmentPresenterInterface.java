package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.lessons;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.SimpleItem;

public interface DialogSelectLessonFragmentPresenterInterface {

    void onItemClick(int position);

    ArrayList<Calls> getItemList();


}

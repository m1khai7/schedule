package com.example.misha.myapplication.module.schedule.edit.page;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

public interface EditSchedulePageFragmentView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";


    void updateView(ArrayList<Lesson> arrayList);

    void showEditDialog(ArrayList<? extends SimpleItem> subjectList, int position, int subject);

    void setWeek(int position);
}

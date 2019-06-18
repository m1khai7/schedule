package com.example.misha.myapplication.module.schedule.edit.page.dialog;

import com.example.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

public interface DialogFragmentPresenterInterface {

    void onItemClick(int fragmentCode);

    ArrayList<SimpleItem> getItemList();


}

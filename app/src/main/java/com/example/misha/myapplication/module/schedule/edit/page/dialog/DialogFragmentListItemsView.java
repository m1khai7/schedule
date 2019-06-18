package com.example.misha.myapplication.module.schedule.edit.page.dialog;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

public interface DialogFragmentListItemsView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";

    void showAddDataDialog(ArrayList<? extends SimpleItem> items, int fragmentCode);
}

package com.example.misha.myapplication.module.editData.page;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.EditDataModel;
import com.example.misha.myapplication.entity.SimpleItem;

import java.util.ArrayList;

public interface EditDataFragmentPageView extends BaseView {

    String ITEMS = "ITEMS";
    String FRAGMENT_CODE = "FRAGMENT_CODE";
    String POSITION = "POSITION";


    void updateItemsAdapter(ArrayList<SimpleItem> listItems);

    void setupWidgets(EditDataModel editDataModel);

    void showEditDataDialog(ArrayList<? extends SimpleItem> items, int position);
}

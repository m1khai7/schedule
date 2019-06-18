package com.example.misha.myapplication.module.editData.page.edit;

public interface DialogFragmentEditDataPresenterInterface {

    void insert(String itemName, int type);

    void deleteItem(int position);

    void updateItem(String itemName, int type, int position);
}

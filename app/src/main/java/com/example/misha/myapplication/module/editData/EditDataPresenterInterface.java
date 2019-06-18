package com.example.misha.myapplication.module.editData;

import com.example.misha.myapplication.data.database.AbsDao;

public interface EditDataPresenterInterface {

    void onClear(AbsDao absDao);

    void onCreateDialog(AbsDao absDao, int selectedTabPosition);

    void onClearClick(int id);
}

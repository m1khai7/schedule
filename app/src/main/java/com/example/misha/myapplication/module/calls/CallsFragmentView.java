package com.example.misha.myapplication.module.calls;

import com.example.misha.myapplication.common.core.BaseView;
import com.example.misha.myapplication.entity.Calls;

import java.util.ArrayList;

public interface CallsFragmentView extends BaseView {

    void updateView(ArrayList<Calls> callsList);


}

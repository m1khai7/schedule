package myapp.schedule.misha.myapplication.module.calls;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Calls;

public interface CallsFragmentView extends BaseView {

    void updateView(ArrayList<Calls> callsList);


}

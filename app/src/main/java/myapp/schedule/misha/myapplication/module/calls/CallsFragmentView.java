package myapp.schedule.misha.myapplication.module.calls;

import myapp.schedule.misha.myapplication.common.core.BaseView;
import myapp.schedule.misha.myapplication.entity.Calls;

import java.util.ArrayList;

public interface CallsFragmentView extends BaseView {

    void updateView(ArrayList<Calls> callsList);


}

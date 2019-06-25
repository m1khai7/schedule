package myapp.schedule.misha.myapplication.module.settings.transfer;

import myapp.schedule.misha.myapplication.common.core.BaseView;

public interface TransferFragmentView extends BaseView {

    void openFragmentSchedule();

    void showProgressDialog();

    void hideProgressDialog();

    void openDirectory();


}

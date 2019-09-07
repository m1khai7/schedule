package myapp.schedule.misha.myapplication.module.settings.menu;

import android.content.Context;

import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.util.DataUtil;

public class MenuPresenter extends BaseMainPresenter<MenuFragmentView> implements MenuPresenterInterface {

    private Context context;

    public MenuPresenter(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void init() {
    }

    @Override
    public void onSelectDateStartSemester() {
        DataUtil.getCurrentDate(context);
    }

    @Override
    public void OnSelectTheme() {
        getView().showDialogSelectTheme();
    }

    @Override
    public void OnShowDevInfo() {
        getView().showDialogDevInfo().show();
    }

    @Override
    public void OnTransferData() {
        getView().openFragmentTransferData();
    }

    @Override
    public void onCreateLesson() {
        getView().showFragmentCreateLesson();
    }
}


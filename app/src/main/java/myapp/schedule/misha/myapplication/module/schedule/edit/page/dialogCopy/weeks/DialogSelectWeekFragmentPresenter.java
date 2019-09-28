package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.weeks;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;

public class DialogSelectWeekFragmentPresenter extends BaseMainPresenter<DialogSelectWeekFragmentView> implements DialogSelectWeekFragmentPresenterInterface {

    @Override
    public void init() {
    }

    @Override
    public void onSelectAllClicked() {
        getView().selectAll();
        getView().updateAdapter();
    }

}

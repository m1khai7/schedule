package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.entity.CopyLesson;
import myapp.schedule.misha.myapplication.entity.Weeks;

public class DialogCopyFragmentPresenter extends BaseMainPresenter<DialogCopyFragmentView> implements DialogCopyFragmentPresenterInterface {

    private ArrayList<CopyLesson> listLessonsForCopy = new ArrayList<>();

    @Override
    public void init() {
    }

    @Override
    public void onDialogLessonClick() {
        getView().showLessonDialog();
    }

    @Override
    public void onImageAddClick(int id, int numberDay, String timeLesson) {
        CopyLesson copyLesson = new CopyLesson();
        copyLesson.setId(id);
        copyLesson.setDay(numberDay);
        copyLesson.setTimeLesson(timeLesson);
        listLessonsForCopy.add(copyLesson);
        getView().updateItemsAdapter(listLessonsForCopy);
    }

    @Override
    public void onImageDeleteClick(ArrayList<CopyLesson> itemList, int position) {
        itemList.remove(position);
        getView().updateItemsAdapter(listLessonsForCopy);
    }

    public void showWeeks(ArrayList<Weeks> listWeeks) {
        getView().openWeekDialog(listWeeks);

    }

    public void onClickCopyLesson() {
        getView().copyLesson();
    }
}



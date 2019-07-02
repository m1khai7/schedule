package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.entity.CopyLesson;

public class DialogCopyFragmentPresenter extends BaseMainPresenter<DialogCopyFragmentView> implements DialogCopyFragmentPresenterInterface {
    private ArrayList<CopyLesson> copyLessons = new ArrayList<>();

    public DialogCopyFragmentPresenter() {
    }

    @Override
    public void init() {
    }

    @Override
    public void onDialogWeekClick() {
        getView().showWeekDialog();
    }

    @Override
    public void onDialogLessonClick() {
        getView().showLessonDialog();
    }

    @Override
    public void onImageAddClick(String day, String timeLesson) {
        CopyLesson copyLesson = new CopyLesson();
        copyLesson.setDay(day);
        copyLesson.setTimeLesson(timeLesson);
        copyLessons.add(copyLesson);
        getView().updateItemsAdapter(copyLessons);
    }


    @Override
    public void onImageDeleteClick(ArrayList<CopyLesson> itemList, int position) {
        itemList.remove(position);
        getView().updateItemsAdapter(copyLessons);
    }
}



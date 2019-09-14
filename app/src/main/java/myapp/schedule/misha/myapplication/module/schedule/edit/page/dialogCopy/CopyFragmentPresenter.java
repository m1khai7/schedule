package myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.entity.CopyLesson;

public class CopyFragmentPresenter extends BaseMainPresenter<CopyFragmentView> implements FragmentPresenterInterface {

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
    public void onImageDeleteClick(ArrayList<CopyLesson> listLessons, int position) {
        listLessons.remove(position);
        getView().updateItemsAdapter(listLessons);
    }

    public void showWeeks() {
        getView().openWeekDialog();
    }

    public void onClickCopyLesson() {
        getView().copyLesson();
    }
}



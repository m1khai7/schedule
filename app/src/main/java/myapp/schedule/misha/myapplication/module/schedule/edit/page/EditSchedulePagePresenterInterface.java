package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.entity.Lesson;

public interface EditSchedulePagePresenterInterface {

    void onSubjectClick(int position);

    void onAudienceClick(int position);

    void onEducatorClick(int position);

    void onTypelessonClick(int position);

    void onCopyUpClick(int position);

    void onCopyDownClick(int position);

    void onClearLessonClick(int position);

    void setWeek(int position);

    ArrayList<Lesson> getLessonList();

}

package myapp.schedule.misha.myapplication.activity;

import android.os.Bundle;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.module.settings.lesson.container.CreateLessonContainer;

public class ActivityCreateLesson extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(CreateLessonContainer.newInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_default;
    }
}
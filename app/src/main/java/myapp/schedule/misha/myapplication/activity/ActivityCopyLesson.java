package myapp.schedule.misha.myapplication.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.EditSchedulePageFragmentView;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogCopy.CopyFragment;

public class ActivityCopyLesson extends BaseActivity {

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getToolbar().getNavigationIcon().setColorFilter(ScheduleApp.getClr(R.color.white), PorterDuff.Mode.SRC_ATOP);
        // Preferences.getInstance().SELECT_THEME.equals(DARK_THEME) ? R.color.white : R.color.black20), PorterDuff.Mode.SRC_ATOP);
        Lesson lesson = getIntent().getParcelableExtra(EditSchedulePageFragmentView.CURRENT_LESSON);
        replaceFragment(CopyFragment.newInstance(lesson));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_default;
    }
}
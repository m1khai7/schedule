package myapp.schedule.misha.myapplication.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.data.database.DatabaseHelper;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.module.calls.CallsFragment;
import myapp.schedule.misha.myapplication.module.editData.EditDataFragment;
import myapp.schedule.misha.myapplication.module.exploreList.ScheduleListFragment;
import myapp.schedule.misha.myapplication.module.schedule.exploreDays.ScheduleFragment;
import myapp.schedule.misha.myapplication.module.settings.SettingsFragment;

public class MainActivity extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if (!Preferences.getInstance().isHintsOpened()) {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            Preferences.getInstance().setHintsOpened();
        }*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.what:
                            Fragment fragment = new CallsFragment();
                            replaceFragment(fragment);
                            return true;
                        case R.id.editData:
                            fragment = new EditDataFragment();
                            replaceFragment(fragment);
                            return true;
                        case R.id.scheduleDays:
                            fragment = new ScheduleFragment();
                            replaceFragment(fragment);
                            return true;
                        case R.id.scheduleList:
                            fragment = new ScheduleListFragment();
                            showProgressBar();
                            replaceFragment(fragment);
                            return true;
                        case R.id.settings:
                            fragment = new SettingsFragment();
                            replaceFragment(fragment);
                            return true;
                    }
                    return false;
                });
        bottomNavigationView.setSelectedItemId(R.id.scheduleDays);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new DatabaseHelper(this).getWritableDatabase();
        CallDao.getInstance().initTable();
    }


}
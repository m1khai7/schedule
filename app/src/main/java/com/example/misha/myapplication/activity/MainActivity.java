package com.example.misha.myapplication.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseActivity;
import com.example.misha.myapplication.data.database.DatabaseHelper;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.module.calls.CallsFragment;
import com.example.misha.myapplication.module.editData.EditDataFragment;
import com.example.misha.myapplication.module.exploreList.ScheduleListFragment;
import com.example.misha.myapplication.module.schedule.exploreDays.ScheduleFragment;
import com.example.misha.myapplication.module.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                        case R.id.scheduleList:
                            fragment = new ScheduleListFragment();
                            replaceFragment(fragment);
                            return true;
                        case R.id.scheduleDays:
                            fragment = new ScheduleFragment();
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
package myapp.schedule.misha.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.data.database.DatabaseHelper;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DatabaseHelper(this).getWritableDatabase();
        CallDao.getInstance().initTable();
        decideNextActivity();
    }

    public void decideNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
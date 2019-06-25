package myapp.schedule.misha.myapplication.module.calls;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;

import myapp.schedule.misha.myapplication.common.core.BaseActivity;
import myapp.schedule.misha.myapplication.common.core.BaseMainPresenter;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Calls;

public class CallsPresenter extends BaseMainPresenter<CallsFragmentView> implements CallsPresenterInterface {

    private Context context;
    private Calendar calendarTimeCalls = Calendar.getInstance();
    private TimePickerDialog.OnTimeSetListener timeOne = (view, hourOfDay, minute) -> {
        calendarTimeCalls.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarTimeCalls.set(Calendar.MINUTE, minute);
        setTimeOne();
    };

    private TimePickerDialog.OnTimeSetListener timeTwo = (view, hourOfDay, minute) -> {
        calendarTimeCalls.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarTimeCalls.set(Calendar.MINUTE, minute);
        setTimeTwo();
    };

    public CallsPresenter(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void init() {
    }

    @Override
    public void onClickOneTime(int position) {
        Preferences.getInstance().setSelectedPositionLesson(position * 2);
        new TimePickerDialog(context, timeOne,
                calendarTimeCalls.get(Calendar.HOUR_OF_DAY),
                calendarTimeCalls.get(Calendar.MINUTE), true)
                .show();
    }

    @Override
    public void onClickTwoTime(int position) {
        Preferences.getInstance().setSelectedPositionLesson(position * 2 + 1);
        new TimePickerDialog(context, timeTwo,
                calendarTimeCalls.get(Calendar.HOUR_OF_DAY),
                calendarTimeCalls.get(Calendar.MINUTE), true)
                .show();
    }

    private void setTimeOne() {
        ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
        callsList.get(Preferences.getInstance().getSelectedPositionLesson()).setName(DateUtils.formatDateTime(context,
                calendarTimeCalls.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
        getView().updateView(callsList);
        CallDao.getInstance().updateItemByID(callsList.get(Preferences.getInstance().getSelectedPositionLesson()));
    }

    private void setTimeTwo() {
        ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
        callsList.get(Preferences.getInstance().getSelectedPositionLesson()).setName(DateUtils.formatDateTime(context,
                calendarTimeCalls.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
        getView().updateView(callsList);
        CallDao.getInstance().updateItemByID(callsList.get(Preferences.getInstance().getSelectedPositionLesson()));


    }


}


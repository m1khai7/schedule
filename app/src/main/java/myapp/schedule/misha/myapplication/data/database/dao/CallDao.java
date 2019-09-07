package myapp.schedule.misha.myapplication.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.AppContentProvider;
import myapp.schedule.misha.myapplication.entity.Calls;

public class CallDao extends AbsDao<Calls> {

    private final static String ID = "id";
    private final static String TIME = "time_lesson";
    private static final String[] ALL_SET_PROPERTIES = new String[]{ID, TIME};
    private static volatile CallDao instance;

    private CallDao() {
    }

    public static CallDao getInstance() {
        if (null == instance) {
            instance = new CallDao();
        }
        return instance;
    }

    @Override
    protected String[] getAllColumns() {
        return ALL_SET_PROPERTIES;
    }

    @Override
    protected Uri getTableUri() {
        return AppContentProvider.CALLS_URI;
    }

    @Override
    protected Calls makeInstanceFromCursor(Cursor cursor) {
        Calls calls = new Calls();
        calls.setId(getString(cursor, ID));
        calls.setName(getString(cursor, TIME));
        return calls;
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Calls instance) {
        ContentValues set = new ContentValues();
        set.put(ID, instance.getId());
        set.put(TIME, instance.getName());
        return set;
    }

    public void initTable() {
        if (!getAllData().isEmpty()) return;
        ArrayList<Calls> calls = new ArrayList<>();
        calls.add(new Calls(String.valueOf(1), "8:30"));
        calls.add(new Calls(String.valueOf(2), "10:00"));
        calls.add(new Calls(String.valueOf(3), "10:10"));
        calls.add(new Calls(String.valueOf(4), "11:40"));
        calls.add(new Calls(String.valueOf(5), "12:20"));
        calls.add(new Calls(String.valueOf(6), "13:50"));
        calls.add(new Calls(String.valueOf(7), "14:00"));
        calls.add(new Calls(String.valueOf(8), "15:30"));
        calls.add(new Calls(String.valueOf(9), "15:40"));
        calls.add(new Calls(String.valueOf(10), "17:10"));
        calls.add(new Calls(String.valueOf(11), "17:30"));
        calls.add(new Calls(String.valueOf(12), "19:00"));
        insertAll(calls);
    }

    public boolean updateItemByID(Calls calls) {
        int affectedRows = getContentResolver().update(getTableUri(), makeContentValuesFromInstance(calls),
                KEY_ID + EQUALS, new String[]{String.valueOf(calls.getId())});
        return affectedRows == 1;
    }

}

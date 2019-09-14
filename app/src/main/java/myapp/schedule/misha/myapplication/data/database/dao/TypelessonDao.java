package myapp.schedule.misha.myapplication.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.AppContentProvider;
import myapp.schedule.misha.myapplication.entity.Typelesson;

public class TypelessonDao extends AbsDao<Typelesson> {

    public final static String ID = "id";
    public final static String TYPELESSON = "typelesson";
    public static final String[] ALL_SET_PROPERTIES = new String[]{ID, TYPELESSON};
    private static volatile TypelessonDao instance;

    private TypelessonDao() {
    }

    public static TypelessonDao getInstance() {
        if (null == instance) {
            instance = new TypelessonDao();
        }
        return instance;
    }

    public Typelesson getItemByName(String name) {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                TYPELESSON + EQUALS, new String[]{String.valueOf(name)}, null);
        ArrayList<Typelesson> models = extractItemsFromCursor(cursor);
        return models.isEmpty() ? null : models.get(0);
    }

    @Override
    protected String[] getAllColumns() {
        return ALL_SET_PROPERTIES;
    }

    @Override
    protected Uri getTableUri() {
        return AppContentProvider.TYPELESSONS_URI;
    }

    @Override
    protected Typelesson makeInstanceFromCursor(Cursor cursor) {
        Typelesson typelesson = new Typelesson();
        typelesson.setId(getString(cursor, ID));
        typelesson.setName(getString(cursor, TYPELESSON));
        return typelesson;
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Typelesson instance) {
        ContentValues set = new ContentValues();
        set.put(ID, instance.getId());
        set.put(TYPELESSON, instance.getName());
        return set;
    }
}

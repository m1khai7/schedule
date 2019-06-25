package myapp.schedule.misha.myapplication.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.AppContentProvider;
import myapp.schedule.misha.myapplication.entity.Audience;

public class AudienceDao extends AbsDao<Audience> {

    public final static String ID = "id";
    public final static String AUDIENCE = "audience";
    public static final String[] ALL_SET_PROPERTIES = new String[]{ID, AUDIENCE};
    private static volatile AudienceDao instance;

    private AudienceDao() {
    }

    public static AudienceDao getInstance() {
        if (null == instance) {
            instance = new AudienceDao();
        }
        return instance;
    }

    @Override
    protected String[] getAllColumns() {
        return ALL_SET_PROPERTIES;
    }


    @Override
    protected Uri getTableUri() {
        return AppContentProvider.AUDIENCES_URI;
    }

    @Override
    protected Audience makeInstanceFromCursor(Cursor cursor) {
        Audience audience = new Audience();
        audience.setId(getString(cursor, ID));
        audience.setName(getString(cursor, AUDIENCE));
        return audience;
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Audience instance) {
        ContentValues set = new ContentValues();
        set.put(ID, instance.getId());
        set.put(AUDIENCE, instance.getName());
        return set;
    }
}

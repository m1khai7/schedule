package myapp.schedule.misha.myapplication.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.AppContentProvider;
import myapp.schedule.misha.myapplication.entity.Subject;

public class SubjectDao extends AbsDao<Subject> {

    public final static String ID = "id";
    public final static String SUBJECT = "subject";
    public static final String[] ALL_SET_PROPERTIES = new String[]{ID, SUBJECT};
    private static volatile SubjectDao instance;

    private SubjectDao() {
    }

    public static SubjectDao getInstance() {
        if (instance == null) {
            instance = new SubjectDao();
        }
        return instance;
    }

    @Override
    protected String[] getAllColumns() {
        return ALL_SET_PROPERTIES;
    }

    @Override
    protected Uri getTableUri() {
        return AppContentProvider.SUBJECTS_URI;
    }

    @Override
    protected Subject makeInstanceFromCursor(Cursor cursor) {
        Subject subject = new Subject();
        subject.setId(getString(cursor, ID));
        subject.setName(getString(cursor, SUBJECT));
        return subject;
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Subject instance) {
        ContentValues set = new ContentValues();
        set.put(ID, instance.getId());
        set.put(SUBJECT, instance.getName());
        return set;
    }
}

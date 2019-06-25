package myapp.schedule.misha.myapplication.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.data.database.AbsDao;
import myapp.schedule.misha.myapplication.data.database.AppContentProvider;
import myapp.schedule.misha.myapplication.entity.Lesson;

public class LessonDao extends AbsDao<Lesson> {

    public final static String ID = "id";
    public final static String NUMBER_WEEK = "number_week";
    public final static String NUMBER_DAY = "number_day";
    public final static String NUMBER_LESSON = "number_lesson";
    public final static String ID_SUBJECT = "id_subject";
    public final static String ID_AUDIENCE = "id_audience";
    public final static String ID_EDUCATOR = "id_educator";
    public final static String ID_TYPE_LESSON = "id_typelesson";
    public static final String[] ALL_LESSONS_PROPERTIES = new String[]{ID, NUMBER_WEEK,
            NUMBER_DAY, NUMBER_LESSON, ID_SUBJECT, ID_AUDIENCE, ID_EDUCATOR, ID_TYPE_LESSON};
    private static volatile LessonDao instance;

    private LessonDao() {
    }

    public static LessonDao getInstance() {
        if (null == instance) {
            instance = new LessonDao();
        }
        return instance;
    }

    @Override
    protected String[] getAllColumns() {
        return ALL_LESSONS_PROPERTIES;
    }

    @Override
    protected Uri getTableUri() {
        return AppContentProvider.LESSONS_URI;
    }

    @Override
    protected Lesson makeInstanceFromCursor(Cursor cursor) {
        Lesson lesson = new Lesson();
        lesson.setId(getString(cursor, ID));
        lesson.setNumber_week(getString(cursor, NUMBER_WEEK));
        lesson.setNumber_day(getString(cursor, NUMBER_DAY));
        lesson.setNumber_lesson((getString(cursor, NUMBER_LESSON)));
        lesson.setId_subject(getString(cursor, ID_SUBJECT));
        lesson.setId_audience(getString(cursor, ID_AUDIENCE));
        lesson.setId_educator(getString(cursor, ID_EDUCATOR));
        lesson.setId_typelesson(getString(cursor, ID_TYPE_LESSON));
        return lesson;
    }

    @Override
    protected ContentValues makeContentValuesFromInstance(Lesson instance) {
        ContentValues set = new ContentValues();
        set.put(ID, instance.getId());
        set.put(NUMBER_WEEK, instance.getNumber_week());
        set.put(NUMBER_DAY, instance.getNumber_day());
        set.put(NUMBER_LESSON, instance.getNumber_lesson());
        set.put(ID_SUBJECT, instance.getId_subject());
        set.put(ID_AUDIENCE, instance.getId_audience());
        set.put(ID_EDUCATOR, instance.getId_educator());
        set.put(ID_TYPE_LESSON, instance.getId_typelesson());
        return set;
    }


    public ArrayList<Lesson> getLessonByWeekAndDay(int week, int day) {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                NUMBER_WEEK + EQUALS + " AND " + NUMBER_DAY + EQUALS, new String[]{String.valueOf(week), String.valueOf(day)}, null);
        return extractItemsFromCursor(cursor);
    }


    public ArrayList<Lesson> getLessonByWeek(int week) {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                NUMBER_WEEK + EQUALS, new String[]{String.valueOf(week)}, null);
        return extractItemsFromCursor(cursor);
    }


    public boolean updateItemByID(Lesson lesson) {
        int affectedRows = getContentResolver().update(getTableUri(), makeContentValuesFromInstance(lesson),
                ID + EQUALS, new String[]{String.valueOf(lesson.getId())});
        return affectedRows == 1;
    }

}

package myapp.schedule.misha.myapplication.data.database;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.ScheduleApp;

/**
 * Abstract data access object.
 *
 * @param <T> model template
 */
public abstract class AbsDao<T> implements DaoInterface<T> {

    /**
     * Equal string.
     */
    protected static final String EQUALS = "=?";
    /**
     * Key for 'ID' column in the workouts table.
     */
    protected static final String KEY_ID = "id";


    /**
     * Get value for specified value from cursor.
     *
     * @param cursor     Cursor to get value from.
     * @param columnName Name of column.
     * @return Value of column from the cursor.
     */
    protected String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    /**
     * Get value for specified value from cursor for int.
     *
     * @param cursor     cursor
     * @param columnName column group
     * @return int column value
     */
    protected int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    /**
     * Getter for all columns array.
     *
     * @return all columns array
     */
    protected abstract String[] getAllColumns();

    /**
     * Getter for table uri.
     *
     * @return table uri
     */
    protected abstract Uri getTableUri();

    /**
     * Get all records in table.
     *
     * @return all records in table array
     */
    public ArrayList<T> getAllData() {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                null, null, null);
        return extractItemsFromCursor(cursor);
    }

    /**
     * Return content resolver to get access to content providers.
     *
     * @return ContentResolver class instance
     */
    protected ContentResolver getContentResolver() {
        return ScheduleApp.getAppContext().getContentResolver();
    }

    /**
     * Make class instance form cursor.
     *
     * @param cursor cursor
     * @return class instance
     */
    protected abstract T makeInstanceFromCursor(Cursor cursor);

    /**
     * Make content values from class instance.
     *
     * @param instance instance
     * @return content values
     */
    protected abstract ContentValues makeContentValuesFromInstance(T instance);

    /**
     * Extract class instances from cursor.
     *
     * @param cursor Cursor
     * @return class instances array
     */
    protected ArrayList<T> extractItemsFromCursor(Cursor cursor) {
        ArrayList<T> items = new ArrayList<>();
        if (cursor == null) return items;
        if (cursor.moveToFirst()) {
            do {
                try {
                    items.add(makeInstanceFromCursor(cursor));
                } catch (CursorIndexOutOfBoundsException e) {
                    return items;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    @Override
    public T getItemByID(long id) {
        Cursor cursor = getContentResolver().query(getTableUri(), getAllColumns(),
                KEY_ID + EQUALS, new String[]{String.valueOf(id)}, null);
        ArrayList<T> models = extractItemsFromCursor(cursor);
        return models.isEmpty() ? null : models.get(0);
    }

    @Override
    public void insertItem(T item) {
        getContentResolver().insert(getTableUri(), makeContentValuesFromInstance(item));
    }


    @Override
    public void updateItem(T item, long id) {
        getContentResolver().update(getTableUri(), makeContentValuesFromInstance(item), KEY_ID + EQUALS, new String[]{String.valueOf(id)});

    }


    @Override
    public void insertAll(ArrayList<T> items) {
        for (T item : items) {
            insertItem(item);
        }
    }


    @Override
    public void deleteAll() {
        getContentResolver().delete(getTableUri(), null, null);
    }

    @Override
    public boolean deleteItemById(long id) {
        int affectedRowsNumber = getContentResolver().delete(getTableUri(), KEY_ID + EQUALS,
                new String[]{String.valueOf(id)});
        return affectedRowsNumber == 1;
    }
}

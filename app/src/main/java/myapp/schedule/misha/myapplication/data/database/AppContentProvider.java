package myapp.schedule.misha.myapplication.data.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;

/**
 * Class provides access to application data.
 */
@SuppressWarnings("PMD")
public class AppContentProvider extends ContentProvider {

    public static final String LESSONS_TABLE = "lessons";
    public static final String SUBJECTS_TABLE = "subjects";
    public static final String AUDIENCES_TABLE = "audiences";
    public static final String EDUCATORS_TABLE = "educators";
    public static final String TYPELESSONS_TABLE = "typelessons";
    public static final String CALLS_TABLE = "calls";
    public static final String LIMIT = "limit";
    /**
     * Application Content provider URI.
     */
    private static final Uri PROVIDER_URI
            = Uri.parse("content://com.schedule.misha.myapplication.provider/");
    public static final Uri LESSONS_URI = Uri.parse(PROVIDER_URI + LESSONS_TABLE);
    public static final Uri SUBJECTS_URI = Uri.parse(PROVIDER_URI + SUBJECTS_TABLE);
    public static final Uri AUDIENCES_URI = Uri.parse(PROVIDER_URI + AUDIENCES_TABLE);
    public static final Uri EDUCATORS_URI = Uri.parse(PROVIDER_URI + EDUCATORS_TABLE);
    public static final Uri TYPELESSONS_URI = Uri.parse(PROVIDER_URI + TYPELESSONS_TABLE);
    public static final Uri CALLS_URI = Uri.parse(PROVIDER_URI + CALLS_TABLE);
    /**
     * Database helper object.
     */
    private SQLiteOpenHelper database;

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public synchronized Cursor query(@NonNull Uri uri, String[] projection, String selection,
                                     String[] selectionArgs, String sortOrder) {
        String tableName = getType(uri);
        if (tableName == null) return null;
        try {
            return database.getReadableDatabase().query(tableName, projection, selection,
                    selectionArgs, null, null, sortOrder, uri.getQueryParameter(LIMIT));
        } catch (SQLiteException exception) {
            return null;
        }
    }

    @Override
    public synchronized int update(@NonNull Uri uri, ContentValues values, String selection,
                                   String[] selectionArgs) {
        String tableName = getType(uri);
        if (tableName == null) return 0;
        int affectedRowsNumber = database.getWritableDatabase().update(
                tableName, values, selection, selectionArgs);
        if (affectedRowsNumber > 0) getContext().getContentResolver().notifyChange(uri, null);
        return affectedRowsNumber;
    }

    @Override
    public synchronized Uri insert(@NonNull Uri uri, ContentValues values) {
        String tableName = getType(uri);
        if (tableName == null) return null;
        long recordID = database.getWritableDatabase().insert(tableName, null, values);
        if (recordID == -1) return null;
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(tableName + "#" + recordID);
    }

    @Override
    public synchronized int delete(@NonNull Uri uri, String selection, String[] selArgs) {
        String tableName = getType(uri);
        if (tableName == null) {
            return 0;
        }
        int affectedRowsNumber = database.getWritableDatabase().delete(
                tableName, selection, selArgs);
        if (affectedRowsNumber > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return affectedRowsNumber;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        if (uri.toString().startsWith(SUBJECTS_URI.toString())) {
            return SUBJECTS_TABLE;
        } else if (uri.toString().startsWith(LESSONS_URI.toString())) {
            return LESSONS_TABLE;
        } else if (uri.toString().startsWith(AUDIENCES_URI.toString())) {
            return AUDIENCES_TABLE;
        } else if (uri.toString().startsWith(EDUCATORS_URI.toString())) {
            return EDUCATORS_TABLE;
        } else if (uri.toString().startsWith(TYPELESSONS_URI.toString())) {
            return TYPELESSONS_TABLE;
        } else if (uri.toString().startsWith(CALLS_URI.toString())) {
            return CALLS_TABLE;
        } else {
            return null;
        }
    }
}

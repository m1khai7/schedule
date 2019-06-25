package myapp.schedule.misha.myapplication.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.entity.Lesson;


/**
 * Class creates data base if it don't exist.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Name of database file.
     */
    public static final String DATABASE_NAME = "Lesson.db";
    /**
     * Database version.
     */
    public static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE  subjects " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "subject VARCHAR UNIQUE ON CONFLICT IGNORE );";
    private static final String CREATE_TABLE_AUDIENCES = "CREATE TABLE  audiences  " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "audience VARCHAR UNIQUE ON CONFLICT IGNORE );";
    private static final String CREATE_TABLE_EDUCATORS = "CREATE TABLE  educators  " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "educator VARCHAR UNIQUE ON CONFLICT IGNORE );";
    private static final String CREATE_TABLE_TYPELESSONS = "CREATE TABLE  typelessons  " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "typelesson VARCHAR UNIQUE ON CONFLICT IGNORE );";
    private static final String CREATE_CALL_SCHEDULE = "CREATE TABLE calls " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "time_lesson VARCHAR UNIQUE ON CONFLICT IGNORE );";
    private static final String CREATE_TABLE_LESSONS = "CREATE TABLE lessons " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "number_week INTEGER, number_day INTEGER, number_lesson INTEGER, id_subject INTEGER," +
            " id_audience INTEGER, id_educator INTEGER, " +
            "id_typelesson INTEGER," +
            "FOREIGN KEY (id_subject) REFERENCES subjects (id)  ON DELETE CASCADE, " +
            "FOREIGN KEY (id_audience) REFERENCES audiences (id)   ON DELETE CASCADE, " +
            "FOREIGN KEY (id_educator) REFERENCES educators (id)   ON DELETE CASCADE, " +
            "FOREIGN KEY (id_typelesson) REFERENCES typelessons (id)   ON DELETE CASCADE, " +
            "FOREIGN KEY (number_lesson) REFERENCES calls (id))";
    /**
     * Migration list.
     */
    private final ArrayList<Patch> migrationsList = new ArrayList<Patch>() {
        {
            add(createV1Patch());
        }
    };


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sdb) {
        for (int i = 0; i < migrationsList.size(); i++) {
            migrationsList.get(i).apply(sdb);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            migrationsList.get(i).revert(sdb);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            migrationsList.get(i).apply(sdb);
        }
    }


    /**
     * Create v1 patch.
     *
     * @return v1 patch
     */
    private Patch createV1Patch() {
        return new Patch() {

            public void apply(SQLiteDatabase sdb) {
                sdb.execSQL(CREATE_TABLE_SUBJECTS);
                sdb.execSQL(CREATE_TABLE_AUDIENCES);
                sdb.execSQL(CREATE_TABLE_EDUCATORS);
                sdb.execSQL(CREATE_TABLE_TYPELESSONS);
                sdb.execSQL(CREATE_CALL_SCHEDULE);
                sdb.execSQL(CREATE_TABLE_LESSONS);

                sdb.beginTransaction();

                try {

                    ArrayList<Lesson> lessons = new ArrayList<>();
                    for (int week = 1; week < 18; week++) {
                        for (int day = 1; day < 7; day++) {
                            for (int timeLesson = 1; timeLesson < 7; timeLesson++) {
                                lessons.add(new Lesson(week, day, timeLesson, 0, 0, 0, 0));
                                ContentValues set = new ContentValues();
                                set.put(LessonDao.NUMBER_WEEK, week);
                                set.put(LessonDao.NUMBER_DAY, day);
                                set.put(LessonDao.NUMBER_LESSON, timeLesson);
                                set.put(LessonDao.ID_SUBJECT, 0);
                                set.put(LessonDao.ID_AUDIENCE, 0);
                                set.put(LessonDao.ID_EDUCATOR, 0);
                                set.put(LessonDao.ID_TYPE_LESSON, 0);

                                sdb.insert(AppContentProvider.LESSONS_TABLE, null, set);
                            }
                        }
                    }

                    sdb.setTransactionSuccessful();
                } finally {

                    sdb.endTransaction();
                }

            }

            @Override
            public void revert(SQLiteDatabase sdb) {
                //do nothing
            }
        };
    }

    /**
     * Database patch abstract class.
     */
    abstract class Patch {

        /**
         * Apply patch.
         *
         * @param sdb database
         */
        public abstract void apply(SQLiteDatabase sdb);

        /**
         * Revert patch.
         *
         * @param sdb database
         */
        public abstract void revert(SQLiteDatabase sdb);
    }
}

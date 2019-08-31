package myapp.schedule.misha.myapplication.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class CopyLesson implements Parcelable {

    public static final Creator<CopyLesson> CREATOR = new Creator<CopyLesson>() {
        @Override
        public CopyLesson createFromParcel(Parcel in) {
            return new CopyLesson(in);
        }

        @Override
        public CopyLesson[] newArray(int size) {
            return new CopyLesson[size];
        }
    };

    private int day;

    private int id;

    private String timeLesson;

    public CopyLesson(int day, int id, String timeLesson) {
        this.id = id;
        this.day = day;
        this.timeLesson = timeLesson;
    }

    public CopyLesson(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.day = cursor.getInt(1);
        this.timeLesson = cursor.getString(2);
    }

    protected CopyLesson(Parcel in) {
        id = in.readInt();
        day = in.readInt();
        timeLesson = in.readString();
    }

    public CopyLesson() {
    }

    public String getTimeLesson() {
        return timeLesson;
    }

    public void setTimeLesson(String timeLesson) {
        this.timeLesson = timeLesson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(day);
        dest.writeString(timeLesson);
    }

}

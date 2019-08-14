package myapp.schedule.misha.myapplication.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    private String timeLesson;

    public CopyLesson(int day, String timeLesson) {
        this.day = day;
        this.timeLesson = timeLesson;
    }

    public CopyLesson(Cursor cursor) {
        this.day = cursor.getInt(0);
        this.timeLesson = cursor.getString(1);
    }

    protected CopyLesson(Parcel in) {
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
        dest.writeInt(day);
        dest.writeString(timeLesson);
    }

}

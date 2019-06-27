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
    @Expose
    @SerializedName("day")
    private String day;
    @Expose
    @SerializedName("timeLesson")
    private String timeLesson;

    public CopyLesson(String day, String timeLesson) {
        this.day = day;
        this.timeLesson = timeLesson;
    }

    public CopyLesson(Cursor cursor) {
        this.day = cursor.getString(0);
        this.timeLesson = cursor.getString(1);
    }

    protected CopyLesson(Parcel in) {
        day = in.readString();
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(timeLesson);
    }

}

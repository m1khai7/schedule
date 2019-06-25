package myapp.schedule.misha.myapplication.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//Todo прочитать про сериализацию и Parcelable
public class Typelesson implements Parcelable, SimpleItem {

    public static final Creator<Typelesson> CREATOR = new Creator<Typelesson>() {
        @Override
        public Typelesson createFromParcel(Parcel in) {
            return new Typelesson(in);
        }

        @Override
        public Typelesson[] newArray(int size) {
            return new Typelesson[size];
        }
    };
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("name_typelesson")
    private String name;


    public Typelesson(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Typelesson(Cursor cursor) {
        this.id = cursor.getString(0);
        this.name = cursor.getString(1);
    }

    protected Typelesson(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public Typelesson() {

    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}

package myapp.schedule.misha.myapplication.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;


//Todo прочитать про сериализацию и Parcelable
public class Weeks implements Parcelable, SimpleItem {

    public static final Creator<Weeks> CREATOR = new Creator<Weeks>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Weeks createFromParcel(Parcel in) {
            return new Weeks(in);
        }

        @Override
        public Weeks[] newArray(int size) {
            return new Weeks[size];
        }
    };

    private String name;

    private boolean checked;


    public Weeks(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Weeks(Parcel in) {
        name = in.readString();
        checked = in.readBoolean();
    }

    public Weeks() {
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setCheck(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeBoolean(checked);
    }
}

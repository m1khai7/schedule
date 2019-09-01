package myapp.schedule.misha.myapplication.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;


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

    private int number;


    public Weeks(String name, boolean checked, int number) {
        this.name = name;
        this.checked = checked;
        this.number = number;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Weeks(Parcel in) {
        name = in.readString();
        checked = in.readBoolean();
        number = in.readInt();
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


    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
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
        dest.writeInt(number);
    }

    public ArrayList<Weeks> getNewListWeeks() {
        ArrayList<Weeks> listWeeks = new ArrayList<>();
        List<String> arrayWeek = Arrays.asList(ScheduleApp.getAppContext().getResources().getStringArray(R.array.weeks));
        for (String stringWeek : arrayWeek) {
            Weeks week = new Weeks();
            week.setName(stringWeek);
            listWeeks.add(week);
        }
        return listWeeks;
    }
}

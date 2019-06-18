package com.example.misha.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.misha.myapplication.data.database.AbsDao;
import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;

import static com.example.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static com.example.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static com.example.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static com.example.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;

public class EditDataModel implements Parcelable {

    public static final Creator<EditDataModel> CREATOR = new Creator<EditDataModel>() {
        @Override
        public EditDataModel createFromParcel(Parcel in) {
            return new EditDataModel(in);
        }

        @Override
        public EditDataModel[] newArray(int size) {
            return new EditDataModel[size];
        }
    };
    private int error;
    private int hint;
    private int title;
    private int inputType;
    private int maxLenth;
    private int type;
    private int position;
    private String name;

    public EditDataModel(int error, int hint, int title, int inputType, int maxLenth, int type, int position, String name) {
        this.error = error;
        this.hint = hint;
        this.title = title;
        this.inputType = inputType;
        this.maxLenth = maxLenth;
        this.type = type;
        this.position = position;
        this.name = name;
    }

    protected EditDataModel(Parcel in) {
        error = in.readInt();
        hint = in.readInt();
        title = in.readInt();
        inputType = in.readInt();
        maxLenth = in.readInt();
        type = in.readInt();
        position = in.readInt();
        name = in.readString();
    }

    public int getError() {
        return error;
    }

    public int getHint() {
        return hint;
    }

    public int getTitle() {
        return title;
    }

    public int getInputType() {
        return inputType;
    }

    public int getMaxLenth() {
        return maxLenth;
    }

    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(error);
        dest.writeInt(hint);
        dest.writeInt(title);
        dest.writeInt(inputType);
        dest.writeInt(maxLenth);
        dest.writeInt(type);
        dest.writeInt(position);
        dest.writeString(name);
    }

    public AbsDao getDao() {
        if (type == FRAGMENT_SUBJECTS) {
            return SubjectDao.getInstance();
        }
        if (type == FRAGMENT_AUDIENCES) {
            return AudienceDao.getInstance();
        }
        if (type == FRAGMENT_EDUCATORS) {
            return EducatorDao.getInstance();
        }
        if (type == FRAGMENT_TYPELESSONS) {
            return TypelessonDao.getInstance();
        }
        return null;
    }
}

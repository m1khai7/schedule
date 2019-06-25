package com.example.misha.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Licenses implements Parcelable {

    public static final Creator<Licenses> CREATOR = new Creator<Licenses>() {
        @Override
        public Licenses createFromParcel(Parcel in) {
            return new Licenses(in);
        }

        @Override
        public Licenses[] newArray(int size) {
            return new Licenses[size];
        }
    };
    private final String title;
    private final String text;

    public Licenses(final String title, final String text) {
        this.title = title;
        this.text = text;
    }

    private Licenses(Parcel in) {
        title = in.readString();
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(text);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}

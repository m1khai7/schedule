package com.example.misha.myapplication.entity;

import android.os.Parcelable;

public interface SimpleItem extends Parcelable {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);
}

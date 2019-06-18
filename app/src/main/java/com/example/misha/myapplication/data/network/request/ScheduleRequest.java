package com.example.misha.myapplication.data.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleRequest {

    @Expose
    @SerializedName("name_db")
    private String dbName;

    public ScheduleRequest(String dbName) {
        this.dbName = dbName;
    }
}

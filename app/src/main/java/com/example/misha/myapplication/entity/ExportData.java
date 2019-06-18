package com.example.misha.myapplication.entity;

import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExportData {

    @Expose
    @SerializedName("audiences")
    private ArrayList<Audience> audiences = AudienceDao.getInstance().getAllData();

    @Expose
    @SerializedName("subjects")
    private ArrayList<Subject> subjects = SubjectDao.getInstance().getAllData();

    @Expose
    @SerializedName("educators")
    private ArrayList<Educator> educators = EducatorDao.getInstance().getAllData();

    @Expose
    @SerializedName("typelessons")
    private ArrayList<Typelesson> typelessons = TypelessonDao.getInstance().getAllData();

    @Expose
    @SerializedName("lessons")
    private ArrayList<Lesson> lessons = LessonDao.getInstance().getAllData();

    @Expose
    @SerializedName("calls")
    private ArrayList<Calls> calls = CallDao.getInstance().getAllData();


}

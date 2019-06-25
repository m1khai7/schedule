package myapp.schedule.misha.myapplication.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImportData {

    @Expose
    @SerializedName("audiences")
    private ArrayList<Audience> audiences;
    @Expose
    @SerializedName("subjects")
    private ArrayList<Subject> subjects;
    @Expose
    @SerializedName("educators")
    private ArrayList<Educator> educators;
    @Expose
    @SerializedName("typelessons")
    private ArrayList<Typelesson> typelessons;
    @Expose
    @SerializedName("lessons")
    private ArrayList<Lesson> lessons;
    @Expose
    @SerializedName("calls")
    private ArrayList<Calls> calls;

    public ArrayList<Audience> getAudiences() {
        return audiences;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Educator> getEducators() {
        return educators;
    }

    public ArrayList<Typelesson> getTypelessons() {
        return typelessons;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<Calls> getCalls() {
        return calls;
    }
}

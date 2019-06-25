package myapp.schedule.misha.myapplication.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("number_week")
    private String number_week;
    @Expose
    @SerializedName("number_day")
    private String number_day;
    @Expose
    @SerializedName("number_lesson")
    private String number_lesson;
    @Expose
    @SerializedName("id_subject")
    private String id_subject;
    @Expose
    @SerializedName("id_audience")
    private String id_audience;
    @Expose
    @SerializedName("id_educator")
    private String id_educator;
    @Expose
    @SerializedName("id_typelesson")
    private String id_typelesson;


    public Lesson() {

    }


    public Lesson(int number_week, int number_day, int number_lesson, int id_subject, int id_audience, int id_educator, int id_typelesson) {
        this.number_week = String.valueOf(number_week);
        this.number_day = String.valueOf(number_day);
        this.number_lesson = String.valueOf(number_lesson);
        this.id_subject = String.valueOf(id_subject);
        this.id_audience = String.valueOf(id_audience);
        this.id_educator = String.valueOf(id_educator);
        this.id_typelesson = String.valueOf(id_typelesson);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_subject() {
        return id_subject;
    }

    public void setId_subject(String id_subject) {
        this.id_subject = id_subject;
    }

    public String getId_audience() {
        return id_audience;
    }

    public void setId_audience(String id_audience) {
        this.id_audience = id_audience;
    }

    public String getId_educator() {
        return id_educator;
    }

    public void setId_educator(String id_educator) {
        this.id_educator = id_educator;
    }

    public String getId_typelesson() {
        return id_typelesson;
    }

    public void setId_typelesson(String id_typelesson) {
        this.id_typelesson = id_typelesson;
    }

    public String getNumber_lesson() {
        return number_lesson;
    }

    public void setNumber_lesson(String number_lesson) {
        this.number_lesson = number_lesson;
    }

    public String getNumber_day() {
        return number_day;
    }

    public void setNumber_day(String number_day) {
        this.number_day = number_day;
    }

    public String getNumber_week() {
        return number_week;
    }

    public void setNumber_week(String number_week) {
        this.number_week = number_week;
    }

    public void setEducatorEdit(String educatorEdit) {
        this.id_educator = educatorEdit;
    }

    public void setData(String subject, String audience, String educator, String typeLesson) {
        this.id_subject = subject;
        this.id_audience = audience;
        this.id_educator = educator;
        this.id_typelesson = typeLesson;
    }
}

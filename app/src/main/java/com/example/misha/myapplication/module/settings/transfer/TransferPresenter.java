package com.example.misha.myapplication.module.settings.transfer;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.database.AppContentProvider;
import com.example.misha.myapplication.data.database.DatabaseHelper;
import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;
import com.example.misha.myapplication.entity.ExportData;
import com.example.misha.myapplication.entity.ImportData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.misha.myapplication.data.database.dao.LessonDao.ID;
import static com.example.misha.myapplication.data.database.dao.LessonDao.ID_AUDIENCE;
import static com.example.misha.myapplication.data.database.dao.LessonDao.ID_EDUCATOR;
import static com.example.misha.myapplication.data.database.dao.LessonDao.ID_SUBJECT;
import static com.example.misha.myapplication.data.database.dao.LessonDao.ID_TYPE_LESSON;
import static com.example.misha.myapplication.data.database.dao.LessonDao.NUMBER_DAY;
import static com.example.misha.myapplication.data.database.dao.LessonDao.NUMBER_LESSON;
import static com.example.misha.myapplication.data.database.dao.LessonDao.NUMBER_WEEK;

public class TransferPresenter extends BaseMainPresenter<TransferFragmentView> implements TransferPresenterInterface {


    private Context context;

    public TransferPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void init() {
    }


    @Override
    public void onClickImport() {
        getView().openDirectory();
    }

    @Override
    public void onClickExport() {
        export_data();
    }


    @Override
    public void import_data(String file) {
        StringBuilder text = new StringBuilder();
        String jsonText = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            jsonText = text.toString();
        } catch (IOException e) {

        }

        Gson gson = new Gson();
        ImportData importData = gson.fromJson(jsonText, ImportData.class);
        CallDao.getInstance().deleteAll();
        CallDao.getInstance().insertAll(importData.getCalls());
        SubjectDao.getInstance().deleteAll();
        SubjectDao.getInstance().insertAll(importData.getSubjects());
        AudienceDao.getInstance().deleteAll();
        AudienceDao.getInstance().insertAll(importData.getAudiences());
        EducatorDao.getInstance().deleteAll();
        EducatorDao.getInstance().insertAll(importData.getEducators());
        TypelessonDao.getInstance().deleteAll();
        TypelessonDao.getInstance().insertAll(importData.getTypelessons());
        LessonDao.getInstance().deleteAll();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        ContentValues set;
        database.beginTransaction();
        try {
            for (int i = 0; i < 612; i++) {
                set = new ContentValues();
                set.put(ID, i + 1);
                set.put(NUMBER_WEEK, importData.getLessons().get(i).getNumber_week());
                set.put(NUMBER_DAY, importData.getLessons().get(i).getNumber_day());
                set.put(NUMBER_LESSON, importData.getLessons().get(i).getNumber_lesson());
                set.put(ID_SUBJECT, importData.getLessons().get(i).getId_subject());
                set.put(ID_AUDIENCE, importData.getLessons().get(i).getId_audience());
                set.put(ID_EDUCATOR, importData.getLessons().get(i).getId_educator());
                set.put(ID_TYPE_LESSON, importData.getLessons().get(i).getId_typelesson());
                database.insert(AppContentProvider.LESSONS_TABLE, null, set);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        //  getView().hideProgressDialog();
        getView().openFragmentSchedule();
    }


    private void export_data() {
        String nameFile = "export.txt";
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(new ExportData());
        File file = new File(context.getExternalFilesDir(null), nameFile);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        writer.close();

        FileOutputStream outputStream;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(jsonFromJavaArrayList.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("text/*");
        context.startActivity(Intent.createChooser(shareIntent, "Отправить расписание"));


    }


}
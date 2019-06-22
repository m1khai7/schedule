package com.example.misha.myapplication.module.exploreList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.data.database.dao.AudienceDao;
import com.example.misha.myapplication.data.database.dao.CallDao;
import com.example.misha.myapplication.data.database.dao.EducatorDao;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.database.dao.SubjectDao;
import com.example.misha.myapplication.data.database.dao.TypelessonDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Audience;
import com.example.misha.myapplication.entity.Calls;
import com.example.misha.myapplication.entity.Educator;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.entity.Subject;
import com.example.misha.myapplication.entity.Typelesson;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ScheduleListFragmentAdapter extends RecyclerView.Adapter {

    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private Subject subject;
    private Audience audience;
    private Educator educator;
    private Typelesson typelesson;


    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
                return new ScheduleListFragmentAdapter.ViewHolderLesson(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_lesson, parent, false);
                return new ScheduleListFragmentAdapter.ViewHolderEmptyLesson(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
                return new ViewHolderDay(view);
        }
        return null;
    }

    public void setLessonList(ArrayList<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                Lesson lesson = lessonList.get(position);
                try {
                    subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
                    audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
                    educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
                    typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
                } catch (NumberFormatException ignored) {
                }
                ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
                ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).timeEditOne.setText(callsList.get((Integer.parseInt(lesson.getNumber_lesson()))/2).getName());
                ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).timeEditTwo.setText(callsList.get((Integer.parseInt(lesson.getNumber_lesson()))/2+1).getName());
                if (subject == null) {
                    break;
                } else {
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).subjectHint.setHint("Предмет");
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).subjectEdit.setText(subject.getName());
                }
                if (audience == null) {
                    break;
                } else {
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).audienceHint.setHint("Аудитория");
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).audienceEdit.setText(audience.getName());
                }
                if (educator == null) {
                    break;
                } else {
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).educatorHint.setHint("Преподаватель");
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).educatorEdit.setText(educator.getName());
                }
                if (typelesson == null) {
                    break;
                } else {
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).typelessonEdit.setText(typelesson.getName());
                }
                break;
            case 1:
                callsList = CallDao.getInstance().getAllData();
                ((ScheduleListFragmentAdapter.ViewHolderEmptyLesson) holder).timeEditOne.setText(callsList.get(mod(position, 6) * 2).getName());
                ((ScheduleListFragmentAdapter.ViewHolderEmptyLesson) holder).timeEditTwo.setText(" - " + callsList.get((mod(position, 6) * 2) + 1).getName());
                break;
        }
    }

    private int mod(int x, int y) {
        int result = x % y;
        return result < 0 ? result + y : result;
    }

    @Override
    public int getItemCount() {
       /*
        if (!lessonList.isEmpty()) {
            if ((lessonList.get(0).getId_subject().equals("0") || lessonList.get(0).getId_audience().equals("0") || lessonList.get(0).getId_educator().equals("0") || lessonList.get(0).getId_typelesson().equals("0")) &&
                    (lessonList.get(1).getId_subject().equals("0") || lessonList.get(1).getId_audience().equals("0") || lessonList.get(1).getId_educator().equals("0") || lessonList.get(1).getId_typelesson().equals("0")) &&
                    (lessonList.get(2).getId_subject().equals("0") || lessonList.get(2).getId_audience().equals("0") || lessonList.get(2).getId_educator().equals("0") || lessonList.get(2).getId_typelesson().equals("0")) &&
                    (lessonList.get(3).getId_subject().equals("0") || lessonList.get(3).getId_audience().equals("0") || lessonList.get(3).getId_educator().equals("0") || lessonList.get(3).getId_typelesson().equals("0")) &&
                    (lessonList.get(4).getId_subject().equals("0") || lessonList.get(4).getId_audience().equals("0") || lessonList.get(4).getId_educator().equals("0") || lessonList.get(4).getId_typelesson().equals("0")) &&
                    (lessonList.get(5).getId_subject().equals("0") || lessonList.get(5).getId_audience().equals("0") || lessonList.get(5).getId_educator().equals("0") || lessonList.get(5).getId_typelesson().equals("0"))) {
                numberItem = 1;
            }
        }*/
        return lessonList.size();
    }

    public static class ViewHolderEmptyLesson extends RecyclerView.ViewHolder {
        private final TextView timeEditOne;
        private final TextView timeEditTwo;

        public ViewHolderEmptyLesson(View view) {
            super(view);
            timeEditOne = view.findViewById(R.id.timeOne);
            timeEditTwo = view.findViewById(R.id.timeTwo);
        }
    }

    public static class ViewHolderDay extends RecyclerView.ViewHolder {
        public ViewHolderDay(View view) {
            super(view);
        }
    }


    public class ViewHolderLesson extends RecyclerView.ViewHolder {

        private final TextView timeEditOne;
        private final TextView timeEditTwo;
        private final TextView subjectEdit;
        private final TextView audienceEdit;
        private final TextView educatorEdit;
        private final TextView typelessonEdit;
        private final TextInputLayout subjectHint;
        private final TextInputLayout audienceHint;
        private final TextInputLayout educatorHint;


        public ViewHolderLesson(View view) {
            super(view);
            timeEditOne = view.findViewById(R.id.timeOne);
            timeEditTwo = view.findViewById(R.id.timeTwo);
            subjectEdit = view.findViewById(R.id.subject);
            audienceEdit = view.findViewById(R.id.audience);
            educatorEdit = view.findViewById(R.id.educator);
            typelessonEdit = view.findViewById(R.id.typelesson);
            subjectHint = view.findViewById(R.id.subject_hint);
            audienceHint = view.findViewById(R.id.audience_hint);
            educatorHint = view.findViewById(R.id.educator_hint);

        }
    }
}

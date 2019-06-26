package myapp.schedule.misha.myapplication.module.schedule.exploreDays.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

public class ScheduleFragmentPagerAdapter extends RecyclerView.Adapter {

    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private Subject subject;
    private Audience audience;
    private Educator educator;
    private Typelesson typelesson;

    @Override
    public int getItemViewType(int position) {
        int codeViewType = 0;
        Lesson lesson = lessonList.get(position);
        try {
            subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
            audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
            educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
            typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
        } catch (NumberFormatException ignored) {
        }
        if (subject == null || audience == null || educator == null || typelesson == null) {
            codeViewType = 1;
        }
        if (getItemCount() == 1) {
            codeViewType = 2;
        }
        return codeViewType;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
                return new ViewHolderLesson(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_lesson, parent, false);
                return new ViewHolderEmptyLesson(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_day, parent, false);
                return new ViewHolderEmptyDay(view);
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
                ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
                ((ViewHolderLesson) holder).timeEditOne.setText(callsList.get(position * 2).getName());
                ((ViewHolderLesson) holder).timeEditTwo.setText(callsList.get((position * 2) + 1).getName());
                Lesson lesson = lessonList.get(position);
                try {
                    subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
                    audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
                    educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
                    typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
                } catch (NumberFormatException ignored) {
                }
                if (subject == null) {
                    break;
                } else {
                    ((ViewHolderLesson) holder).subjectHint.setHint("Предмет");
                    ((ViewHolderLesson) holder).subjectEdit.setText(subject.getName());
                }
                if (audience == null) {
                    break;
                } else {
                    ((ViewHolderLesson) holder).audienceHint.setHint("Аудитория");
                    ((ViewHolderLesson) holder).audienceEdit.setText(audience.getName());
                }
                if (educator == null) {
                    break;
                } else {
                    ((ViewHolderLesson) holder).educatorHint.setHint("Преподаватель");
                    ((ViewHolderLesson) holder).educatorEdit.setText(educator.getName());
                }
                if (typelesson == null) {
                    break;
                } else {
                    ((ViewHolderLesson) holder).typelessonEdit.setText(typelesson.getName());
                }
                break;

            case 1:
                callsList = CallDao.getInstance().getAllData();
                ((ViewHolderEmptyLesson) holder).timeEditOne.setText(callsList.get(position * 2).getName());
                ((ViewHolderEmptyLesson) holder).timeEditTwo.setText(" - " + callsList.get((position * 2) + 1).getName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        int numberItem = lessonList.size();
        if (!lessonList.isEmpty()) {
            int countPositiveItems = 0;
            for (int i = 0; i < 6; i++) {
                Lesson lesson = lessonList.get(i);
                try {
                    subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
                    audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
                    educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
                    typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
                } catch (NumberFormatException ignored) {
                }
                if (subject == null || audience == null || educator == null || typelesson == null) {
                    countPositiveItems += 1;
                }
            }
            if (countPositiveItems == 6) {
                numberItem = 1;
            }
        }
        return numberItem;
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

    public static class ViewHolderEmptyDay extends RecyclerView.ViewHolder {
        public ViewHolderEmptyDay(View view) {
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

package myapp.schedule.misha.myapplication.module.exploreList;

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
import myapp.schedule.misha.myapplication.util.DataUtil;

public class ScheduleListFragmentAdapter extends RecyclerView.Adapter {

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
        return codeViewType;
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_day, parent, false);
                return new ViewHolderDataDay(view);
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
                try {
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).timeEditOne.setText(callsList.get(Integer.parseInt(String.valueOf(lessonList.get(position).getNumber_lesson())) * 2 - 2).getName());
                    ((ScheduleListFragmentAdapter.ViewHolderLesson) holder).timeEditTwo.setText(callsList.get(Integer.parseInt(String.valueOf(lessonList.get(position).getNumber_lesson())) * 2 - 1).getName());
                } catch (NumberFormatException ignored) {
                }
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
                ((ViewHolderDataDay) holder).dataDayText.setText(DataUtil.dateDay(lessonList, position));
        }
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class ViewHolderDataDay extends RecyclerView.ViewHolder {
        private final TextView dataDayText;

        public ViewHolderDataDay(View view) {
            super(view);
            dataDayText = view.findViewById(R.id.titleDataDay);
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

        ViewHolderLesson(View view) {
            super(view);
            timeEditOne = view.findViewById(R.id.timeOne);
            timeEditTwo = view.findViewById(R.id.timeTwo);
            subjectEdit = view.findViewById(R.id.subject);
            audienceEdit = view.findViewById(R.id.timeLesson);
            educatorEdit = view.findViewById(R.id.day);
            typelessonEdit = view.findViewById(R.id.typelesson);
            subjectHint = view.findViewById(R.id.subject_hint);
            audienceHint = view.findViewById(R.id.audience_hint);
            educatorHint = view.findViewById(R.id.educator_hint);
        }
    }
}

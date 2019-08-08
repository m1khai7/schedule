package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.data.database.dao.AudienceDao;
import myapp.schedule.misha.myapplication.data.database.dao.CallDao;
import myapp.schedule.misha.myapplication.data.database.dao.EducatorDao;
import myapp.schedule.misha.myapplication.data.database.dao.SubjectDao;
import myapp.schedule.misha.myapplication.data.database.dao.TypelessonDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Calls;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static myapp.schedule.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;

public class EditScheduleFragmentPagerAdapter extends RecyclerView.Adapter<EditScheduleFragmentPagerAdapter.ViewHolder> {

    private List<Lesson> lessonList;
    private EditSchedulePagePresenterInterface callback;
    private Subject subject;
    private Audience audience;
    private Educator educator;
    private Typelesson typelesson;

    public EditScheduleFragmentPagerAdapter(EditSchedulePagePresenterInterface editScheduleCallback) {
        this.callback = editScheduleCallback;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edit_lesson, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.render(position);
        holder.textViewOptions.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(view.getContext(), holder.textViewOptions);
            popup.inflate(R.menu.menu_item_edit_lesson);
            if (position == 0) {
                popup.getMenu().removeItem(R.id.copyUp);
            }
            if (position == 5) {
                popup.getMenu().removeItem(R.id.copyDown);
            }
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.copyUp:
                        callback.onCopyUpClick(position);
                        return true;
                    case R.id.copyDown:
                        callback.onCopyDownClick(position);
                        return true;
                    case R.id.copyOtherDay:
                        callback.onCopyLessonOtherDay(position);
                        return true;
                    case R.id.clearLesson:
                        callback.onClearLessonClick(position);
                        return true;
                    case R.id.clearDay:
                        callback.onClearDayClick();
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView timeEditOne;
        private final TextView timeEditTwo;
        private final TextView subjectEdit;
        private final TextView audienceEdit;
        private final TextView educatorEdit;
        private final TextView typeLessonEdit;
        private final ImageView textViewOptions;


        public ViewHolder(View view) {
            super(view);
            timeEditOne = view.findViewById(R.id.timeOne);
            timeEditTwo = view.findViewById(R.id.timeTwo);
            subjectEdit = view.findViewById(R.id.subject);
            audienceEdit = view.findViewById(R.id.timeLesson);
            educatorEdit = view.findViewById(R.id.day);
            typeLessonEdit = view.findViewById(R.id.typelesson);
            textViewOptions = view.findViewById(R.id.menuOptions);
            subjectEdit.setOnClickListener(this);
            audienceEdit.setOnClickListener(this);
            educatorEdit.setOnClickListener(this);
            typeLessonEdit.setOnClickListener(this);
            view.setOnClickListener(this);
        }


        private void render(int position) {
            Lesson lesson = lessonList.get(position);
            ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
            timeEditOne.setText(callsList.get(position * 2).getName());
            timeEditTwo.setText(callsList.get((position * 2) + 1).getName());
            try {
                subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
            } catch (NumberFormatException ignored) {
            }
            try {
                audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
            } catch (NumberFormatException ignored) {
            }
            try {
                educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
            } catch (NumberFormatException ignored) {
            }
            try {
                typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
            } catch (NumberFormatException ignored) {
            }

            if (subject == null) {
                subjectEdit.setText(R.string.hint_subject);
            } else {
                subjectEdit.setText(subject.getName());
            }
            if (audience == null) {
                audienceEdit.setText(R.string.hint_audience);
            } else {
                audienceEdit.setText(audience.getName());
            }
            if (educator == null) {
                educatorEdit.setText(R.string.hint_educator);
            } else {
                educatorEdit.setText(educator.getName());
            }
            if (typelesson == null) {
                typeLessonEdit.setText(R.string.hint_typelesson);
            } else {
                typeLessonEdit.setText(typelesson.getName());
            }

            if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
                textViewOptions.setImageResource(R.drawable.ic_more_vert_white);
            }
            if (Preferences.getInstance().getSelectedTheme().equals(LIGHT_THEME)) {
                textViewOptions.setImageResource(R.drawable.ic_more_vert_black);
            }

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.subject) {
                callback.onSubjectClick(getAdapterPosition());
            }
            if (v.getId() == R.id.timeLesson) {
                callback.onAudienceClick(getAdapterPosition());
            }
            if (v.getId() == R.id.day) {
                callback.onEducatorClick(getAdapterPosition());
            }
            if (v.getId() == R.id.typelesson) {
                callback.onTypelessonClick(getAdapterPosition());
            }
        }
    }
}

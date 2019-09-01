package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
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

public class EditScheduleFragmentPagerAdapter extends RecyclerView.Adapter<EditScheduleFragmentPagerAdapter.ViewHolder> {

    private List<Lesson> lessonList;
    private EditSchedulePagePresenterInterface callback;
    private Subject subject;
    private Audience audience;
    private Educator educator;
    private Typelesson typelesson;
    private Context context;

    public EditScheduleFragmentPagerAdapter(EditSchedulePagePresenterInterface editScheduleCallback, Context context) {
        this.callback = editScheduleCallback;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            PopupMenu.OnMenuItemClickListener {

        private final TextView timeEditOne;
        private final TextView timeEditTwo;
        private final TextView subjectEdit;
        private final TextView audienceEdit;
        private final TextView educatorEdit;
        private final TextView typeLessonEdit;
        private final ImageView textViewOptions;
        private PopupMenu popup;


        public ViewHolder(View view) {
            super(view);
            timeEditOne = view.findViewById(R.id.timeOne);
            timeEditTwo = view.findViewById(R.id.timeTwo);
            subjectEdit = view.findViewById(R.id.subject);
            audienceEdit = view.findViewById(R.id.audience);
            educatorEdit = view.findViewById(R.id.educator);
            typeLessonEdit = view.findViewById(R.id.typelesson);
            textViewOptions = view.findViewById(R.id.menuOptions);
            subjectEdit.setOnClickListener(this);
            audienceEdit.setOnClickListener(this);
            educatorEdit.setOnClickListener(this);
            typeLessonEdit.setOnClickListener(this);
            textViewOptions.setOnClickListener(this);
            popup = new PopupMenu(view.getContext(), textViewOptions);
            popup.inflate(R.menu.menu_item_edit_lesson);
            popup.setOnMenuItemClickListener(this);
        }

        private void render(int position) {
            Lesson lesson = lessonList.get(position);
            ArrayList<Calls> callsList = CallDao.getInstance().getAllData();
            timeEditOne.setText(callsList.get(position * 2).getName());
            timeEditTwo.setText(callsList.get((position * 2) + 1).getName());
            if (position == 0) {
                popup.getMenu().removeItem(R.id.copyUp);
            }
            if (position == 5) {
                popup.getMenu().removeItem(R.id.copyDown);
            }
            try {
                subject = SubjectDao.getInstance().getItemByID(Long.parseLong(lesson.getId_subject()));
                audience = AudienceDao.getInstance().getItemByID(Long.parseLong(lesson.getId_audience()));
                educator = EducatorDao.getInstance().getItemByID(Long.parseLong(lesson.getId_educator()));
                typelesson = TypelessonDao.getInstance().getItemByID(Long.parseLong(lesson.getId_typelesson()));
            } catch (NumberFormatException ignored) {
            }
            subjectEdit.setText(subject == null ? ScheduleApp.getStr(R.string.hint_subject) : subject.getName());
            audienceEdit.setText(audience == null ? ScheduleApp.getStr(R.string.hint_audience) : audience.getName());
            educatorEdit.setText(educator == null ? ScheduleApp.getStr(R.string.hint_educator) : educator.getName());
            typeLessonEdit.setText(typelesson == null ? ScheduleApp.getStr(R.string.hint_typelesson) : typelesson.getName());
            String selectedTheme = Preferences.getInstance().getSelectedTheme();
            textViewOptions.setImageResource(selectedTheme.equals(DARK_THEME) ? R.drawable.ic_more_vert_white : R.drawable.ic_more_vert_black);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int position = getAdapterPosition();
            if (id == R.id.subject) {
                callback.onSubjectClick(position);
            }
            if (id == R.id.audience) {
                callback.onAudienceClick(position);
            }
            if (id == R.id.educator) {
                callback.onEducatorClick(position);
            }
            if (id == R.id.typelesson) {
                callback.onTypelessonClick(position);
            }
            if (id == R.id.menuOptions) {
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int id = menuItem.getItemId();
            int position = getAdapterPosition();
            if (id == R.id.copyUp) {
                callback.onCopyUpClick(position);
            }
            if (id == R.id.copyDown) {
                callback.onCopyDownClick(position);
            }
            if (id == R.id.copyOtherDay) {
                callback.onCopyLessonOtherDay(position);
            }
            if (id == R.id.clearLesson) {
                callback.onClearLessonClick(position);
            }
            if (id == R.id.clearDay) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(ScheduleApp.getStr(R.string.dialog_clear_day))
                        .setCancelable(false)
                        .setPositiveButton(ScheduleApp.getStr(R.string.ack), (dialog, idButton) -> callback.onClearDayClick())
                        .setNegativeButton(ScheduleApp.getStr(R.string.cancel), (dialog, idButton) -> dialog.cancel());
                builder.create().show();
            }
            return true;
        }
    }
}

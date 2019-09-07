package myapp.schedule.misha.myapplication.module.schedule.edit.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import myapp.schedule.misha.myapplication.Constants;
import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.activity.ActivityCopyLesson;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.data.database.dao.LessonDao;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;
import myapp.schedule.misha.myapplication.entity.Audience;
import myapp.schedule.misha.myapplication.entity.Educator;
import myapp.schedule.misha.myapplication.entity.Lesson;
import myapp.schedule.misha.myapplication.entity.SimpleItem;
import myapp.schedule.misha.myapplication.entity.Subject;
import myapp.schedule.misha.myapplication.entity.Typelesson;
import myapp.schedule.misha.myapplication.module.schedule.edit.page.dialogEdit.DialogEditFragmentListItems;

import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static myapp.schedule.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;
import static myapp.schedule.misha.myapplication.Constants.LIST_ITEMS;

public class EditSchedulePageFragment extends BaseMainFragment implements EditSchedulePageFragmentView, View.OnClickListener {

    private EditScheduleFragmentPagerAdapter rvadapter;
    private FloatingActionButton mainFab, evenWeekFab, unevenWeekFab;
    private Animation fabOpen, fabClose, rotateForward, rotateBackward;
    private EditSchedulePagePresenter presenter;

    public static EditSchedulePageFragment newInstance(int selectedWeek, int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_WEEK, selectedWeek);
        args.putInt(Constants.DAY, position + 1);
        EditSchedulePageFragment fragment = new EditSchedulePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int positionWeek = getArguments().getInt(Constants.SELECTED_WEEK);
        int day = getArguments().getInt(Constants.DAY);
        presenter = new EditSchedulePagePresenter(getContext(), positionWeek, day);
        rvadapter = new EditScheduleFragmentPagerAdapter(presenter, getContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_edit_schedule_recycler, container, false);
        mainFab = getActivity().findViewById(R.id.main_fab);
        evenWeekFab = getActivity().findViewById(R.id.copyWeek);
        unevenWeekFab = getActivity().findViewById(R.id.clearDay);
        mainFab.setOnClickListener(this);
        evenWeekFab.setOnClickListener(this);
        unevenWeekFab.setOnClickListener(this);
        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        RecyclerView rvLessons = view.findViewById(R.id.rv_lessons_edit);
        rvLessons.setAdapter(rvadapter);
        rvLessons.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mainFab.getVisibility() == android.view.View.VISIBLE) {
                    if (Preferences.getInstance().getFabOpen()) {
                        mainFab.hide();
                        mainFab.startAnimation(rotateBackward);
                        mainFab.setClickable(false);
                        evenWeekFab.startAnimation(fabClose);
                        unevenWeekFab.startAnimation(fabClose);
                        evenWeekFab.setClickable(false);
                        unevenWeekFab.setClickable(false);
                        Preferences.getInstance().setFabOpen(false);
                    } else {
                        mainFab.hide();
                        mainFab.setClickable(false);
                        Preferences.getInstance().setFabOpen(false);
                    }
                } else if (dy < 0 && mainFab.getVisibility() != android.view.View.VISIBLE) {
                    mainFab.show();
                    mainFab.setClickable(true);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Preferences.getInstance().setFabOpen(false);
        presenter.init();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }


    public void updateView(ArrayList<Lesson> lessonList) {
        rvadapter.setLessonList(lessonList);
        rvadapter.notifyDataSetChanged();
    }

    public void setWeek(int selectedWeek) {
        presenter.setWeek(selectedWeek + 1);
    }

    @Override
    public void showEditDialog(ArrayList<? extends SimpleItem> items, int position, int fragmentCode) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ITEMS, items);
        args.putInt(POSITION, position);
        args.putInt(FRAGMENT_CODE, fragmentCode);
        DialogEditFragmentListItems dialogFragment = DialogEditFragmentListItems.newInstance(args);
        dialogFragment.show(getChildFragmentManager(), DialogEditFragmentListItems.class.getSimpleName());
    }

    @Override
    public void showCopyLesson(Lesson currentLesson) {
        Intent intent = new Intent(getActivity(), ActivityCopyLesson.class);
        intent.putExtra(EditSchedulePageFragment.CURRENT_LESSON, currentLesson);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        ArrayList<Lesson> lessonList = presenter.getLessonList();
        if (requestCode == FRAGMENT_SUBJECTS) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Subject subject = data.getParcelableExtra(LIST_ITEMS);
            lessonList.get(lessonPosition).setId_subject(subject.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
        if (requestCode == FRAGMENT_TYPELESSONS) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Typelesson typelesson = data.getParcelableExtra(LIST_ITEMS);
            lessonList.get(lessonPosition).setId_typelesson(typelesson.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
        if (requestCode == FRAGMENT_AUDIENCES) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Audience audience = data.getParcelableExtra(LIST_ITEMS);
            lessonList.get(lessonPosition).setId_audience(audience.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
        if (requestCode == FRAGMENT_EDUCATORS) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Educator educator = data.getParcelableExtra(LIST_ITEMS);
            lessonList.get(lessonPosition).setEducatorEdit(educator.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
    }


    public void animateFAB() {
        if (Preferences.getInstance().getFabOpen()) {
            mainFab.startAnimation(rotateBackward);
            evenWeekFab.startAnimation(fabClose);
            unevenWeekFab.startAnimation(fabClose);
            evenWeekFab.setClickable(false);
            unevenWeekFab.setClickable(false);
            Preferences.getInstance().setFabOpen(false);

        } else {
            mainFab.startAnimation(rotateForward);
            evenWeekFab.startAnimation(fabOpen);
            unevenWeekFab.startAnimation(fabOpen);
            evenWeekFab.setClickable(true);
            unevenWeekFab.setClickable(true);
            Preferences.getInstance().setFabOpen(true);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_fab:
                presenter.onButtonClicked(R.id.main_fab);
                break;
            case R.id.copyWeek:
                presenter.onButtonClicked(R.id.copyWeek);
                break;
            case R.id.clearDay:
                presenter.onButtonClicked(R.id.clearDay);
                break;
        }
    }


}
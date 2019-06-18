package com.example.misha.myapplication.module.schedule.edit.page;

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

import com.example.misha.myapplication.Constants;
import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseMainFragment;
import com.example.misha.myapplication.common.core.BasePresenter;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Audience;
import com.example.misha.myapplication.entity.Educator;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.entity.SimpleItem;
import com.example.misha.myapplication.entity.Subject;
import com.example.misha.myapplication.entity.Typelesson;
import com.example.misha.myapplication.module.schedule.edit.page.dialog.DialogFragmentListItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.misha.myapplication.Constants.FRAGMENT_AUDIENCES;
import static com.example.misha.myapplication.Constants.FRAGMENT_EDUCATORS;
import static com.example.misha.myapplication.Constants.FRAGMENT_SUBJECTS;
import static com.example.misha.myapplication.Constants.FRAGMENT_TYPELESSONS;
import static com.example.misha.myapplication.Constants.ITEMS_LIST;

public class EditSchedulePageFragment extends BaseMainFragment implements EditSchedulePageFragmentView {

    private EditScheduleFragmentPagerAdapter rvadapter;
    private FloatingActionButton mainFab, evenWeekFab, unevenWeekFab;
    private Animation fabClose;
    private Animation rotateBackward;
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
        presenter = new EditSchedulePagePresenter(positionWeek, day);
        rvadapter = new EditScheduleFragmentPagerAdapter(presenter);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.item_edit_schedule_recycler, container, false);
        mainFab = getActivity().findViewById(R.id.main_fab);
        evenWeekFab = getActivity().findViewById(R.id.even_weekFab);
        unevenWeekFab = getActivity().findViewById(R.id.uneven_weekFab);

        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

        RecyclerView rvLessons = fragmentView.findViewById(R.id.rv_lessons_edit);
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
                    }
                } else if (dy < 0 && mainFab.getVisibility() != android.view.View.VISIBLE) {
                    mainFab.show();
                    mainFab.setClickable(true);
                }
            }
        });
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
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
        DialogFragmentListItems dialogFragment = DialogFragmentListItems.newInstance(args);
        dialogFragment.show(getChildFragmentManager(), DialogFragmentListItems.class.getSimpleName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultOk, Intent data) {
        ArrayList<Lesson> lessonList = presenter.getLessonList();
        if (requestCode == FRAGMENT_SUBJECTS) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Subject subject = data.getParcelableExtra(ITEMS_LIST);
            lessonList.get(lessonPosition).setId_subject(subject.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
        if (requestCode == FRAGMENT_TYPELESSONS) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Typelesson typelesson = data.getParcelableExtra(ITEMS_LIST);
            lessonList.get(lessonPosition).setId_typelesson(typelesson.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
        if (requestCode == FRAGMENT_AUDIENCES) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Audience audience = data.getParcelableExtra(ITEMS_LIST);
            lessonList.get(lessonPosition).setId_audience(audience.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));
        }
        if (requestCode == FRAGMENT_EDUCATORS) {
            int lessonPosition = data.getIntExtra(POSITION, 0);
            Educator educator = data.getParcelableExtra(ITEMS_LIST);
            lessonList.get(lessonPosition).setEducatorEdit(educator.getId());
            updateView(lessonList);
            LessonDao.getInstance().updateItemByID(lessonList.get(lessonPosition));

        }
    }

}
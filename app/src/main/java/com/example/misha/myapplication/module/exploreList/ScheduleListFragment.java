package com.example.misha.myapplication.module.exploreList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.CustomSpinnerAdapterWeeks;
import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseMainFragment;
import com.example.misha.myapplication.common.core.BasePresenter;
import com.example.misha.myapplication.data.database.dao.LessonDao;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.module.schedule.edit.EditScheduleFragment;
import com.example.misha.myapplication.util.DataUtil;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static com.example.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;


public class ScheduleListFragment extends BaseMainFragment implements ScheduleListFragmentView, AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private CustomSpinnerAdapterWeeks customSpinnerAdapterWeeks;
    private ScheduleListPresenter presenter;
    private ScheduleListFragmentAdapter rvadapter;
    private TextView titleDay;
    private ArrayList<Lesson> lessons;

    @Override
    public void onResume() {
        super.onResume();
        spinner = new Spinner(getContext());
        spinner.setBackgroundColor(Color.TRANSPARENT);
        spinner.setAdapter(customSpinnerAdapterWeeks);
        spinner.setOnItemSelectedListener(this);
        getContext().getToolbar().addView(spinner);
        getContext().setCurrentTitle(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.onWeekSelected(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScheduleListPresenter();
        rvadapter = new ScheduleListFragmentAdapter();
        setHasOptionsMenu(true);
        customSpinnerAdapterWeeks = new CustomSpinnerAdapterWeeks(getContext());
        DataUtil.hintKeyboard(getContext());
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_schedule_list, container, false);
        RecyclerView rvLessons = fragmentView.findViewById(R.id.rv_lessons_edit);
        rvLessons.setAdapter(rvadapter);
        titleDay = fragmentView.findViewById(R.id.titleDay);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvLessons.setLayoutManager(linearLayoutManager);
        rvLessons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) rvLessons.getLayoutManager();
                int firstVisiblePosition = layoutManager != null ? (layoutManager.findFirstVisibleItemPosition() == 0 ? layoutManager.findFirstVisibleItemPosition() : layoutManager.findFirstVisibleItemPosition() - 1) : 0;
                titleDay.setText(DataUtil.dateDay(lessons, firstVisiblePosition));
            }
        });
        return fragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    @Override
    public void selectWeek(int position) {
    }

    @Override
    public void openEditor() {
        getContext().replaceFragment(new EditScheduleFragment());
    }

    @Override
    public void selectCurrentDay(int currentDay) {

    }

    @Override
    public void selectCurrentWeek(int currentWeek) {
        spinner.setSelection(currentWeek);
    }


    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
        return presenter;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_empty, menu);
        if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
            menu.findItem(R.id.btn_edit).setIcon(R.drawable.ic_edit_white);
        }
        if (Preferences.getInstance().getSelectedTheme().equals(LIGHT_THEME)) {
            menu.findItem(R.id.btn_edit).setIcon(R.drawable.ic_edit_black);
        }
    }

    public void updateList(ArrayList<Lesson> lessonList) {
        this.lessons = lessonList;
        rvadapter.setLessonList(lessonList);
        rvadapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.btn_edit) {
            presenter.onButtonClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().getToolbar().removeView(spinner);
    }

}

package com.example.misha.myapplication.module.exploreList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misha.myapplication.CustomSpinnerAdapterWeeks;
import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseMainFragment;
import com.example.misha.myapplication.common.core.BasePresenter;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Lesson;
import com.example.misha.myapplication.module.schedule.edit.EditScheduleFragment;
import com.example.misha.myapplication.module.schedule.exploreDays.page.ScheduleFragmentPagerAdapter;
import com.example.misha.myapplication.util.DataUtil;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static com.example.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;


public class ScheduleListFragment extends BaseMainFragment implements ScheduleListFragmentView, AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private CustomSpinnerAdapterWeeks customSpinnerAdapterWeeks;
    private ScheduleListPresenter presenter;
    private ScheduleListFragmentAdapter rvadapter;
    private Snackbar snackbar;

    @Override
    public void onResume() {
        super.onResume();
        spinner = new Spinner(getContext());
        spinner.setBackgroundColor(Color.TRANSPARENT);
        spinner.setAdapter(customSpinnerAdapterWeeks);
        spinner.setOnItemSelectedListener(this);
        getContext().getToolbar().addView(spinner);
        getContext().setCurrentTitle(null);
        presenter.init();
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
        View fragmentView = inflater.inflate(R.layout.item_edit_schedule_recycler, container, false);
        RecyclerView rvLessons = fragmentView.findViewById(R.id.rv_lessons_edit);
        rvLessons.setAdapter(rvadapter);
        snackbar = Snackbar.make(getContext().findViewById(android.R.id.content), "should disappear when scrolling!", Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvLessons.setLayoutManager(linearLayoutManager);
        rvLessons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {

                    if (!snackbar.isShown())
                        snackbar.show();

                } else if (!recyclerView.canScrollVertically(1)) {
                    if (snackbar.isShown())
                        snackbar.dismiss();
                }
                if (dy < 0) {
                    if (!snackbar.isShown())
                        snackbar.show();
                } else if (dy > 0) {
                    if (snackbar.isShown())
                        snackbar.dismiss();
                }
            }
        });
        return fragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

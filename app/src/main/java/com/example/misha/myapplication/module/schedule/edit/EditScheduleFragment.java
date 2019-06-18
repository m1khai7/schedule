package com.example.misha.myapplication.module.schedule.edit;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener;

import com.example.misha.myapplication.CustomSpinnerAdapterWeeks;
import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseMainFragment;
import com.example.misha.myapplication.common.core.BasePresenter;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.module.schedule.TabDaysAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import static com.example.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static com.example.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;

public class EditScheduleFragment extends BaseMainFragment implements EditScheduleFragmentView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditScheduleFragmentPagerAdapter pagerAdapter;
    private TabDaysAdapter adapterTabDays;
    private Spinner spinner;
    private ViewPager viewPager;
    private FloatingActionButton mainFab, evenWeekFab, unevenWeekFab;
    private Animation fabOpen, fabClose, rotateForward, rotateBackward;
    private RecyclerView dayTabs;
    private CustomSpinnerAdapterWeeks customSpinnerAdapterWeeks;
    private EditSchedulePresenter presenter;


    @Override
    public void onResume() {
        super.onResume();
        spinner = new Spinner(getContext());
        spinner.setBackgroundColor(Color.TRANSPARENT);
        spinner.setAdapter(customSpinnerAdapterWeeks);
        spinner.setOnItemSelectedListener(this);
        presenter.init();
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
        presenter = new EditSchedulePresenter(getContext());
        setHasOptionsMenu(true);
        customSpinnerAdapterWeeks = new CustomSpinnerAdapterWeeks(getContext());
        pagerAdapter = new EditScheduleFragmentPagerAdapter(getChildFragmentManager());
        adapterTabDays = new TabDaysAdapter((position, view) -> presenter.onPageSelected(position));
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_schedule, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                presenter.onPageSwiped(position);
            }
        });
        evenWeekFab = view.findViewById(R.id.even_weekFab);
        unevenWeekFab = view.findViewById(R.id.uneven_weekFab);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(6);
        dayTabs = view.findViewById(R.id.rv_tab);
        dayTabs.setAdapter(adapterTabDays);
        mainFab = view.findViewById(R.id.main_fab);
        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        mainFab.setOnClickListener(this);
        evenWeekFab.setOnClickListener(this);
        unevenWeekFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_empty, menu);
        if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
            menu.findItem(R.id.btn_edit).setIcon(R.drawable.ic_ok_white);
        }
        if (Preferences.getInstance().getSelectedTheme().equals(LIGHT_THEME)) {
            menu.findItem(R.id.btn_edit).setIcon(R.drawable.ic_ok_black);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
        return presenter;
    }

    @Override
    public void selectWeek(int position) {
        pagerAdapter.setWeek(position);
        adapterTabDays.updateData(position);
        adapterTabDays.setSelection(viewPager.getCurrentItem());
        dayTabs.setAdapter(adapterTabDays);
    }

    @Override
    public void selectCurrentDay(int currentDay) {
        int selectedDayTab = Preferences.getInstance().getSelectedPositionTabDays();
        viewPager.setCurrentItem(selectedDayTab);
        adapterTabDays.setSelection(selectedDayTab);
    }

    @Override
    public void selectCurrentWeek(int currentWeek) {
        spinner.setSelection(currentWeek);
    }

    @Override
    public void swipePage(int position) {
        adapterTabDays.setSelection(position);
    }

    @Override
    public void selectPage(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().getToolbar().removeView(spinner);
    }


    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.btn_edit) {
            getContext().getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
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
            case R.id.even_weekFab:
                presenter.onButtonClicked(R.id.even_weekFab);
                break;
            case R.id.uneven_weekFab:
                presenter.onButtonClicked(R.id.uneven_weekFab);
                break;
        }
    }


}

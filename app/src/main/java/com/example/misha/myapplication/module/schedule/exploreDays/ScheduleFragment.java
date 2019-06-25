package com.example.misha.myapplication.module.schedule.exploreDays;

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
import com.example.misha.myapplication.module.schedule.edit.EditScheduleFragment;
import com.example.misha.myapplication.util.DataUtil;

import org.jetbrains.annotations.NotNull;

import static com.example.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static com.example.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;


public class ScheduleFragment extends BaseMainFragment implements ScheduleFragmentView, AdapterView.OnItemSelectedListener {

    private ScheduleFragmentPagerAdapter pagerAdapterTabDays;
    private TabDaysAdapter adapterTabDays;
    private RecyclerView dayTabs;
    private ViewPager viewPager;
    private Spinner spinner;
    private CustomSpinnerAdapterWeeks customSpinnerAdapterWeeks;
    private SchedulePresenter presenter;

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
        presenter = new SchedulePresenter();
        setHasOptionsMenu(true);
        customSpinnerAdapterWeeks = new CustomSpinnerAdapterWeeks(getContext());
        pagerAdapterTabDays = new ScheduleFragmentPagerAdapter(getChildFragmentManager());
        adapterTabDays = new TabDaysAdapter((position, view) -> presenter.onPageSelected(position));
        DataUtil.hintKeyboard(getContext());
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_days, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                presenter.onPageSwiped(position);
            }
        });
        viewPager.setAdapter(pagerAdapterTabDays);
        viewPager.setOffscreenPageLimit(6);
        dayTabs = view.findViewById(R.id.rv_tab);
        dayTabs.setAdapter(adapterTabDays);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void selectWeek(int position) {
        pagerAdapterTabDays.setWeek(position);
        adapterTabDays.updateData(position);
        adapterTabDays.setSelection(viewPager.getCurrentItem());
        dayTabs.setAdapter(adapterTabDays);
    }

    @Override
    public void openEditor() {
        getContext().replaceFragment(new EditScheduleFragment());
    }

    @Override
    public void selectCurrentDay(int currentDay) {
        viewPager.setCurrentItem(currentDay);
        adapterTabDays.setSelection(currentDay);
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

package myapp.schedule.misha.myapplication.module.schedule.exploreDays.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import myapp.schedule.misha.myapplication.Constants;
import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.common.core.BaseMainFragment;
import myapp.schedule.misha.myapplication.common.core.BasePresenter;
import myapp.schedule.misha.myapplication.entity.Lesson;

public class SchedulePageFragment extends BaseMainFragment implements SchedulePageFragmentView {

    private ScheduleFragmentPagerAdapter rvadapter;
    private SchedulePagePresenter presenter;
    private View view;

    public static SchedulePageFragment newInstance(int selectedWeek, int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_WEEK, selectedWeek);
        args.putInt(Constants.DAY, position + 1);
        SchedulePageFragment fragment = new SchedulePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int day = getArguments().getInt(Constants.DAY);
        int positionWeek = getArguments().getInt(Constants.SELECTED_WEEK);
        rvadapter = new ScheduleFragmentPagerAdapter();
        presenter = new SchedulePagePresenter(day, positionWeek);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_edit_schedule_recycler, container, false);
        RecyclerView rvLessons = view.findViewById(R.id.rv_lessons_edit);
        rvLessons.setAdapter(rvadapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setEmptyDay() {
        view.findViewById(R.id.view_empty_day).setVisibility(View.VISIBLE);
    }

    public void updateView(List<Lesson> lessonList) {
        view.findViewById(R.id.view_empty_day).setVisibility(View.GONE);
        rvadapter.setLessonList(lessonList);
    }

    public void setWeek(int selectedWeek) {
        presenter.setWeek(selectedWeek + 1);
    }
}